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

        val saveResult = database.saveUserInfo(
            name = apiUserData.userName,
            nickName = apiUserData.nickName,
            platform = "KAKAO",
            phoneNumber = apiUserData.phoneNumber.reMakePhoneNumber(),
            jobList = Pair("NONE" ,"NONE"),
            loveState = Pair("NONE" , "NONE"),
            diet = "NONE",
            language = "KOREAN"
        )
        if (!saveResult) {
            return Result.Fail("save Error")
        }
        val jobList = if (apiUserData.job.first == "NONE") {
            Pair("학생", "IT")
        } else {
            apiUserData.job
        }
        apiUserData.job = jobList
        apiUserData.phoneNumber = apiUserData.phoneNumber.reMakePhoneNumber()
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

            else -> this // 포맷 안 맞으면 원본 반환
        }
    }
}