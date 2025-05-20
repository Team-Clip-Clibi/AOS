package com.sungil.domain.useCase

import com.sungil.domain.UseCase
import com.sungil.domain.repository.DatabaseRepository
import javax.inject.Inject

class GetFirstMatchInput @Inject constructor(private val database: DatabaseRepository) {

    sealed interface Result : UseCase.Result {
        data class Success(val message: String) : Result
        data class Fail(val message: String) : Result
    }

    suspend fun invoke(): Result {
        val result = database.getFirstMatchInput()
        if (!result) {
            return Result.Fail("First match data is not input")
        }
        return Result.Success("First match data is input")
    }
}