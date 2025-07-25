package com.example.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.mapper.toLoadResult
import com.sungil.database.token.TokenManager
import com.sungil.domain.TOKEN_FORM
import com.sungil.domain.exception.TokenSaveException
import com.sungil.domain.exception.UnauthorizedException
import com.sungil.network.http.HttpApi
import com.sungil.domain.model.Notification
import retrofit2.HttpException
import javax.inject.Inject

/**
 * 이미 읽은 알람 API 추가
 */
class NotificationReadPagingSource @Inject constructor(
    private val api: HttpApi,
    private val tokenManager: TokenManager,
) : PagingSource<String, Notification>() {
    override fun getRefreshKey(state: PagingState<String, Notification>): String? = null

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Notification> {
        return try {
            val (accessToken, refreshToken) = tokenManager.getToken()
            val response = when (params.key) {
                null -> api.requestReadNotify(TOKEN_FORM + accessToken)
                else -> api.requestReadNotifyNext(TOKEN_FORM + accessToken, params.key!!)
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
                                null -> api.requestReadNotify(TOKEN_FORM + refreshResponse.body()!!.accessToken)
                                else -> api.requestReadNotifyNext(
                                    TOKEN_FORM + refreshResponse.body()!!.accessToken,
                                    params.key!!
                                )
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