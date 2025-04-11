package com.sungil.domain.useCase

import com.sungil.domain.UseCase
import com.sungil.domain.repository.DatabaseRepository
import javax.inject.Inject

class SaveChangeProfileData @Inject constructor(private val database: DatabaseRepository) :
    UseCase<SaveChangeProfileData.Param, SaveChangeProfileData.Result> {
    data class Param(
        val nickName: String,
        val job: Pair<String, String>,
        val loveState: Pair<String, Boolean>,
        val diet: String,
        val language: String,
    ) : UseCase.Param

    sealed interface Result : UseCase.Result {
        data class Success(val message: String) : Result
        data class Fail(val errorMessage: String) : Result
    }

    override suspend fun invoke(param: Param): Result {
        val beforeUserData = database.getUserInfo() ?: return Result.Fail("data is null")
        beforeUserData.nickName = param.nickName
        beforeUserData.job = param.job
        beforeUserData.loveState = param.loveState
        beforeUserData.diet = param.diet
        beforeUserData.language = param.language
        val saveResult = database.saveUserInfo(
            name = beforeUserData.userName,
            nickName = beforeUserData.nickName,
            platform = "KAKAO",
            phoneNumber = beforeUserData.phoneNumber,
            jobList = beforeUserData.job,
            loveState = beforeUserData.loveState,
            diet = beforeUserData.diet,
            language = beforeUserData.language
        )
        if (!saveResult) {
            return Result.Fail("save Fail")
        }
        return Result.Success("save Success")
    }
}