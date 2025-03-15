package com.sungil.domain.useCase

import com.sungil.domain.UseCase
import com.sungil.domain.repository.DatabaseRepository
import javax.inject.Inject

class GetToken @Inject constructor(private val repo: DatabaseRepository) :
    UseCase<GetToken.Param, GetToken.Result> {
    data class Param(
        val token: String,
        val refreshToken: String,
    ) : UseCase.Param

    sealed interface Result : UseCase.Result {
        data class Success(val message: String) : Result
        data class Fail(val errorMessage: String) : Result
    }

    override suspend fun invoke(param: Param): Result {
        val result = repo.saveToken(param.token, param.refreshToken)
        if (!result) {
            return Result.Fail("fail to save Token")
        }
        return Result.Success("save Success")
    }
}