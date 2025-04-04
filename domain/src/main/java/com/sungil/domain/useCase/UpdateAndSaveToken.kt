package com.sungil.domain.useCase

import com.sungil.domain.TOKEN_FORM
import com.sungil.domain.UseCase
import com.sungil.domain.repository.DatabaseRepository
import com.sungil.domain.repository.NetworkRepository
import javax.inject.Inject

class UpdateAndSaveToken @Inject constructor(
    private val repo: DatabaseRepository,
    private val api: NetworkRepository,
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
        val beforeToken = repo.getFcmToken()
        val token = repo.getToken()
        return when {
            beforeToken.isEmpty() -> {
                val result = repo.saveFcmToken(param.fcmToken)
                if (result) {
                    Result.Success("Success to save token (no previous token)")
                } else {
                    Result.Fail("Failed to save token")
                }
            }

            beforeToken == param.fcmToken -> {
                Result.Success("Already same token")
            }

            beforeToken != param.fcmToken && token.first != null && token.second != null -> {
                val result = repo.updateFcmToken(param.fcmToken)
                if (!result) {
                    return Result.Fail("Failed to update token")
                }
                val updateFcmTokenResult = api.requestUpdateFcmToken(TOKEN_FORM + token.first)
                if (updateFcmTokenResult != 204) {
                    return Result.Fail("Fail to update Fcm Token")
                }
                return Result.Success("Success to save data")
            }

            else -> {
                Result.Fail("Unknown Error")
            }
        }
    }
}