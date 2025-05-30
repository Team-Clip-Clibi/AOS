package com.sungil.domain.useCase

import com.sungil.domain.TOKEN_FORM
import com.sungil.domain.UseCase
import com.sungil.domain.repository.DatabaseRepository
import com.sungil.domain.repository.DeviceRepository
import com.sungil.domain.repository.NetworkRepository
import com.sungil.domain.tokenManger.TokenMangerController
import javax.inject.Inject

class UpdateNickName @Inject constructor(
    private val deviceRepo: DeviceRepository,
    private val networkRepo: NetworkRepository,
    private val database: DatabaseRepository,
    private val tokenMangerController: TokenMangerController,
) : UseCase<UpdateNickName.Param, UpdateNickName.Result> {
    data class Param(
        val name: String,
    ) : UseCase.Param

    sealed interface Result : UseCase.Result {
        data class Success(val message: String) : Result
        data class Fail(val message: String) : Result
    }

    override suspend fun invoke(param: Param): Result {
        val name = param.name
        if (name.length > 8) {
            deviceRepo.requestVibrate()
            return Result.Fail("to long")
        }
        if (name.length <= 2) {
            deviceRepo.requestVibrate()
            return Result.Fail("to short")
        }
        if (name.matches(Regex("[^a-zA-Z0-9가-힣]"))) {
            deviceRepo.requestVibrate()
            return Result.Fail("no special")
        }
        val token = database.getToken()
        val inputResult = networkRepo.inputNickName(name, TOKEN_FORM + token.first)
        when (inputResult) {
            204 -> {
                val userData = database.getUserInfo()
                val saveResult = database.saveUserInfo(
                    name = userData.userName,
                    nickName = name,
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
                return Result.Success("nick name update Success")
            }

            401 -> {
                val refreshToken = tokenMangerController.requestUpdateToken(token.second)
                if (!refreshToken) {
                    return Result.Fail("reLogin")
                }
                val newToken = database.getToken()
                val reRequest = networkRepo.inputNickName(name, TOKEN_FORM + newToken.first)
                if (reRequest != 204) {
                    return Result.Fail("update fail")
                }
                val userData = database.getUserInfo()
                val saveResult = database.saveUserInfo(
                    name = userData.userName,
                    nickName = name,
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
                return Result.Success("nick name update Success")
            }

            400 -> {
                return Result.Fail("update fail")
            }

            else -> return Result.Fail("network Error")
        }
    }
}
