package com.example.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.mapper.toLoadResult
import com.sungil.database.token.TokenManager
import com.sungil.domain.TOKEN_FORM
import com.sungil.domain.exception.TokenSaveException
import com.sungil.domain.exception.UnauthorizedException
import com.sungil.domain.model.Notification
import com.sungil.network.http.HttpApi
import retrofit2.HttpException
import javax.inject.Inject

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
                    val refreshResponse = api.requestRefreshToken(refreshToken)

                    when {
                        !refreshResponse.isSuccessful -> {
                            LoadResult.Error(UnauthorizedException("serverError"))
                        }

                        refreshResponse.code() != 200 || refreshResponse.body() == null -> {
                            LoadResult.Error(UnauthorizedException("reLogin"))
                        }

                        !tokenManager.updateToken(
                            refreshResponse.body()!!.accessToken,
                            refreshResponse.body()!!.refreshToken
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

                else -> LoadResult.Error(HttpException(response))
            }

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}