package com.example.data.paging

import com.sungil.database.token.TokenManager
import com.sungil.network.http.HttpApi
import javax.inject.Inject

class MatchPageSource @Inject constructor(
    private val api: HttpApi,
    private val tokenManger: TokenManager,
) {
}