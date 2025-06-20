package com.example.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.mapper.toLoadResult
import com.sungil.database.token.TokenManager
import com.sungil.domain.TOKEN_FORM
import com.sungil.domain.exception.UnauthorizedException
import com.sungil.domain.model.MatchingData
import com.sungil.network.http.HttpApi
import com.sungil.network.model.MatchingOrder
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
            matchingOrder = MatchingOrder(
                matchingStatus = matchingStatus,
                lastMeetingTime = lastTime
            )
        )
        if (!response.isSuccessful) {
            return LoadResult.Error(UnauthorizedException("network error"))
        }
        when (response.code()) {
            200 -> return response.toLoadResult()
            204 -> return LoadResult.Page(
                data = emptyList(),
                prevKey = null,
                nextKey = null
            )

            401 -> {
                val requestRefreshToken = api.requestRefreshToken(refreshToken)
                if(!requestRefreshToken.isSuccessful){
                    return LoadResult.Error(UnauthorizedException("network error"))
                }
                if(requestRefreshToken.body() == null){
                    return LoadResult.Error(UnauthorizedException("network error"))
                }
                val updateToken = token.updateToken(requestRefreshToken.body()!!.accessToken , refreshToken)
                if (!updateToken) return LoadResult.Error(UnauthorizedException("reLogin"))
                val newToken = token.getToken()
                val reRequest = api.requestMatchingData(
                    bearerToken = TOKEN_FORM + newToken.first,
                    matchingOrder = MatchingOrder(
                        matchingStatus = matchingStatus,
                        lastMeetingTime = lastTime
                    )
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