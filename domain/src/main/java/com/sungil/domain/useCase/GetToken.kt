package com.sungil.domain.useCase

import com.sungil.domain.UseCase
import com.sungil.domain.repository.DatabaseRepository
import javax.inject.Inject

class GetToken @Inject constructor(private val repo: DatabaseRepository) {

    sealed interface Result : UseCase.Result {
        data class Success(val token: String) : Result
        data class Fail(val errorMessage: String) : Result
    }

    suspend fun invoke(): Result {
        val data = repo.getKaKaoId()
        if (data =="") {
            return Result.Fail("The Data is Error")
        }
        return Result.Success(data)
    }
}