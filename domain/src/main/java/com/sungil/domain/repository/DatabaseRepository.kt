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
    suspend fun saveUserInfo(
        termAllCheck: Boolean,
        servicePermission: Boolean,
        privatePermission: Boolean,
        marketingPermission: Boolean,
        name: String,
        nickName: String,
        birthYear: String,
        birthMonth: String,
        birthDay: String,
        city: String,
        area: String,
        gender: String,
    ): Boolean

    suspend fun getNotifyState(): Boolean
    suspend fun setNotifyState(data: Boolean): Boolean
}