package com.sungil.database.token

import com.sungil.database.room.dao.TokenDao
import com.sungil.database.room.model.Token
import javax.inject.Inject

class TokenManagerImpl @Inject constructor(private val tokenDao: TokenDao) : TokenManager {
    private var token: Pair<String, String>? = null

    override suspend fun getToken(): Pair<String, String> {
        if (token == null) {
            val dbToken =
                tokenDao.getToken() ?: throw IllegalArgumentException("token data is null")
            token = Pair(dbToken.accessToken, dbToken.refreshToken)
            return Pair(dbToken.accessToken, dbToken.refreshToken)
        }
        return token!!
    }

    override suspend fun updateToken(accessToken: String, refreshToken: String): Boolean {
        return try {
            token = Pair(accessToken, refreshToken)
            tokenDao.insertToken(Token(accessToken = accessToken, refreshToken = refreshToken))
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun clearToken(): Boolean {
        return try {
            token = null
            tokenDao.deleteAll()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}