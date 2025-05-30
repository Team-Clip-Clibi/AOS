package com.sungil.domain.useCase

import com.sungil.domain.TOKEN_FORM
import com.sungil.domain.UseCase
import com.sungil.domain.repository.DatabaseRepository
import com.sungil.domain.repository.NetworkRepository
import com.sungil.domain.tokenManger.TokenMangerController
import javax.inject.Inject

class UpdateDiet @Inject constructor(
    private val network: NetworkRepository,
    private val database: DatabaseRepository,
    private val tokenMangerController: TokenMangerController,
) : UseCase<UpdateDiet.Param, UpdateDiet.Result> {
    data class Param(
        val diet: String,
        val dietContent: String,
    ) : UseCase.Param

    sealed interface Result : UseCase.Result {
        data class Success(val message: String) : Result
        data class Fail(val errorMessage: String) : Result
    }

    override suspend fun invoke(param: Param): Result {
        val userData = database.getUserInfo()
        val token = database.getToken()
        val updateResult = network.requestUpdateDiet(
            accessToken = TOKEN_FORM + token.first,
            diet = if (param.dietContent.trim().isNotEmpty()) param.dietContent else param.diet
        )
        when (updateResult) {
            204 -> {
                userData.diet = param.diet
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
                return Result.Success("Success")
            }

            401 -> {
                val refreshToken = tokenMangerController.requestUpdateToken(token.second)
                if (!refreshToken) return Result.Fail("reLogin")
                val newToken = database.getToken()
                val reRequest = network.requestUpdateDiet(
                    accessToken = TOKEN_FORM + newToken.first,
                    diet = if (param.dietContent.trim()
                            .isNotEmpty()
                    ) param.dietContent else param.diet
                )
                if (reRequest != 204) {
                    return Result.Fail("Fail to update diet")
                }
                userData.diet = param.diet
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
                return Result.Success("Success")
            }
            else -> return Result.Fail("network error")
        }
    }
}