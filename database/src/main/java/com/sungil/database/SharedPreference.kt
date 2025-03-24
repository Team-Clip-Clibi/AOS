package com.sungil.database

interface SharedPreference {

    suspend fun saveKaKaoId(data: String): Boolean
    suspend fun getKaKaoId(): String
    suspend fun deleteToken(): Boolean
    suspend fun saveSignUp(data: Boolean): Boolean
    suspend fun getAlreadySignUp(): Boolean
}