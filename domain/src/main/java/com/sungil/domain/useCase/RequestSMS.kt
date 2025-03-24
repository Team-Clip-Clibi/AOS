package com.sungil.domain.useCase

import android.app.Activity
import com.sungil.domain.UseCase
import com.sungil.domain.repository.NetworkRepository
import javax.inject.Inject

class RequestSMS @Inject constructor(private val repo: NetworkRepository) :
    UseCase<RequestSMS.Param, RequestSMS.Result> {

    data class Param(
        val phoneNumber: String,
        val activity: Activity,
    ) : UseCase.Param

    sealed interface Result : UseCase.Result {
        data class Success(val message: String) : Result
        data class Fail(val errorMessage: String) : Result
    }

    override suspend fun invoke(param: Param): Result {
        val editPhoneNumber = if(param.phoneNumber.startsWith("0")){
            param.phoneNumber.drop(1)
        } else{
            param.phoneNumber
        }
        val result = repo.requestSMS(editPhoneNumber , param.activity)
        if(!result){
            return Result.Fail("Fail to request SMS")
        }
        return Result.Success("Success to request SMS")
    }
}