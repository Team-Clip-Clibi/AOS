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
    ) : UseCase.Param

    sealed interface Result : UseCase.Result {
        data class Success(val message: String) : Result
        data class Fail(val errorMessage: String) : Result
    }

    override suspend fun invoke(param: Param): Result {
        if (!param.birthYear.all { it.isDigit() } || !param.birthMonth.all { it.isDigit() } || !param.birthDay.all { it.isDigit() }) {
            return Result.Fail("Data is Not input")
        }
        val editBirthDay = reformBirth(param.birthYear, param.birthMonth, param.birthDay)
        val token = database.getToken()
        if (token.first == null || token.second == null) {
            return Result.Fail("Token is null")
        }
        val sendResult = network.inputUserDetail(
            token = TOKEN_FORM + token.first,
            gender = param.gender,
            birth = editBirthDay,
            city = param.city,
            county = param.county
        )
        if (sendResult != 204) {
            return Result.Fail("network error")
        }
        return Result.Success("okay detail")
    }

    private fun reformBirth(year: String, month: String, day: String): String {
        return "$year-$month-$day"
    }
}