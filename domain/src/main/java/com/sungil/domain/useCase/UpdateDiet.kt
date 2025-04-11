package com.sungil.domain.useCase

import com.sungil.domain.TOKEN_FORM
import com.sungil.domain.UseCase
import com.sungil.domain.repository.DatabaseRepository
import com.sungil.domain.repository.NetworkRepository
import javax.inject.Inject

class UpdateDiet @Inject constructor(
    private val network: NetworkRepository,
    private val database: DatabaseRepository,
) : UseCase<UpdateDiet.Param, UpdateDiet.Result> {
    data class Param(
        val diet: String,
    ) : UseCase.Param

    sealed interface Result : UseCase.Result {
        data class Success(val message: String) : Result
        data class Fail(val errorMessage: String) : Result
    }

    override suspend fun invoke(param: Param): Result {
        val userData = database.getUserInfo() ?: return Result.Fail("userData is null")
        val token = database.getToken()
        if (token.first == null) {
            return Result.Fail("token is null")
        }
        val updateResult = network.requestUpdateDiet(
            TOKEN_FORM + token.first,
            param.diet
        )
        if (updateResult != 204) {
            return Result.Fail("network error")
        }
        userData.diet = param.diet
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
        if (!saveResult) {
            return Result.Fail("Save Fail")
        }
        return Result.Success("Success")
    }
}