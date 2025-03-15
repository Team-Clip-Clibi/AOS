package com.sungil.database

import com.sungil.database.model.TokenData

interface SharedPreference {

    suspend fun saveToken(tokeData: TokenData): Boolean
    suspend fun getToken(): TokenData
    suspend fun deleteToken(key: String): Boolean
}