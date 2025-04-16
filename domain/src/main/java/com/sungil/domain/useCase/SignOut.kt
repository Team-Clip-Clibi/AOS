package com.sungil.domain.useCase

import com.sungil.domain.TOKEN_FORM
import com.sungil.domain.UseCase
import com.sungil.domain.repository.DatabaseRepository
import com.sungil.domain.repository.NetworkRepository
import javax.inject.Inject

class SignOut @Inject constructor(
    private val network: NetworkRepository,
    private val database: DatabaseRepository,
) {
    sealed interface Result : UseCase.Result {
        data class Success(val message: String) : Result
        data class Fail(val errorMessage: String) : Result
    }

    suspend fun invoke(): Result {
        val deleteKakaoId = database.deleteKaKaoId()
        val deleteUserInfo = database.deleteUserIfo()
        val token = database.getToken()
        val requestSignOut = network.requestSignOut(
            TOKEN_FORM + token.second
        )
        if (requestSignOut != 204) {
            return Result.Fail("network error")
        }
        val deleteToken = database.removeToken()
        val signInActivate = database.saveSingUpKey(false)
        if (!deleteKakaoId || !deleteUserInfo || !deleteToken || !signInActivate) {
            return Result.Fail("delete fail")
        }
        return Result.Success("good bye")
    }
}