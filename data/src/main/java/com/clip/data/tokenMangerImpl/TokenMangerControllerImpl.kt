package com.clip.data.tokenMangerImpl

import com.clip.domain.repository.DatabaseRepository
import com.clip.domain.repository.NetworkRepository
import com.clip.domain.tokenManger.TokenMangerController
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

class TokenMangerControllerImpl @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val databaseRepository: DatabaseRepository,
) : TokenMangerController {

    private val mutex = Mutex()
    @Volatile
    private var isRefreshing = false
    @Volatile
    private var lastRefreshSuccess = false

    override suspend fun requestUpdateToken(refreshToken: String): Boolean {
        if (isRefreshing) {
            return mutex.withLock { lastRefreshSuccess }
        }
        return mutex.withLock {
            if (isRefreshing) return@withLock lastRefreshSuccess
            isRefreshing = true
            val result = networkRepository.requestUpdateToken(refreshToken)
            lastRefreshSuccess = result.first == 200 &&
                    !result.second.isNullOrEmpty() &&
                    databaseRepository.setToken(result.second!!, result.third!!)
            isRefreshing = false
            lastRefreshSuccess
        }
    }
}