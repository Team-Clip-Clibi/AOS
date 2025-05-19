package com.sungil.domain.useCase

import com.sungil.domain.TOKEN_FORM
import com.sungil.domain.UseCase
import com.sungil.domain.repository.DatabaseRepository
import com.sungil.domain.repository.NetworkRepository
import javax.inject.Inject

class GetPaymentConfirm @Inject constructor(
    private val database: DatabaseRepository,
    private val network: NetworkRepository,
) : UseCase<GetPaymentConfirm.Param, GetPaymentConfirm.Result> {
    data class Param(
        val paymentKey: String,
        val orderId: String,
        val orderType: String,
    ) : UseCase.Param

    sealed interface Result : UseCase.Result {
        data class Success(val message: String) : Result
        data class Fail(val errorMessage: String) : Result
    }

    override suspend fun invoke(param: Param): Result {
        val token = database.getToken()
        val result = network.requestPayConfirm(
            token = TOKEN_FORM + token.first,
            paymentKey = param.paymentKey,
            orderId = param.orderId,
            orderType = param.orderType
        )
        when (result) {
            200 -> return Result.Success("Success to pay")
            401 -> {
                val refreshToken = network.requestUpdateToken(token.second)
                if (refreshToken.first != 200) {
                    return Result.Fail("reLogin")
                }
                val saveToken = database.setToken(refreshToken.second!!, refreshToken.third!!)
                if (!saveToken) {
                    return Result.Fail("save error")
                }
                val reRequest = network.requestPayConfirm(
                    token = TOKEN_FORM + refreshToken.second,
                    orderId = param.orderId,
                    orderType = param.orderType,
                    paymentKey = param.paymentKey
                )
                if (reRequest != 200) {
                    return Result.Fail("network error")
                }
                return Result.Success("Success to pay")
            }

            else -> return Result.Fail("network error")
        }
    }
}