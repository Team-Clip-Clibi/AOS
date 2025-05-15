package com.sungil.domain.useCase

import com.sungil.domain.UseCase
import com.sungil.domain.repository.DatabaseRepository
import com.sungil.domain.repository.NetworkRepository
import javax.inject.Inject

class OneThingMatchOrder @Inject constructor(
    private val database: DatabaseRepository,
    private val network: NetworkRepository,
) : UseCase<OneThingMatchOrder.Param, OneThingMatchOrder.Result> {
    data class Param(
        val topic: String,
        val districts: String,
        val date: String,
        val timeSlot: String,
        val tmiContent: String,
        val oneThingBudgetRange: String,
    ) : UseCase.Param

    sealed interface Result : UseCase.Result {
        data class Success(val orderId: String) : Result
        data class Fail(val errorMessage: String) : Result
    }

    /**
     * 이제 Order API 호출 하자 ^^
     */
    override suspend fun invoke(param: Param): Result {
        val userId : String = database.getKaKaoId()
        if(userId.trim() == ""){
            return Result.Fail("user id is null")
        }

    }
}