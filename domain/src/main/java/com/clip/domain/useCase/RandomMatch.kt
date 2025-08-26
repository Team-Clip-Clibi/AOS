package com.clip.domain.useCase

import com.clip.domain.UseCase
import com.clip.domain.repository.DatabaseRepository
import com.clip.domain.repository.NetworkRepository
import com.clip.domain.tokenManger.TokenMangerController
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