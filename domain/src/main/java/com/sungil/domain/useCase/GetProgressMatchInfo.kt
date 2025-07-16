package com.sungil.domain.useCase

import com.sungil.domain.TOKEN_FORM
import com.sungil.domain.UseCase
import com.sungil.domain.model.MatchProgress
import com.sungil.domain.model.MatchProgressUiModel
import com.sungil.domain.model.NetworkResult
import com.sungil.domain.model.OneThingMap
import com.sungil.domain.repository.DatabaseRepository
import com.sungil.domain.repository.NetworkRepository
import com.sungil.domain.tokenManger.TokenMangerController
import javax.inject.Inject

class GetProgressMatchInfo @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val databaseRepository: DatabaseRepository,
    private val tokenMangerController: TokenMangerController,
) : UseCase<GetProgressMatchInfo.Param, GetProgressMatchInfo.Result> {
    data class Param(
        val matchId: Int,
        val matchType: String,
    ) : UseCase.Param

    sealed interface Result : UseCase.Result {
        data class Success(val data: MatchProgressUiModel) : Result
        data class Fail(val errorMessage: String) : Result
    }

    override suspend fun invoke(param: Param): Result {
        val token = databaseRepository.getToken()
        val request = networkRepository.requestProgressMatch(
            token = TOKEN_FORM + token.first,
            matchId = param.matchId,
            matchType = param.matchType
        )
        when (request) {
            is NetworkResult.Error -> {
                when (request.code) {
                    401 -> {
                        val refreshToken = tokenMangerController.requestUpdateToken(token.second)
                        if (!refreshToken) return Result.Fail("reLogin")
                        val newToken = databaseRepository.getToken()
                        val reRequest = networkRepository.requestProgressMatch(
                            token = TOKEN_FORM + newToken.first,
                            matchId = param.matchId,
                            matchType = param.matchType
                        )
                        if (reRequest is NetworkResult.Success) {
                            return Result.Success(reRequest.data.toMatchProgressUi())
                        }
                        return Result.Fail("network error")
                    }

                    else -> return Result.Fail("network error")
                }
            }

            is NetworkResult.Success -> return Result.Success(request.data.toMatchProgressUi())
        }
    }

    private fun MatchProgress.toMatchProgressUi(): MatchProgressUiModel {
        return MatchProgressUiModel(
            nickName = this.nicknameList,
            tmi = this.tmiList,
            content = this.nicknameOnethingMap.map { (nick, content) ->
                OneThingMap(
                    nickName = nick,
                    content = content
                )
            }
        )
    }
}
