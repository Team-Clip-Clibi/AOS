package com.clip.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.clip.data.mapper.toLoadResult
import com.clip.database.token.TokenManager
import com.clip.domain.REFRESH_TOKEN
import com.clip.domain.TOKEN_FORM
import com.clip.domain.exception.UnauthorizedException
import com.clip.domain.model.MatchingData
import com.clip.network.http.HttpApi
import javax.inject.Inject

class MatchPagingSource @Inject constructor(
    private val api: HttpApi,
    private val token: TokenManager,
    private val matchingStatus: String,
    private val lastMeetingTime: String? = null,
) : PagingSource<String, MatchingData>() {

    override fun getRefreshKey(state: PagingState<String, MatchingData>): String? {
        return lastMeetingTime
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, MatchingData> {
        val lastTime = params.key ?: lastMeetingTime!!
        val (accessToken, refreshToken) = token.getToken()
        val response = api.requestMatchingData(
            bearerToken = TOKEN_FORM + accessToken,
            matchingStatus = matchingStatus,
            lastMeetingTime = lastTime
        )
        if (!response.isSuccessful) {
            return LoadResult.Error(UnauthorizedException("network error"))
        }
        when (response.code()) {
            200 -> return response.toLoadResult()
            204 -> {
                val sampleMatchingDataList = listOf(
                    MatchingData(
                        id = 1234,
                        meetTime = "2025-06-27T02:52:30.147Z",
                        matchingStatusName = "APPLIED",
                        matchingType = "ONETHING",
                        myOneThingContent = "개발자 커피챗 모임",
                        isReviewWritten = false
                    ),
                    MatchingData(
                        id = 2345,
                        meetTime = "2025-06-26T19:10:00.000Z",
                        matchingStatusName = "CONFIRMED",
                        matchingType = "RANDOM",
                        myOneThingContent = "AI 스터디 번개",
                        isReviewWritten = true
                    ),
                    MatchingData(
                        id = 3456,
                        meetTime = "2025-06-25T08:00:00.000Z",
                        matchingStatusName = "COMPLETED",
                        matchingType = "ONETHING",
                        myOneThingContent = "프론트엔드 기술 공유",
                        isReviewWritten = false
                    )
                )
               return LoadResult.Page(
                    data = sampleMatchingDataList,
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
                if (requestRefreshToken.body() == null) {
                    return LoadResult.Error(UnauthorizedException("network error"))
                }
                val updateToken =
                    token.updateToken(requestRefreshToken.body()!!.accessToken, refreshToken)
                if (!updateToken) return LoadResult.Error(UnauthorizedException("reLogin"))
                val newToken = token.getToken()
                val reRequest = api.requestMatchingData(
                    bearerToken = TOKEN_FORM + newToken.first,
                    matchingStatus = matchingStatus,
                    lastMeetingTime = lastTime
                )
                if (!reRequest.isSuccessful) return LoadResult.Error(UnauthorizedException("network error"))
                if (reRequest.code() == 200) {
                    return reRequest.toLoadResult()
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