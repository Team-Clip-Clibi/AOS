package com.clip.domain.useCase

import com.clip.domain.TOKEN_FORM
import com.clip.domain.UseCase
import com.clip.domain.repository.DatabaseRepository
import com.clip.domain.repository.NetworkRepository
import javax.inject.Inject

class SendReport @Inject constructor(
    private val database: DatabaseRepository,
    private val network: NetworkRepository,
) : UseCase<SendReport.Param, SendReport.Result> {
    data class Param(
        val content: String,
        val reportCategory: String,
    ) : UseCase.Param

    sealed interface Result : UseCase.Result {
        data class Success(val message: String) : Result
        data class Fail(val errorMessage: String) : Result
    }

    override suspend fun invoke(param: Param): Result {
        val token = database.getToken()
        if (token.first == null) {
            return Result.Fail("token is null")
        }
        val report = network.requestReport(
            accessToken = TOKEN_FORM + token.first,
            content = param.content,
            reportCategory = param.reportCategory
        )
        if (report != 204) {
            return Result.Fail("network error")
        }
        return Result.Success("Success")
    }
}