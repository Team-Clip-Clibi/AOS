package com.sungil.database

import com.sungil.database.model.TokenData

interface SharedPreference {

    suspend fun saveKaKaoId(data : String): Boolean
    suspend fun getKaKaoId(): String
    suspend fun deleteToken(key: String): Boolean
}