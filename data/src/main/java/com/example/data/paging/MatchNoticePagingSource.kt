package com.example.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.mapper.toLoadResult
import com.sungil.database.token.TokenManager
import com.sungil.domain.REFRESH_TOKEN
import com.sungil.domain.TOKEN_FORM
import com.sungil.domain.exception.UnauthorizedException
import com.sungil.domain.model.Job
import com.sungil.domain.model.MatchNotice
import com.sungil.network.http.HttpApi
import javax.inject.Inject

class MatchNoticePagingSource @Inject constructor(
    private val api: HttpApi,
    private val token: TokenManager,
    private val lastMeetingTime: String = "",
) : PagingSource<String, MatchNotice>() {
    override fun getRefreshKey(state: PagingState<String, MatchNotice>): String {
        return lastMeetingTime
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, MatchNotice> {
        val lastTime = params.key ?: lastMeetingTime
        val (accessToken, refreshToken) = token.getToken()
        val response = api.requestMatchNotice(
            bearerToken = TOKEN_FORM + accessToken,
            lastMeetingTime = lastTime
        )
        if (!response.isSuccessful) {
            return LoadResult.Error(UnauthorizedException("network error"))
        }
        when (response.code()) {
            200 -> return response.toLoadResult(currentKey = lastTime)
            204 -> {
                //TODO 배포 시 삭제
                val testData = listOf(
                    MatchNotice(
                        matchId = 0,
                        matchTime = "2025-08-04T05:51:35.926Z",
                        matchStatus = "CONFIRMED",
                        matchType = "ONE_THING",
                        restaurantName = "한옥마루 강남점",
                        restaurantAddress = "서울 강남구 강남대로 106번길 25",
                        menuCategory = "양식(파스타 및 피자)",
                        jonInfos = listOf(
                            Job(
                                "학생",
                                1
                            ),
                            Job(
                                "의료인",
                                2
                            ), Job(
                                "예술계",
                                1
                            )
                        ),
                        diet = listOf(
                            "비건이에요",
                            "비건이에요",
                            "비건이에요",
                            "베지테리언이에요",
                            "글루텐 프리를 지켜요",
                            "해산물을 별로 안좋아해요",
                            "견과류 알레르기가 있어요"
                        ),
                        category = "면접 준비는 어떻게 하고 계시나요? 주제 입력 최대 50자 까지라, 세줄 까지는 나올 수 있을 듯요"
                    ),
                    MatchNotice(
                        matchId = 0,
                        matchTime = "2025-08-05T05:51:35.926Z",
                        matchStatus = "CONFIRMED",
                        matchType = "RANDOM",
                        restaurantName = "한옥마루 강남점",
                        restaurantAddress = "서울 강남구 강남대로 106번길 25",
                        menuCategory = "양식(파스타 및 피자)",
                        jonInfos = listOf(
                            Job(
                                "학생",
                                1
                            ),
                            Job(
                                "의료인",
                                2
                            ), Job(
                                "예술계",
                                1
                            )
                        ),
                        diet = listOf(
                            "비건이에요",
                        ),
                        category = "지난 안내문은 버튼만 없습니당"
                    ),
                )
                return LoadResult.Page(
                    data = testData,
                    prevKey = null,
                    nextKey = null
                )
            }

            401 -> {
                val requestRefreshToken =
                    api.requestRefreshToken(mapOf(REFRESH_TOKEN to refreshToken))
                if (!requestRefreshToken.isSuccessful) {
                    return LoadResult.Error(UnauthorizedException("network error"))
                }
                val updateToken =
                    token.updateToken(requestRefreshToken.body()!!.accessToken, refreshToken)
                if (!updateToken) return LoadResult.Error(UnauthorizedException("reLogin"))
                val newToken = token.getToken()
                val reRequest = api.requestMatchNotice(
                    bearerToken = TOKEN_FORM + newToken.first,
                    lastMeetingTime = lastTime
                )
                if (!reRequest.isSuccessful) return LoadResult.Error(UnauthorizedException("network error"))
                if (reRequest.code() == 200) {
                    return reRequest.toLoadResult(currentKey = lastTime)
                }
                if (reRequest.code() == 204) {
                    return LoadResult.Page(
                        data = emptyList(),
                        prevKey = null,
                        nextKey = null
                    )
                }
                return LoadResult.Error(UnauthorizedException("network error"))
            }

            else -> return LoadResult.Error(UnauthorizedException("network error"))
        }
    }
}