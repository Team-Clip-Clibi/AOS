package com.sungil.domain.repository

import com.sungil.domain.model.UserInfo

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
        marketingPermission: Boolean,
        name: String,
        nickName: String,
        birthYear: String,
        birthMonth: String,
        birthDay: String,
        city: String,
        area: String,
        gender: String,
        platform: String,
        phoneNumber: String,
        jobList: Pair<String , String>,
        loveState: Pair<String, String>,
        diet: String,
        language: String,
    ): Boolean

    suspend fun getNotifyState(): Boolean
    suspend fun setNotifyState(data: Boolean): Boolean
    suspend fun setToken(accessToken: String, refreshToken: String): Boolean
    suspend fun getToken(): Pair<String?, String?>
    suspend fun getUserInfo(): UserInfo?
    suspend fun deleteUserIfo() : Boolean
    suspend fun removeToken(): Boolean
}