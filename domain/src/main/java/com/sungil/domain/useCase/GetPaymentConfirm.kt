package com.sungil.domain.useCase

import com.sungil.domain.TOKEN_FORM
import com.sungil.domain.UseCase
import com.sungil.domain.repository.DatabaseRepository
import com.sungil.domain.repository.NetworkRepository
import com.sungil.domain.tokenManger.TokenMangerController
import javax.inject.Inject

class GetPaymentConfirm @Inject constructor(
    private val database: DatabaseRepository,
    private val network: NetworkRepository,
    private val tokenManger: TokenMangerController
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
                val refreshToken = tokenManger.requestUpdateToken(token.second)
                if (!refreshToken) return Result.Fail("reLogin")
                val newToken = database.getToken()
                val reRequest = network.requestPayConfirm(
                    token = TOKEN_FORM + newToken.first,
                    paymentKey = param.paymentKey,
                    orderId = param.orderId,
                    orderType = param.orderType
                )
                if (reRequest == 200) {
                    return Result.Success("Success to pay")
                }
                return Result.Fail("network error")
            }

            else -> return Result.Fail("network error")
        }
    }
}