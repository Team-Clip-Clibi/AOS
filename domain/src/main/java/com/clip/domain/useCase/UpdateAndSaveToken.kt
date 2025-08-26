package com.clip.domain.useCase

import com.clip.domain.TOKEN_FORM
import com.clip.domain.UseCase
import com.clip.domain.repository.DatabaseRepository
import com.clip.domain.repository.NetworkRepository
import com.clip.domain.tokenManger.TokenMangerController
import javax.inject.Inject

class UpdateAndSaveToken @Inject constructor(
    private val database: DatabaseRepository,
    private val network: NetworkRepository,
    private val tokenManger: TokenMangerController
) :
    UseCase<UpdateAndSaveToken.Param, UpdateAndSaveToken.Result> {

    data class Param(
        val fcmToken: String,
    ) : UseCase.Param

    sealed interface Result : UseCase.Result {
        data class Success(val message: String) : Result
        data class Fail(val errorMessage: String) : Result
    }

    override suspend fun invoke(param: Param): Result {
        val beforeToken = database.getFcmToken()
        if(beforeToken.isEmpty()){
            val saveFirebase = database.saveFcmToken(param.fcmToken)
            if(!saveFirebase) return Result.Fail("Failed to save token")
            return Result.Fail("reLogin")
        }
        return when {
            beforeToken.isEmpty() || beforeToken != param.fcmToken -> {
                val result = database.saveFcmToken(param.fcmToken)
                when (result) {
                    true -> {
                        val tokenStatus = database.getTokenDataStatus()
                        if (tokenStatus) {
                            return Result.Success("Success to save token (no previous token)")
                        }
                        val token = database.getToken()
                        val updateFcm = network.requestUpdateFcmToken(
                            accessToken = TOKEN_FORM + token.first,
                            fcmToken = param.fcmToken
                        )
                        when (updateFcm) {
                            204 -> {
                                return Result.Success("Success to save token (no previous token)")
                            }

                            401 -> {
                                val updateToken = tokenManger.requestUpdateToken(token.second)
                                if (!updateToken) return Result.Fail("reLogin")
                                val newToken = database.getToken()
                                val reRequest = network.requestUpdateFcmToken(
                                    accessToken = TOKEN_FORM + newToken.first,
                                    fcmToken = param.fcmToken
                                )
                                if (reRequest == 204) {
                                    return Result.Success("Success to save token (no previous token)")
                                }
                                if (reRequest == 401) {
                                    return Result.Fail("reLogin")
                                }
                                return Result.Fail("network error")
                            }

                            else -> return Result.Fail("network error")
                        }
                    }

                    false -> {
                        return Result.Fail("Failed to save token")
                    }
                }
            }

            beforeToken == param.fcmToken -> {
                Result.Success("Already same token")
            }

            else -> {
                Result.Fail("Unknown Error")
            }
        }
    }
}