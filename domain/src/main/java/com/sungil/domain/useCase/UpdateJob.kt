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
        val job: String,
    ) : UseCase.Param

    sealed interface Result : UseCase.Result {
        data class Success(val message: String) : Result
        data class Fail(val errorMessage: String) : Result
    }

    override suspend fun invoke(param: Param): Result {
        val token = database.getToken()
        if (param.job == "NONE") {
            return Result.Fail("No Change")
        }
        val updateResult = network.requestUpdateJob(TOKEN_FORM + token.first, param.job)
        when (updateResult) {
            204 -> {
                val userData = database.getUserInfo()
                userData.job = param.job
                val saveResult = database.saveUserInfo(
                    name = userData.userName,
                    nickName = userData.nickName ?: "error",
                    platform = "KAKAO",
                    phoneNumber = userData.phoneNumber,
                    jobList = userData.job,
                    loveState = userData.loveState,
                    diet = userData.diet,
                    language = userData.language
                )
                if (!saveResult) {
                    return Result.Fail("Save Fail")
                }
                return Result.Success("Save Success")
            }

            401 -> {
                val refreshToken = network.requestUpdateToken(token.second)
                if (refreshToken.first != 200) {
                    return Result.Fail("reLogin")
                }
                if (refreshToken.second == null || refreshToken.third == null) {
                    return Result.Fail("network error")
                }
                val saveToken = database.setToken(refreshToken.second!!, refreshToken.third!!)
                if (!saveToken) {
                    return Result.Fail("Save Fail")
                }
                val reRequest =
                    network.requestUpdateJob(TOKEN_FORM + refreshToken.second, param.job)
                if (reRequest != 204) {
                    return Result.Fail("Fail to update job")
                }
                return Result.Success("Save Success")
            }

            else -> return Result.Fail("network error")
        }
    }
}