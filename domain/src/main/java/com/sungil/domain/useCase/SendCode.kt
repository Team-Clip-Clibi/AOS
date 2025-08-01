package com.sungil.domain.useCase

import android.net.Network
import com.sungil.domain.UseCase
import com.sungil.domain.repository.NetworkRepository
import javax.inject.Inject

class SendCode @Inject constructor(private val repo: NetworkRepository) :
    UseCase<SendCode.Param, SendCode.Result> {

    data class Param(
        val smsCode: String,
    ) : UseCase.Param

    sealed interface Result : UseCase.Result {
        data class Success(val message: String) : Result
        data class Fail(val errorMessage: String) : Result
    }

    override suspend fun invoke(param: Param): Result {
        return try {
            repo.verifySMS(param.smsCode)
            Result.Success("send Success")
        } catch (e: Exception) {
            Result.Fail("send Fail")
        }
    }

}