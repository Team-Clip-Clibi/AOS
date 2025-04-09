package com.sungil.domain.useCase

import com.sungil.domain.TOKEN_FORM
import com.sungil.domain.UseCase
import com.sungil.domain.repository.DatabaseRepository
import com.sungil.domain.repository.NetworkRepository
import javax.inject.Inject

class UpdateJob @Inject constructor(
    private val network: NetworkRepository,
    private val database: DatabaseRepository,
) : UseCase<UpdateJob.Param, UpdateJob.Result> {
    data class Param(
        val job: Pair<String, String>,
    ) : UseCase.Param

    sealed interface Result : UseCase.Result {
        data class Success(val message: String) : Result
        data class Fail(val errorMessage: String) : Result
    }

    override suspend fun invoke(param: Param): Result {
        val token = database.getToken()
        if (token.first == null || token.second == null) {
            return Result.Fail("Token is null")
        }
        val jobList = listOf(param.job.first, param.job.second).filter { it != "NONE" }
        if (jobList.isEmpty()) {
            return Result.Fail("No Change")
        }
        val updateResult = network.requestUpdateJob(TOKEN_FORM + token.first!!, jobList)
        if (updateResult != 204) {
            return Result.Fail("network Error")
        }
        return Result.Success("Save Success")
    }
}