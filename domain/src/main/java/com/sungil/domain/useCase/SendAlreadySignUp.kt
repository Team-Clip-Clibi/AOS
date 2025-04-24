package com.sungil.domain.useCase

import com.sungil.domain.TOKEN_FORM
import com.sungil.domain.UseCase
import com.sungil.domain.repository.DatabaseRepository
import com.sungil.domain.repository.NetworkRepository
import javax.inject.Inject

class SendAlreadySignUp @Inject constructor(
    private val database: DatabaseRepository,
    private val network: NetworkRepository,
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
                    nickName = requestUserInfo.data.nickName,
                    platform = "KAKAO",
                    phoneNumber = requestUserInfo.data.phoneNumber,
                    jobList = Pair("NONE", "NONE"),
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
                val refreshToken = network.requestUpdateToken(token.second)
                if(refreshToken.first != 200){
                    return Result.Fail("reLogin")
                }
                val setToken = database.setToken(accessToken = refreshToken.second !!, refreshToken =  refreshToken.third!!)
                if(!setToken){
                    return Result.Fail("save error")
                }
                val reRequest = network.requestUserData(TOKEN_FORM+refreshToken.second)
                if(reRequest!!.responseCode != 201){
                    return Result.Fail("network error")
                }
                val saveUserData = database.saveUserInfo(
                    name = reRequest.data.userName,
                    nickName = reRequest.data.nickName,
                    platform = "KAKAO",
                    phoneNumber = reRequest.data.phoneNumber,
                    jobList = Pair("NONE", "NONE"),
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