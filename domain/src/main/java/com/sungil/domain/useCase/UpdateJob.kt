package com.sungil.domain.useCase

import com.sungil.domain.TOKEN_FORM
import com.sungil.domain.UseCase
import com.sungil.domain.repository.DatabaseRepository
import com.sungil.domain.repository.NetworkRepository
import com.sungil.domain.tokenManger.TokenMangerController
import javax.inject.Inject

class UpdateJob @Inject constructor(
    private val network: NetworkRepository,
    private val database: DatabaseRepository,
    private val tokenMangerController: TokenMangerController,
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
                val refreshToken = tokenMangerController.requestUpdateToken(token.second)
                if (!refreshToken) return Result.Fail("reLogin")
                val newToken = database.getToken()
                val reRequest = network.requestUpdateJob(
                    accessToken = TOKEN_FORM + newToken.first,
                    data = param.job
                )
                if (reRequest != 204) {
                    return Result.Fail("Fail to update job")
                }
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

            else -> return Result.Fail("network error")
        }
    }
}