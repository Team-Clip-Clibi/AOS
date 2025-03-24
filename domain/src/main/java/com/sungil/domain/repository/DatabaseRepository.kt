package com.sungil.domain.repository

interface DatabaseRepository {
    suspend fun saveKaKaoId(kakaoId: String): Boolean
    suspend fun getKaKaoId(): String
    suspend fun deleteKaKaoId(): Boolean
    suspend fun saveSingUpKey(data : Boolean) : Boolean
    suspend fun getSingUpData() : Boolean
}