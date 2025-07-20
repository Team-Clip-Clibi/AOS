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
                    204 ,400 ->{
                        //TODO 배포 이전까지 사용
                        val dummyMatchProgress = MatchProgressUiModel(
                            nickName = listOf(
                                "김성일", "윤동주", "오현식", "장정우", "장세은", "김병진", "하서은", "신민선"
                            ),
                            tmi = listOf(
                                "데이팅앱을 운영하고 있어요",
                                "축구를 좋아해요",
                                "영국 어학연수 가는 것이 목표에요",
                                "제주도 스탭을 했었어요",
                                "제주도 스탭을 했었어요",
                                "축구를 좋아해요",
                                "피자를 좋아해요",
                                "치킨을 좋아해요"
                            ),
                            content = listOf(
                                OneThingMap("김성일", "매일 아침 6시에 일어나 책을 읽고 있어요"),
                                OneThingMap("윤동주", "시간이 날 때마다 별을 바라보며 생각을 정리해요"),
                                OneThingMap("오현식", "최근에 러닝 5km 챌린지를 완주했어요"),
                                OneThingMap("장정우", "주말마다 수채화 그림을 그려요"),
                                OneThingMap("장세은", "매달 가계부를 쓰며 소비를 관리하고 있어요"),
                                OneThingMap("김병진", "스타트업에서 UX 디자이너로 일하고 있어요"),
                                OneThingMap("하서은", "좋은 대화가 좋은 관계를 만든다고 생각해요"),
                                OneThingMap("신민선", "아직은 어떤 목표를 정하지 않았어요")
                            )
                        )
                        return Result.Success(dummyMatchProgress)
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
