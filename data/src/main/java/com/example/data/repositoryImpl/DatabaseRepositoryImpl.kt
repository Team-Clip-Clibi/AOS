package com.example.data.repositoryImpl

import com.sungil.database.SharedPreference
import com.sungil.database.room.dao.TokenDao
import com.sungil.database.room.dao.UserInfoDao
import com.sungil.database.room.model.Token
import com.sungil.database.room.model.UserInfo
import com.sungil.domain.repository.DatabaseRepository
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(
    private val database: SharedPreference,
    private val userInfoDao: UserInfoDao,
    private val tokenDao: TokenDao,
) :
    DatabaseRepository {

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
        jobList: Pair<String, String>,
        loveState: Pair<String, Boolean>,
        diet: String,
        language: String,
    ): Boolean {
        val userInfo = UserInfo(
            name = name,
            nickName = nickName,
            platform = platform,
            phoneNumber = phoneNumber,
            firstJob = jobList.first,
            secondJob = jobList.second,
            myLoveState = loveState.first,
            wantLoveState = loveState.second,
            diet = diet,
            language = language
        )
        try {
            userInfoDao.insert(userInfo)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    override suspend fun getNotifyState(): Boolean {
        return database.getNotificationState()
    }

    override suspend fun setNotifyState(data: Boolean): Boolean {
        return database.setNotificationState(data)
    }

    override suspend fun setToken(accessToken: String, refreshToken: String): Boolean {
        return try {
            tokenDao.insertToken(
                Token(
                    accessToken = accessToken,
                    refreshToken = refreshToken
                )
            )
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun getToken(): Pair<String?, String?> {
        val token = tokenDao.getToken()
        return Pair(token?.accessToken, token?.refreshToken)
    }

    override suspend fun getUserInfo(): com.sungil.domain.model.UserInfo? {
        return userInfoDao.getUserInfo()?.toDomain()
    }

    override suspend fun deleteUserIfo(): Boolean {
        return try {
            userInfoDao.deleteAll()
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun removeToken(): Boolean {
        return try {
            tokenDao.deleteAll()
            true
        } catch (e: Exception) {
            false
        }
    }

    private fun UserInfo.toDomain(): com.sungil.domain.model.UserInfo {
        return com.sungil.domain.model.UserInfo(
            userName = name,
            nickName = nickName,
            phoneNumber = phoneNumber,
            job = Pair(firstJob, secondJob),
            loveState = Pair(myLoveState, wantLoveState),
            diet = diet,
            language = language
        )
    }

}