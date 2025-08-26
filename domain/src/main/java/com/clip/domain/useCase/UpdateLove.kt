package com.clip.domain.useCase

import com.clip.domain.TOKEN_FORM
import com.clip.domain.UseCase
import com.clip.domain.repository.DatabaseRepository
import com.clip.domain.repository.NetworkRepository
import javax.inject.Inject

class UpdateLove @Inject constructor(
    private val database: DatabaseRepository,
    private val network: NetworkRepository,
) : UseCase<UpdateLove.Param, UpdateLove.Result> {
    data class Param(
        val love: String,
        val relationShip: String,
    ) : UseCase.Param

    sealed interface Result : UseCase.Result {
        data class Success(val message: String) : Result
        data class Fail(val errorMessage: String) : Result
    }

    override suspend fun invoke(param: Param): Result {
        val token = database.getToken()
        val userData = database.getUserInfo()
        if (token.first == null || userData == null) {
            return Result.Fail("token is null")
        }
        val result = network.requestUpdateLoveState(
            TOKEN_FORM + token.first,
            love = param.love,
            relation = param.relationShip == "SAME"
        )
        if (result != 204) {
            return Result.Fail("Fail to update Love")
        }
        val relation = param.relationShip == "SAME"
        userData.loveState = Pair(param.love,relation)
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