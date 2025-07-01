package com.example.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.mapper.toLoadResult
import com.sungil.database.token.TokenManager
import com.sungil.domain.REFRESH_TOKEN
import com.sungil.domain.TOKEN_FORM
import com.sungil.domain.exception.UnauthorizedException
import com.sungil.domain.model.MatchNotice
import com.sungil.network.http.HttpApi
import javax.inject.Inject

class MatchNoticePagingSource @Inject constructor(
    private val api: HttpApi,
    private val token: TokenManager,
    private val lastMeetingTime: String = "",
) : PagingSource<String, MatchNotice>() {
    override fun getRefreshKey(state: PagingState<String, MatchNotice>): String? {
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
            204 -> return LoadResult.Page(
                data = emptyList(),
                prevKey = null,
                nextKey = null
            )

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