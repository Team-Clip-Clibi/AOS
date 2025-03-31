package com.sungil.domain.useCase

import com.sungil.domain.UseCase
import com.sungil.domain.repository.DatabaseRepository
import javax.inject.Inject

class SetNotifyState @Inject constructor(
    private val database: DatabaseRepository,
) : UseCase<SetNotifyState.Param, SetNotifyState.Result> {
    data class Param(
        val notifyState: Boolean,
    ) : UseCase.Param

    sealed interface Result : UseCase.Result {
        data class Success(val message: String) : Result
        data class Fail(val errorMessage: String) : Result
    }

    override suspend fun invoke(param: Param): Result {
        val result = database.setNotifyState(param.notifyState)
        if (!result) {
            return Result.Fail("Error save Notify")
        }
        return Result.Success("Success save Notify")
    }
}