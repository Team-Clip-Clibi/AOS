package com.sungil.domain.useCase

import com.sungil.domain.UseCase
import com.sungil.domain.repository.DatabaseRepository
import javax.inject.Inject

class LogOut @Inject constructor(private val database: DatabaseRepository) {

    sealed interface Result : UseCase.Result {
        data class Success(val message: String) : Result
        data class Fail(val errorMessage: String) : Result
    }

    suspend fun invoke(): Result {
        val result = database.removeToken()
        if (!result) {
            return Result.Fail("fail to remove data")
        }
        return Result.Success("Success")
    }
}