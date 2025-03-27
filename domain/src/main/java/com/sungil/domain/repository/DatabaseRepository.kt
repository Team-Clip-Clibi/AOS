package com.sungil.domain.repository

interface DatabaseRepository {
    suspend fun saveKaKaoId(kakaoId: String): Boolean
    suspend fun getKaKaoId(): String
    suspend fun deleteKaKaoId(): Boolean
    suspend fun saveSingUpKey(data: Boolean): Boolean
    suspend fun getSingUpData(): Boolean
    suspend fun saveFcmToken(data: String): Boolean
    suspend fun getFcmToken(): String
    suspend fun updateFcmToken(data: String): Boolean
}