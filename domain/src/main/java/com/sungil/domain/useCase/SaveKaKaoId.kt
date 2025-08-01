package com.sungil.domain.useCase

import com.sungil.domain.UseCase
import com.sungil.domain.repository.DatabaseRepository
import javax.inject.Inject

class SaveKaKaoId @Inject constructor(private val repo: DatabaseRepository) :
    UseCase<SaveKaKaoId.Param, SaveKaKaoId.Result> {
    data class Param(
        val token: String,
    ) : UseCase.Param

    sealed interface Result : UseCase.Result {
        data class Success(val token: String) : Result
        data class Fail(val errorMessage: String) : Result
    }

    override suspend fun invoke(param: Param): Result {
        val result = repo.saveKaKaoId(param.token)
        if (!result) {
            return Result.Fail("fail to save Token")
        }
        return Result.Success(param.token)
    }
}