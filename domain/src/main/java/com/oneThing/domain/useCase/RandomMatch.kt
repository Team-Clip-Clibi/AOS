package com.oneThing.domain.useCase

import com.oneThing.domain.UseCase
import com.oneThing.domain.repository.DatabaseRepository
import com.oneThing.domain.repository.NetworkRepository
import com.oneThing.domain.tokenManger.TokenMangerController
import javax.inject.Inject

class RandomMatch @Inject constructor(
    private val database: DatabaseRepository,
    private val tokenManger: TokenMangerController,
    private val networkRepository: NetworkRepository,
) : UseCase<RandomMatch.Param, RandomMatch.Result> {
    data class Param(
        val topic: String,
        val district: String,
        val tmiContent: String,
    ) : UseCase.Param

    sealed interface Result : UseCase.Result {
        data class Success(val matchData: RandomMatch) : Result
        data class Error(val errorMessage: String) : Result
    }

    override suspend fun invoke(param: Param): Result {
        TODO("Not yet implemented")
    }
}