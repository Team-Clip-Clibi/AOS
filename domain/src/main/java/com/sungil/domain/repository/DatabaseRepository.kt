package com.sungil.domain.repository

interface DatabaseRepository {
    suspend fun saveToken(token: String, refreshToken: String): Boolean
    suspend fun getToken(): Pair<String, String>
    suspend fun deleteToken(): Boolean
}