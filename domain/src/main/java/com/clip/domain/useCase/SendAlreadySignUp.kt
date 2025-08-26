package com.clip.domain.useCase

import com.clip.domain.TOKEN_FORM
import com.clip.domain.UseCase
import com.clip.domain.repository.DatabaseRepository
import com.clip.domain.repository.NetworkRepository
import com.clip.domain.tokenManger.TokenMangerController
import javax.inject.Inject

class SendAlreadySignUp @Inject constructor(
    private val database: DatabaseRepository,
    private val network: NetworkRepository,
    private val tokenMangerController: TokenMangerController
) {
    sealed interface Result : UseCase.Result {
        data class Success(val message: String) : Result
        data class Fail(val errorMessage: String) : Result
    }

    suspend fun invoke(): Result {
        val saveResult = database.saveSingUpKey(true)
        if (!saveResult) {
            return Result.Fail("Fail to save")
        }
        val token = database.getToken()
        val requestUserInfo = network.requestUserData(TOKEN_FORM + token.first)
        when (requestUserInfo!!.responseCode) {
            200 -> {
                val saveUserData = database.saveUserInfo(
                    name = requestUserInfo.data.userName,
                    nickName = requestUserInfo.data.nickName ?: "error",
                    platform = "KAKAO",
                    phoneNumber = requestUserInfo.data.phoneNumber,
                    jobList ="NONE",
                    loveState = Pair("NONE", false),
                    diet = "NONE",
                    language = "KOREAN"
                )
                if (!saveUserData) {
                    return Result.Fail("save error")
                }
                return Result.Success("Success")
            }

            401 -> {
                val refreshToken = tokenMangerController.requestUpdateToken(token.second)
                if (!refreshToken) {
                    return Result.Fail("reLogin")
                }
                val newToken = database.getToken()
                val reRequest = network.requestUserData(TOKEN_FORM + newToken.first)
                if (reRequest!!.responseCode != 200) {
                    return Result.Fail("network error")
                }
                val saveUserData = database.saveUserInfo(
                    name = reRequest.data.userName,
                    nickName = reRequest.data.nickName ?: "error",
                    platform = "KAKAO",
                    phoneNumber = reRequest.data.phoneNumber,
                    jobList = "NONE",
                    loveState = Pair("NONE", false),
                    diet = "NONE",
                    language = "KOREAN"
                )
                if (!saveUserData) {
                    return Result.Fail("save error")
                }
                return Result.Success("Success")
            }
            else -> return Result.Fail("network error")
        }
    }
}