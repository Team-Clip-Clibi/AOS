package com.sungil.domain.useCase

import com.sungil.domain.TOKEN_FORM
import com.sungil.domain.UseCase
import com.sungil.domain.repository.DatabaseRepository
import com.sungil.domain.repository.NetworkRepository
import javax.inject.Inject

class SendUserDetail @Inject constructor(
    private val database: DatabaseRepository,
    private val network: NetworkRepository,
) : UseCase<SendUserDetail.Param, SendUserDetail.Result> {

    data class Param(
        val gender: String,
        val birthYear: String,
        val birthMonth: String,
        val birthDay: String,
        val city: String,
        val county: String,
        val cityDisplayName: String,
        val countyDisplayName: String,
        val marketingPermission: Boolean,
        val name: String,
        val nickName: String,
        val phoneNumber: String,
    ) : UseCase.Param

    sealed interface Result : UseCase.Result {
        data class Success(val message: String) : Result
        data class Fail(val errorMessage: String) : Result
    }

    override suspend fun invoke(param: Param): Result {
        val year = param.birthYear.filter { it.isDigit() }
        val month = param.birthMonth.filter { it.isDigit() }.toIntOrNull()?.let {
            if (it < 10) "0$it" else "$it"
        } ?: ""
        val day = param.birthDay.filter { it.isDigit() }

        if (year.isEmpty() || month.isEmpty() || day.isEmpty()) {
            return Result.Fail("Data is Not input")
        }
        val editBirthDay = reformBirth(year, month, day)
        val token = database.getToken()
        val sendResult = network.inputUserDetail(
            token = TOKEN_FORM + token.first,
            gender = param.gender,
            birth = editBirthDay,
            city = param.city,
            county = param.county
        )
        if(sendResult == 401){
            return Result.Fail("reLogin")
        }
        if (sendResult != 204) {
            return Result.Fail("network error")
        }
        val saveResult = database.saveUserInfo(
            name = param.name,
            nickName = param.nickName,
            platform = "KAKAO",
            phoneNumber = param.phoneNumber,
            jobList = Pair("NONE" ,"NONE"),
            loveState = Pair("NONE" , false),
            diet = "NONE",
            language = "KOREAN"
        )
        if (!saveResult) {
            return Result.Fail("Save Fail")
        }
        val saveSignUpResult = database.saveSingUpKey(true)
        if(!saveSignUpResult){
            return Result.Fail("Save SignUp Result")
        }
        return Result.Success("okay detail")
    }

    private fun reformBirth(year: String, month: String, day: String): String {
        return "$year-$month-$day"
    }
}