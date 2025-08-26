package com.clip.database

interface SharedPreference {

    suspend fun saveKaKaoId(data: String): Boolean
    suspend fun getKaKaoId(): String
    suspend fun deleteKAKAOId(): Boolean
    suspend fun saveSignUp(data: Boolean): Boolean
    suspend fun getAlreadySignUp(): Boolean
    suspend fun saveFcmToken(data: String): Boolean
    suspend fun getFcmToken(): String
    suspend fun updateFcmToken(data: String): Boolean
    suspend fun setNotificationState(data: Boolean): Boolean
    suspend fun getNotificationState(): Boolean
    suspend fun saveFirstMatchInput(): Boolean
    suspend fun getFirstMatchInput(): Boolean
    suspend fun getPermissionShowCheck(key: String): Boolean
    suspend fun setPermissionShowCheck(key :String , data :Boolean): Boolean
}