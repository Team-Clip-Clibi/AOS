package com.clip.domain.useCase

import com.clip.domain.UseCase
import com.clip.domain.repository.DatabaseRepository
import javax.inject.Inject

class SetPermissionCheck @Inject constructor(private val database: DatabaseRepository) :
    UseCase<SetPermissionCheck.Param, SetPermissionCheck.Result> {
    data class Param(
        val key: String,
        val data: Boolean,
    ) : UseCase.Param

    sealed interface Result : UseCase.Result {
        data class Success(val message: String) : Result
        data class Fail(val errorMessage: String) : Result
    }

    override suspend fun invoke(param: Param): Result {
        val result = database.setPermissionCheck(key = param.key, data = param.data)
        if (!result) {
            return Result.Fail("set Fail Permission")
        }
        return Result.Success("Set Success Permission")
    }
}