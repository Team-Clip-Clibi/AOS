package com.clip.domain.useCase

import com.clip.domain.UseCase
import com.clip.domain.repository.DatabaseRepository
import javax.inject.Inject

class SaveFirstMatchInput @Inject constructor(private val database: DatabaseRepository) {

    sealed interface Result : UseCase.Result {
        data class Success(val message: String) : Result
        data class Fail(val message: String) : Result
    }

    suspend fun invoke(): Result {
        val result = database.setFirstMatchInput()
        if (!result) {
            return Result.Fail("Save Fail")
        }
        return Result.Success("Save success")
    }
}