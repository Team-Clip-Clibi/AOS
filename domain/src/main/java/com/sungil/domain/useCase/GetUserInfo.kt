package com.sungil.domain.useCase

import com.sungil.domain.TOKEN_FORM
import com.sungil.domain.UseCase
import com.sungil.domain.model.UserInfo
import com.sungil.domain.repository.DatabaseRepository
import com.sungil.domain.repository.NetworkRepository
import javax.inject.Inject

class GetUserInfo @Inject constructor(
    private val database: DatabaseRepository,
    private val network: NetworkRepository,
) {
    sealed interface Result : UseCase.Result {
        data class Success(val data: UserInfo) : Result
        data class Fail(val errorMessage: String) : Result
    }

    suspend fun invoke(): Result {
        val userData = database.getUserInfo()
        if (userData != null) {
            userData.phoneNumber = userData.phoneNumber.reMakePhoneNumber()
            return Result.Success(userData)
        }
        val token = database.getToken()
        if (token.first == null || token.second == null) {
            return Result.Fail("Token is null")
        }
        val apiUserData = network.requestUserData(TOKEN_FORM + token.first!!)
            ?: return Result.Fail("network Error")
        val job = network.requestJob(TOKEN_FORM + token.first!!) ?: listOf("NONE", "NONE")
        val diet = network.requestDiet(TOKEN_FORM + token.first!!) ?: "NONE"
        val language = network.requestLanguage(TOKEN_FORM + token.first!!) ?: "KOREAN"
        val loveState = network.requestLove(TOKEN_FORM + token.first!!)
        apiUserData.job = Pair(
            job.getOrNull(0) ?: "NONE",
            job.getOrNull(1) ?: "NONE"
        )
        apiUserData.diet = if ("null" in diet) {
            "NONE"
        } else {
            diet.replace(Regex("[^가-힣 ]"), "")
        }
        apiUserData.language = language
        val saveResult = database.saveUserInfo(
            name = apiUserData.userName,
            nickName = apiUserData.nickName,
            platform = "KAKAO",
            phoneNumber = apiUserData.phoneNumber.reMakePhoneNumber(),
            jobList = apiUserData.job,
            loveState = if (loveState.first == null) {
                Pair("NONE", false)
            } else {
                Pair(loveState.first!!, loveState.second!!)
            },
            diet = apiUserData.diet,
            language = apiUserData.language
        )
        if (!saveResult) {
            return Result.Fail("save Error")
        }
        return Result.Success(apiUserData)
    }

    private fun String.reMakePhoneNumber(): String {
        val digits = this.filter { it.isDigit() }

        return when {
            digits.startsWith("011") && digits.length == 10 -> {
                "${digits.substring(0, 3)}-${digits.substring(3, 6)}-${digits.substring(6, 10)}"
            }

            digits.length == 11 -> {
                "${digits.substring(0, 3)}-${digits.substring(3, 7)}-${digits.substring(7, 11)}"
            }

            else -> this
        }
    }
}