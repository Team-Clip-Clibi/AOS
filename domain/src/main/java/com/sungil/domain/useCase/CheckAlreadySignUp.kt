package com.sungil.domain.useCase

import com.sungil.domain.UseCase
import com.sungil.domain.repository.DatabaseRepository
import javax.inject.Inject

class CheckAlreadySignUp @Inject constructor(private val repo: DatabaseRepository) {
    sealed interface Result : UseCase.Result {
        data class Success(val message: String) : Result
        data class Fail(val errorMessage: String) : Result
    }

    suspend fun invoke(): Result {
        val result = repo.getSingUpData()
        if (!result) {
            return Result.Fail("Not SignUp")
        }
        return Result.Success("SignUp")
    }
}