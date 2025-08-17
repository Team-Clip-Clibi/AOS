package com.sungil.domain.useCase

import com.sungil.domain.UseCase
import com.sungil.domain.repository.NetworkRepository
import javax.inject.Inject

class CheckAppVersion @Inject constructor(private val networkRepository: NetworkRepository) :
    UseCase<CheckAppVersion.Param, CheckAppVersion.Result> {
    data class Param(
        val isDebug: Boolean,
        val version: String
    ) : UseCase.Param

    sealed interface Result : UseCase.Result {
        data object Success : Result
        data class Fail(val message: String) : Result
    }

    override suspend fun invoke(param: Param): Result {
        if(param.isDebug) return Result.Success
        /**
         * TODO 앱 버전 검사 로직 추가
         */
        return Result.Fail("please check version")
    }
}