package com.sungil.domain.useCase

import com.sungil.domain.TOKEN_FORM
import com.sungil.domain.UseCase
import com.sungil.domain.repository.DatabaseRepository
import com.sungil.domain.repository.NetworkRepository
import javax.inject.Inject

class UpdateLanguage @Inject constructor(
    private val database: DatabaseRepository,
    private val network: NetworkRepository,
) : UseCase<UpdateLanguage.Param, UpdateLanguage.Result> {
    data class Param(
        val language: String,
    ) : UseCase.Param

    sealed interface Result : UseCase.Result {
        data class Success(val message: String) : Result
        data class Fail(val errorMessage: String) : Result
    }

    override suspend fun invoke(param: Param): Result {
        val token = database.getToken()
        val userData = database.getUserInfo()
        val updateResult = network.requestUpdateLanguage(
            TOKEN_FORM + token.first,
            param.language
        )
        if (updateResult != 204) {
            return Result.Fail("network Error")
        }
        userData.language = param.language
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
}