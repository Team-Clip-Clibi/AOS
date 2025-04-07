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
        val apiData = network.requestUserData(TOKEN_FORM + token.first!!)
            ?: return Result.Fail("network Error")
        val saveResult = database.saveUserInfo(
            servicePermission = true,
            privatePermission = true,
            marketingPermission = true,
            name = apiData.userName,
            nickName = apiData.nickName,
            birthYear = "",
            birthMonth = "",
            birthDay = "",
            city = "",
            area = "",
            gender = "MALE",
            platform = "KAKAO",
            phoneNumber = apiData.phoneNumber
        )
        if (!saveResult) {
            return Result.Fail("save Error")
        }
        apiData.phoneNumber = apiData.phoneNumber.reMakePhoneNumber()
        return Result.Success(apiData)
    }
    fun String.reMakePhoneNumber(): String {
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