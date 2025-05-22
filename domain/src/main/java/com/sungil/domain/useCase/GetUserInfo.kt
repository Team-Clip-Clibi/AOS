package com.sungil.domain.useCase

import com.sungil.domain.TOKEN_FORM
import com.sungil.domain.UseCase
import com.sungil.domain.model.UserData
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
        val job = requestJob(userData, token) { newToken -> token = newToken }
        if (job.first == "Fail") return Result.Fail(job.second)
        userData.job = job.second
        val diet = requestDiet(userData, token) { newToken -> token = newToken }
        if (diet.first == "Fail") return Result.Fail(diet.second)
        userData.diet = diet.second
        val love = requestLove(userData, token) { newToken -> token = newToken }
        if (love.first == "Fail") return Result.Fail(love.second)
        userData.loveState = Pair(love.second, love.third)
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
        return if (saveResult) Result.Success(userData) else Result.Fail("save error")
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
                return Pair("Success", userData.job)
            }

            401 -> {
                val refreshToken = network.requestUpdateToken(token.second)
                if (refreshToken.first != 200) {
                    return Pair("Fail", "reLogin")
                }
                val saveToken = database.setToken(refreshToken.second!!, refreshToken.third!!)
                if (!saveToken) {
                    return Pair("Fail", "save error")
                }
                updateToken(Pair(refreshToken.second!!, refreshToken.third!!))
                val reRequest = network.requestJob(TOKEN_FORM + token.first)
                if (reRequest.responseCode != 200) {
                    return Pair("Fail", "network error")
                }
                if (reRequest.job == "") {
                    return Pair("Success", "NONE")
                }
                return Pair("Success", reRequest.job)
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
                return Pair("Success", requestDiet.diet.diet)
            }

            401 -> {
                val refreshToken = network.requestUpdateToken(token.second)
                if (refreshToken.first != 200) {
                    return Pair("Fail", "reLogin")
                }
                val saveToken = database.setToken(refreshToken.second!!, refreshToken.third!!)
                if (!saveToken) {
                    return Pair("Fail", "save error")
                }
                updateToken(Pair(refreshToken.second!!, refreshToken.third!!))
                val reRequest = network.requestDiet(TOKEN_FORM + token.first)
                if (reRequest.response != 200) {
                    return Pair("Fail", "network error")
                }
                if (reRequest.diet.diet.contains("null")) {
                    return Pair("Success", "NONE")
                }
                return Pair("Success", reRequest.diet.diet)
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
                val refreshToken = network.requestUpdateToken(token.second)
                if (refreshToken.first != 200) {
                    return Triple("Fail", "reLogin", false)
                }
                val saveToken = database.setToken(refreshToken.second!!, refreshToken.third!!)
                if (!saveToken) {
                    return Triple("Fail", "save error", false)
                }
                updateToken(Pair(refreshToken.second!!, refreshToken.third!!))
                val reRequest = network.requestLove(TOKEN_FORM + token.first)
                if (reRequest.responseCode != 200) {
                    return Triple("Fail", "network error", false)
                }
                return Triple(
                    "Success",
                    reRequest.data.relationShip,
                    reRequest.data.isSameRelationShip
                )
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