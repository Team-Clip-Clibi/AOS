package com.sungil.domain.useCase

import com.sungil.domain.UseCase
import com.sungil.domain.repository.DatabaseRepository
import com.sungil.domain.repository.NetworkRepository
import javax.inject.Inject

class CheckAlreadySignUpNumber @Inject constructor(private val repo: NetworkRepository,private val database : DatabaseRepository) :
    UseCase<CheckAlreadySignUpNumber.Param, CheckAlreadySignUpNumber.Result> {
    data class Param(
        val phoneNumber: String,
    ) : UseCase.Param

    sealed interface Result : UseCase.Result {
        data class Success(val message: String) : Result
        data class Fail(val errorMessage: String) : Result
    }

    override suspend fun invoke(param: Param): Result {
        val editorNumber = if (param.phoneNumber.contains("-")) {
            param.phoneNumber.replace("-", "").trim()
        } else {
            param.phoneNumber.trim()
        }
        val token = database.getToken()
        if(token.first == null){
            return Result.Fail("token is null")
        }
        val resultCode = repo.checkAlreadySignUpNumber(editorNumber,"Bearer"+" "+token.first!!)
        if (resultCode == "200") {
            return Result.Fail("Already SignUp")
        }
        return Result.Success("okay SignUp")
    }

}