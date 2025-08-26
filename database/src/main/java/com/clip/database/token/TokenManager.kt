package com.clip.database.token

interface TokenManager  {

    suspend fun getToken() : Pair<String, String>
    suspend fun updateToken(accessToken : String , refreshToken : String) : Boolean
    suspend fun clearToken() : Boolean
    suspend fun isTokenNull() : Boolean
}