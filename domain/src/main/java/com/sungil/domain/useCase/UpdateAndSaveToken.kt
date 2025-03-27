package com.sungil.domain.useCase

import com.sungil.domain.UseCase
import com.sungil.domain.repository.DatabaseRepository
import javax.inject.Inject

class UpdateAndSaveToken @Inject constructor(private val repo: DatabaseRepository) :
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

            else -> {
                val result = repo.updateFcmToken(param.fcmToken)
                if (result) {
                    Result.Success("Success to update token")
                } else {
                    Result.Fail("Failed to update token")
                }
            }
        }
    }
}