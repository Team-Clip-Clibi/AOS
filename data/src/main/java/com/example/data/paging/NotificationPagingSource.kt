package com.example.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sungil.database.token.TokenManager
import com.sungil.domain.TOKEN_FORM
import com.sungil.domain.model.Notification
import com.sungil.network.http.HttpApi
import retrofit2.HttpException
import javax.inject.Inject

class NotificationPagingSource @Inject constructor(
    private val api: HttpApi,
    private val tokenManger: TokenManager,
) : PagingSource<String, Notification>() {
    override fun getRefreshKey(state: PagingState<String, Notification>): String? = null

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Notification> {
        return try {
            val token = tokenManger.getToken()
            val response = if (params.key == null) {
                api.requestNotify(TOKEN_FORM + token.first)
            } else {
                api.requestNewNotify(TOKEN_FORM + token.first, params.key!!)
            }
            if (!response.isSuccessful) {
                return LoadResult.Error(HttpException(response))
            }
            return when (response.code()) {
                401 -> {
                    val newToken = api.requestRefreshToken(token.second)
                    if(!newToken.isSuccessful){
                        return LoadResult.Error(HttpException(newToken))
                    }
                    if(newToken.code() != 200){
                        return LoadResult.Error(//TODO 해당 부분에서 사용자에게 재 로그인 하라는 메세지를 View에 전달해야함)
                    }
                    val saveTokenResult = tokenManger.updateToken(newToken.body()!!.accessToken , newToken.body()!!.refreshToken)
                    if(!saveTokenResult){
                        return LoadResult.Error(//TODO 사용자에게 앱 에러 상황을 알려야함)
                    }
                    val retry = if(params.key == null){
                        api.requestNotify(TOKEN_FORM + newToken.body()!!.accessToken)
                    }else {
                        api.requestNewNotify(TOKEN_FORM + newToken.body()!!.accessToken, params.key!!)
                    }
                    return retry.toLoadResult()
                }
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}