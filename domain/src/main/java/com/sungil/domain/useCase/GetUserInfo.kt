package com.sungil.domain.useCase

import com.sungil.domain.TOKEN_FORM
import com.sungil.domain.UseCase
import com.sungil.domain.model.UserData
import com.sungil.domain.model.UserInfo
import com.sungil.domain.repository.DatabaseRepository
import com.sungil.domain.repository.NetworkRepository
import javax.inject.Inject

class GetUserInfo @Inject constructor(
    private val database: DatabaseRepository,
    private val network: NetworkRepository,
) {
    sealed interface Result : UseCase.Result {
        data class Success(val data: UserData) : Result
        data class Fail(val errorMessage: String) : Result
    }

    suspend fun invoke(): Result {
        val userData = database.getUserInfo()
        userData.phoneNumber = userData.phoneNumber.reMakePhoneNumber()
        var token = database.getToken()

        updateJobIfNeeded(userData, token)?.let { token = it } ?: return Result.Fail("job error")
        updateDietIfNeeded(userData, token)?.let { token = it } ?: return Result.Fail("diet error")
        updateLoveStateIfNeeded(userData, token)?.let { token = it } ?: return Result.Fail("love error")

        val saveResult = database.saveUserInfo(
            name = userData.userName,
            nickName = userData.nickName,
            platform = "KAKAO",
            phoneNumber = userData.phoneNumber,
            jobList = userData.job,
            loveState = userData.loveState,
            diet = userData.diet,
            language = userData.language
        )

        return if (saveResult) Result.Success(userData) else Result.Fail("save error")
    }

    private suspend fun updateJobIfNeeded(userData: UserData, token: Pair<String, String>): Pair<String, String>? {
        if (userData.job.first != "NONE") return token

        val result = network.requestJob(TOKEN_FORM + token.first)
        return when (result.responseCode) {
            200 -> {
                userData.job = Pair(result.jobList[0], result.jobList[1])
                token
            }

            401 -> refreshTokenIfNeeded(token.second) { newToken ->
                val retry = network.requestJob(TOKEN_FORM + newToken)
                if (retry.responseCode == 200) {
                    userData.job = Pair(retry.jobList[0], retry.jobList[1])
                    true
                } else false
            }?.also { return it } ?: return null

            else -> null
        }
    }

    private suspend fun updateDietIfNeeded(userData: UserData, token: Pair<String, String>): Pair<String, String>? {
        if (userData.diet != "NONE") return token

        val result = network.requestDiet(TOKEN_FORM + token.first)
        return when (result.response) {
            200 -> {
                userData.diet = result.diet.diet
                token
            }

            401 -> refreshTokenIfNeeded(token.second) { newToken ->
                val retry = network.requestDiet(TOKEN_FORM + newToken)
                if (retry.response == 200) {
                    userData.diet = retry.diet.diet
                    true
                } else false
            }?.also { return it } ?: return null

            else -> null
        }
    }

    private suspend fun updateLoveStateIfNeeded(userData: UserData, token: Pair<String, String>): Pair<String, String>? {
        if (userData.loveState.first != "NONE") return token

        val result = network.requestLove(TOKEN_FORM + token.first)
        return when (result.responseCode) {
            200 -> {
                userData.loveState = Pair(result.data.relationShip, result.data.isSameRelationShip)
                token
            }

            401 -> refreshTokenIfNeeded(token.second) { newToken ->
                val retry = network.requestLove(TOKEN_FORM + newToken)
                if (retry.responseCode == 200) {
                    userData.loveState = Pair(retry.data.relationShip, retry.data.isSameRelationShip)
                    true
                } else false
            }?.also { return it } ?: return null

            else -> null
        }
    }

    private suspend fun refreshTokenIfNeeded(
        oldRefreshToken: String,
        onSuccess: suspend (newAccessToken: String) -> Boolean
    ): Pair<String, String>? {
        val refreshResult = network.requestUpdateToken(TOKEN_FORM + oldRefreshToken)
        if (refreshResult.first != 200) return null

        val saved = database.setToken(refreshResult.second!!, refreshResult.third!!)
        if (!saved) return null

        val success = onSuccess(refreshResult.second!!)
        return if (success) Pair(refreshResult.second!!, refreshResult.third!!) else null
    }

    private fun String.reMakePhoneNumber(): String {
        val digits = this.filter { it.isDigit() }

        return when {
            digits.startsWith("011") && digits.length == 10 ->
                "${digits.substring(0, 3)}-${digits.substring(3, 6)}-${digits.substring(6, 10)}"

            digits.length == 11 ->
                "${digits.substring(0, 3)}-${digits.substring(3, 7)}-${digits.substring(7, 11)}"

            else -> this
        }
    }
}