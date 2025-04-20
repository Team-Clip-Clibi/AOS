package com.sungil.domain.repository

import com.sungil.domain.model.SaveMatch
import com.sungil.domain.model.UserData

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
        name: String,
        nickName: String,
        platform: String,
        phoneNumber: String,
        jobList: Pair<String, String>,
        loveState: Pair<String, Boolean>,
        diet: String,
        language: String,
    ): Boolean

    suspend fun getNotifyState(): Boolean
    suspend fun setNotifyState(data: Boolean): Boolean
    suspend fun setToken(accessToken: String, refreshToken: String): Boolean
    suspend fun getToken(): Pair<String, String>
    suspend fun getUserInfo(): UserData
    suspend fun deleteUserIfo() : Boolean
    suspend fun removeToken(): Boolean
    suspend fun getSaveMatch() : List<SaveMatch>
    suspend fun deleteSaveMatch(matchId : Int) : Boolean
    suspend fun insetSaveMatch(matchId : Int , time : String , category : String , location : String) : Boolean
}