package com.sungil.domain.useCase

import com.sungil.domain.UseCase
import com.sungil.domain.repository.DatabaseRepository
import com.sungil.domain.repository.NetworkRepository
import com.sungil.domain.useCase.SetNotifyState.Result
import javax.inject.Inject

class UpdateAndSaveToken @Inject constructor(
    private val repo: DatabaseRepository,
    private val network : NetworkRepository
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
        return when {
            beforeToken.isEmpty() || beforeToken != param.fcmToken -> {
                val result = repo.saveFcmToken(param.fcmToken)
                when(result){
                    true ->{
                        val tokenStatus = repo.getTokenDataStatus()
                        if(tokenStatus){
                            return Result.Success("Success to save token (no previous token)")
                        }
                        //TODO 서버에 FCM 토큰 보내는 작업 해라
                    }
                    false -> {
                        Result.Fail("Failed to save token")
                    }
                }
                if (result) {
                    Result.Success("Success to save token (no previous token)")
                } else {
                    Result.Fail("Failed to save token")
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