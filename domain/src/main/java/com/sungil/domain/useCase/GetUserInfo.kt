package com.sungil.domain.useCase

import com.sungil.domain.TOKEN_FORM
import com.sungil.domain.UseCase
import com.sungil.domain.model.UserData
import com.sungil.domain.repository.DatabaseRepository
import com.sungil.domain.repository.NetworkRepository
import com.sungil.domain.tokenManger.TokenMangerController
import javax.inject.Inject

class GetUserInfo @Inject constructor(
    private val database: DatabaseRepository,
    private val network: NetworkRepository,
    private val tokenManger: TokenMangerController,
) {
    sealed interface Result : UseCase.Result {
        data class Success(val data: UserData) : Result
        data class Fail(val errorMessage: String) : Result
    }

    suspend fun invoke(): Result {
        val userData = database.getUserInfo()
        userData.phoneNumber = userData.phoneNumber.reMakePhoneNumber()
        var updateData = userData.copy(phoneNumber = userData.phoneNumber)
        var token = database.getToken()
        val job = requestJob(updateData, token) { newToken -> token = newToken }
        if (job.first == "Fail") return Result.Fail(job.second)
        updateData.job = job.second
        val diet = requestDiet(updateData, token) { newToken -> token = newToken }
        if (diet.first == "Fail") return Result.Fail(diet.second)
        updateData.diet = diet.second
        val love = requestLove(updateData, token) { newToken -> token = newToken }
        if (love.first == "Fail") return Result.Fail(love.second)
        updateData.loveState = Pair(love.second, love.third)
        if (userData == updateData) {
            return Result.Success(userData)
        }
        val saveResult = database.saveUserInfo(
            name = updateData.userName,
            nickName = updateData.nickName ?: "error",
            platform = "KAKAO",
            phoneNumber = updateData.phoneNumber,
            jobList = updateData.job,
            loveState = updateData.loveState,
            diet = updateData.diet,
            language = updateData.language
        )
        return if (saveResult) Result.Success(updateData) else Result.Fail("save error")
    }


    private suspend fun requestJob(
        userData: UserData,
        token: Pair<String, String>,
        updateToken: (Pair<String, String>) -> Unit,
    ): Pair<String, String> {
        if (userData.job != "NONE") return Pair("Success", userData.job)
        val result = network.requestJob(TOKEN_FORM + token.first)
        when (result.responseCode) {
            200 -> {
                if (result.job == "") {
                    return Pair("Success", "NONE")
                }
                return Pair("Success", result.job)
            }

            401 -> {
                val refreshToken = tokenManger.requestUpdateToken(token.second)
                if (!refreshToken) {
                    return Pair("Fail", "reLogin")
                }
                val newToken = database.getToken()
                updateToken(Pair(newToken.first, newToken.second))
                val retry = network.requestJob(TOKEN_FORM + newToken.first)
                if (retry.responseCode != 200) {
                    return Pair("Fail", "network error")
                }
                if (retry.job == "") {
                    return Pair("Success", "NONE")
                }
                return Pair("Success", retry.job)
            }

            500 -> {
                return Pair("Success", "NONE")
            }

            else -> return Pair("Fail", "network error")
        }
    }

    private suspend fun requestDiet(
        userData: UserData,
        token: Pair<String, String>,
        updateToken: (Pair<String, String>) -> Unit,
    ): Pair<String, String> {
        if (userData.diet != "NONE") return Pair("Success", userData.diet)
        val requestDiet = network.requestDiet(TOKEN_FORM + token.first)
        when (requestDiet.response) {
            200 -> {
                if (requestDiet.diet.diet.contains("null")) {
                    return Pair("Success", "NONE")
                }
                val regex = Regex("dietaryOption=([^)]*)")
                val result = regex.find(requestDiet.diet.diet)?.groupValues?.get(1)
                if (result.isNullOrEmpty()) {
                    return Pair("Success", "NONE")
                }
                return Pair("Success", result)
            }

            401 -> {
                val refreshToken = tokenManger.requestUpdateToken(token.second)
                if (!refreshToken) {
                    return Pair("Fail", "reLogin")
                }
                val newToken = database.getToken()
                updateToken(Pair(newToken.first, newToken.second))
                val reRequest = network.requestDiet(TOKEN_FORM + newToken.first)
                if (reRequest.response != 200) {
                    return Pair("Fail", "network error")
                }
                if (reRequest.diet.diet.contains("null")) {
                    return Pair("Success", "NONE")
                }
                return Pair("Success", reRequest.diet.diet)
            }

            500 -> {
                return Pair("Success", "NONE")
            }

            else -> return Pair("Fail", "network error")
        }
    }

    private suspend fun requestLove(
        userData: UserData,
        token: Pair<String, String>,
        updateToken: (Pair<String, String>) -> Unit,
    ): Triple<String, String, Boolean> {
        if (userData.loveState.first != "NONE") return Triple(
            "Success",
            userData.loveState.first,
            userData.loveState.second
        )
        val requestLoveState = network.requestLove(TOKEN_FORM + token.first)
        when (requestLoveState.responseCode) {
            200 -> {
                return Triple(
                    "Success",
                    requestLoveState.data.relationShip,
                    requestLoveState.data.isSameRelationShip
                )
            }

            401 -> {
                val refreshToken = tokenManger.requestUpdateToken(token.second)
                if (!refreshToken) {
                    return Triple("Fail", "reLogin", false)
                }
                val newToken = database.getToken()
                updateToken(Pair(newToken.first, newToken.second))
                val reRequest = network.requestLove(TOKEN_FORM + newToken.first)
                if (reRequest.responseCode != 200) {
                    return Triple("Fail", "network error", false)
                }
                return Triple(
                    "Success",
                    reRequest.data.relationShip,
                    reRequest.data.isSameRelationShip
                )
            }

            500 -> {
                return Triple("Success", "NONE", false)
            }

            else -> return Triple("Fail", "network error", false)
        }
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