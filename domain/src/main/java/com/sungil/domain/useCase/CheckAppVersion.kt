package com.sungil.domain.useCase

import com.sungil.domain.UseCase
import com.sungil.domain.model.NetworkResult
import com.sungil.domain.repository.NetworkRepository
import javax.inject.Inject

class CheckAppVersion @Inject constructor(private val networkRepository: NetworkRepository) :
    UseCase<CheckAppVersion.Param, CheckAppVersion.Result> {
    data class Param(
        val isDebug: Boolean,
        val version: String,
    ) : UseCase.Param

    sealed interface Result : UseCase.Result {
        data object Success : Result
        data class Fail(val message: String) : Result
    }

    override suspend fun invoke(param: Param): Result {
        return when (val result = networkRepository.requestAppVersion()) {
            is NetworkResult.Error -> {
                Result.Fail("Fail to check Version")
            }

            is NetworkResult.Success -> {
                if (param.version != result.data) {
                    return Result.Fail("Update App")
                }
                return Result.Success
            }
        }
    }
}