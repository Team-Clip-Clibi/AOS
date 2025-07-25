package com.example.data.repositoryImpl

import com.sungil.database.MeetingManger.MeetingManger
import com.sungil.database.SharedPreference
import com.sungil.database.room.model.Meeting
import com.sungil.database.room.model.UserInfo
import com.sungil.database.token.TokenManager
import com.sungil.database.user.UserData
import com.sungil.domain.model.SaveMatch
import com.sungil.domain.repository.DatabaseRepository
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(
    private val database: SharedPreference,
    private val userInfo: UserData,
    private val tokenManger: TokenManager,
    private val meetingManger: MeetingManger,
) : DatabaseRepository {

    override suspend fun saveKaKaoId(kakaoId: String): Boolean {
        return database.saveKaKaoId(
            kakaoId
        )
    }

    override suspend fun getKaKaoId(): String {
        return database.getKaKaoId()
    }

    override suspend fun deleteKaKaoId(): Boolean {
        return database.deleteKAKAOId()
    }

    override suspend fun saveSingUpKey(data: Boolean): Boolean {
        return database.saveSignUp(data)
    }

    override suspend fun getSingUpData(): Boolean {
        return database.getAlreadySignUp()
    }

    override suspend fun saveFcmToken(data: String): Boolean {
        return database.saveFcmToken(data)
    }

    override suspend fun getFcmToken(): String {
        return database.getFcmToken()
    }

    override suspend fun updateFcmToken(data: String): Boolean {
        return database.updateFcmToken(data)
    }

    override suspend fun saveUserInfo(
        name: String,
        nickName: String,
        platform: String,
        phoneNumber: String,
        jobList: String,
        loveState: Pair<String, Boolean>,
        diet: String,
        language: String,
    ): Boolean {
        val result = this.userInfo.updateUserInfo(
            name = name,
            nickName = nickName,
            platform = platform,
            phoneNumber = phoneNumber,
            job = jobList,
            loveState = loveState,
            diet = diet,
            language = language
        )
        return result
    }

    override suspend fun getNotifyState(): Boolean {
        return database.getNotificationState()
    }

    override suspend fun setNotifyState(data: Boolean): Boolean {
        return database.setNotificationState(data)
    }

    override suspend fun setToken(accessToken: String, refreshToken: String): Boolean {
        return tokenManger.updateToken(accessToken, refreshToken)
    }

    override suspend fun getToken(): Pair<String, String> {
        return tokenManger.getToken()
    }

    override suspend fun getUserInfo(): com.sungil.domain.model.UserData {
        return userInfo.getUserData().toDomain()
    }

    override suspend fun deleteUserIfo(): Boolean {
        return userInfo.deleteUserInfo()
    }

    override suspend fun removeToken(): Boolean {
        return tokenManger.clearToken()
    }

    override suspend fun getSaveMatch(): List<SaveMatch> {
        val saveData = meetingManger.get()
        if (saveData.isEmpty()) {
            return emptyList()
        }
        return saveData.map { it.toDomain() }
    }

    override suspend fun deleteSaveMatch(matchId: Int): Boolean {
        return meetingManger.delete(matchId)
    }

    override suspend fun insetSaveMatch(
        matchId: Int,
        time: String,
        category: String,
        location: String,
    ): Boolean {
        return meetingManger.inset(
            matchId = matchId,
            time = time,
            category = category,
            location = location
        )
    }

    override suspend fun getFirstMatchInput(): Boolean {
        return database.getFirstMatchInput()
    }

    override suspend fun setFirstMatchInput(): Boolean {
        return database.saveFirstMatchInput()
    }

    override suspend fun getUserDataStatus(): Boolean {
        return userInfo.isUserDataIsNull()
    }

    override suspend fun getTokenDataStatus(): Boolean {
        return tokenManger.isTokenNull()
    }

    override suspend fun getPermissionCheck(key: String): Boolean {
        return database.getPermissionShowCheck(key = key)
    }

    override suspend fun setPermissionCheck(key: String, data: Boolean): Boolean {
        return database.setPermissionShowCheck(key = key, data = data)
    }

    private fun UserInfo.toDomain(): com.sungil.domain.model.UserData {
        return com.sungil.domain.model.UserData(
            userName = name,
            nickName = nickName,
            phoneNumber = phoneNumber,
            job = job,
            loveState = Pair(myLoveState, wantLoveState),
            diet = diet,
            language = language
        )
    }

    private fun Meeting.toDomain(): SaveMatch {
        return SaveMatch(
            matchId = matchId,
            category = category,
            time = time,
            location = location
        )
    }
}