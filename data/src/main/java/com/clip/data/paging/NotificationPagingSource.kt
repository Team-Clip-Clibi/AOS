package com.clip.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.clip.data.mapper.toLoadResult
import com.clip.database.token.TokenManager
import com.clip.domain.TOKEN_FORM
import com.clip.domain.exception.TokenSaveException
import com.clip.domain.exception.UnauthorizedException
import com.clip.domain.model.Notification
import com.clip.network.http.HttpApi
import retrofit2.HttpException
import javax.inject.Inject

/**
 * 새로운 알람 조회 Paging
 */
class NotificationPagingSource @Inject constructor(
    private val api: HttpApi,
    private val tokenManager: TokenManager,
) : PagingSource<String, Notification>() {

    override fun getRefreshKey(state: PagingState<String, Notification>): String? = null

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Notification> {
        return try {
            val (accessToken, refreshToken) = tokenManager.getToken()
            val response = when (params.key) {
                null -> api.requestNotify(TOKEN_FORM + accessToken)
                else -> api.requestNewNotify(TOKEN_FORM + accessToken, params.key!!)
            }

            when (response.code()) {
                200 -> response.toLoadResult()
                401 -> {
                    val refreshResponse = api.requestRefreshToken(mapOf("refreshToken" to refreshToken))

                    when {
                        !refreshResponse.isSuccessful -> {
                            LoadResult.Error(UnauthorizedException("serverError"))
                        }

                        refreshResponse.code() != 200 || refreshResponse.body() == null -> {
                            LoadResult.Error(UnauthorizedException("reLogin"))
                        }

                        !tokenManager.updateToken(
                            accessToken = refreshResponse.body()!!.accessToken,
                            refreshToken = refreshToken
                        ) -> {
                            LoadResult.Error(TokenSaveException("save error"))
                        }

                        else -> {
                            val retry = when (params.key) {
                                null -> api.requestNotify(TOKEN_FORM + refreshResponse.body()!!.accessToken)
                                else -> api.requestNewNotify(TOKEN_FORM + refreshResponse.body()!!.accessToken, params.key!!)
                            }

                            if (retry.isSuccessful) {
                                retry.toLoadResult()
                            } else {
                                LoadResult.Error(HttpException(retry))
                            }
                        }
                    }
                }
                204 -> LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
                else -> LoadResult.Error(HttpException(response))
            }

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}