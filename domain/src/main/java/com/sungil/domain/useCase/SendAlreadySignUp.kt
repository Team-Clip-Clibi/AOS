package com.sungil.domain.useCase

import com.sungil.domain.UseCase
import com.sungil.domain.repository.DatabaseRepository
import javax.inject.Inject

class SendAlreadySignUp @Inject constructor(private val database: DatabaseRepository) {
    sealed interface Result : UseCase.Result {
        data class Success(val message: String) : Result
        data class Fail(val errorMessage: String) : Result
    }

    suspend fun invoke(): Result {
        val saveResult = database.saveSingUpKey(true)
        if (!saveResult) {
            return Result.Fail("Fail to save")
        }
        return Result.Success("Success")
    }
}