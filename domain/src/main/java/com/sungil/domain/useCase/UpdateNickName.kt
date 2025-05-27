package com.sungil.domain.useCase

import com.sungil.domain.TOKEN_FORM
import com.sungil.domain.UseCase
import com.sungil.domain.repository.DatabaseRepository
import com.sungil.domain.repository.DeviceRepository
import com.sungil.domain.repository.NetworkRepository
import javax.inject.Inject

class UpdateNickName @Inject constructor(
    private val deviceRepo: DeviceRepository,
    private val networkRepo: NetworkRepository,
    private val database: DatabaseRepository,
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
                val reFreshToken = networkRepo.requestUpdateToken(token.second)
                if (reFreshToken.first != 200) {
                    return Result.Fail("reLogin")
                }
                val saveToken = database.setToken(reFreshToken.second!!, reFreshToken.third!!)
                if (!saveToken) {
                    return Result.Fail("save error")
                }
                val reRequest = networkRepo.inputNickName(name, TOKEN_FORM + reFreshToken.second)
                if (reRequest != 204) {
                    return Result.Fail("update fail")
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
