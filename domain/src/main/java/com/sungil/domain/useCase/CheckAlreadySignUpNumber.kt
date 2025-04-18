package com.sungil.domain.useCase

import com.sungil.domain.TOKEN_FORM
import com.sungil.domain.UseCase
import com.sungil.domain.repository.DatabaseRepository
import com.sungil.domain.repository.NetworkRepository
import java.time.LocalDateTime
import java.time.ZonedDateTime
import javax.inject.Inject

class CheckAlreadySignUpNumber @Inject constructor(
    private val repo: NetworkRepository,
    private val database: DatabaseRepository,
) :
    UseCase<CheckAlreadySignUpNumber.Param, CheckAlreadySignUpNumber.Result> {
    data class Param(
        val phoneNumber: String,
    ) : UseCase.Param

    sealed interface Result : UseCase.Result {
        data class Success(val message: String) : Result
        data class Fail(
            val errorMessage: String,
            val userName: String = "",
            val platform: String = "",
            val createdAt: String = "",
        ) : Result
    }

    override suspend fun invoke(param: Param): Result {
        val editorNumber = param.phoneNumber.replace(Regex("[^0-9]"), "").trim()
        val token = database.getToken()
        val resultCode = repo.checkAlreadySignUpNumber(editorNumber, TOKEN_FORM + token.first)
        if (resultCode.code == 200) {
            return Result.Fail(
                "Already SignUp",
                userName = maskName(resultCode.userName!!),
                platform = reformPlatform(resultCode.platform!!),
                createdAt = extractDateOnly(resultCode.createdAt!!)
            )
        }
        val inputResultCode = repo.inputPhoneNumber(editorNumber, TOKEN_FORM + token.first)
        if (inputResultCode != 204) {
            return Result.Fail("network error")
        }
        return Result.Success("okay SignUp")
    }

    private fun maskName(name: String): String {
        return if (name.length >= 3) {
            name.first() + "*" + name.last()
        } else if (name.length == 2) {
            name.first() + "*"
        } else {
            name
        }
    }

    private fun extractDateOnly(createdAt: String): String {
        return try {
            LocalDateTime.parse(createdAt).toLocalDate().toString()
        } catch (e: Exception) {
            try {
                ZonedDateTime.parse(createdAt).toLocalDate().toString()
            } catch (e: Exception) {
                createdAt

            }
        }
    }

    private fun reformPlatform(data: String): String {
        if (data == "KAKAO") {
            return "카카오톡"
        }
        return data
    }
}