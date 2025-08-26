package com.clip.domain.useCase

import com.clip.domain.TOKEN_FORM
import com.clip.domain.UseCase
import com.clip.domain.model.NetworkResult
import com.clip.domain.model.Participants
import com.clip.domain.repository.DatabaseRepository
import com.clip.domain.repository.NetworkRepository
import com.clip.domain.tokenManger.TokenMangerController
import javax.inject.Inject

class GetParticipants @Inject constructor(
    private val database: DatabaseRepository,
    private val network: NetworkRepository,
    private val tokenMangerController: TokenMangerController,
) : UseCase<GetParticipants.Param, GetParticipants.Result> {
    data class Param(
        val matchId: Int,
        val matchType: String,
    ) : UseCase.Param

    sealed interface Result : UseCase.Result {
        data class Success(val person: List<Participants>) : Result
        data class Fail(val errorMessage: String) : Result
    }

    override suspend fun invoke(param: Param): Result {
        val token = database.getToken()
        val response = network.requestParticipants(
            token = TOKEN_FORM + token.first,
            matchType = param.matchType,
            matchId = param.matchId
        )
        when (response) {
            is NetworkResult.Error -> {
                when (response.code) {
                    401 -> {
                        val refreshToken = tokenMangerController.requestUpdateToken(token.second)
                        if (!refreshToken) return Result.Fail("reLogin")
                        val newToken = database.getToken()
                        val reRequest = network.requestParticipants(
                            token = TOKEN_FORM + newToken.first,
                            matchType = param.matchType,
                            matchId = param.matchId
                        )
                        if (reRequest is NetworkResult.Success) {
                            return Result.Success(reRequest.data)
                        }
                        return Result.Fail("network error")
                    }
                    400 , 200 ->{
                        /**
                         * TODO 가데이터 적용 배포 시 삭제
                         */
                        val sampleData = listOf(
                            Participants(
                                id = 1,
                                nickName = "Assist"
                            ),
                            Participants(
                                id = 2,
                                nickName = "Assist2"
                            ),
                            Participants(
                                id = 3,
                                nickName = "Assist3"
                            )
                        )
                        return Result.Success(sampleData)
                    }
                    else -> return Result.Fail("network error")
                }
            }

            is NetworkResult.Success -> return Result.Success(response.data)
        }
    }
}