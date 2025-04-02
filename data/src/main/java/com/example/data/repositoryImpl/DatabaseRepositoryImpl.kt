package com.example.data.repositoryImpl

import com.sungil.database.SharedPreference
import com.sungil.database.room.dao.TokenDao
import com.sungil.database.room.model.UserInfo
import com.sungil.database.room.dao.UserInfoDao
import com.sungil.database.room.model.Token
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
        platform: String,
        phoneNumber: String,
    ): Boolean {
        val userInfo = UserInfo(
            name = name,
            termServicePermission = servicePermission,
            privatePermission = privatePermission,
            marketingPermission = marketingPermission,
            nickName = nickName,
            birtYear = birthYear,
            birthMonth = birthMonth,
            birthDay = birthDay,
            city = city,
            area = area,
            gender = gender,
            platform = platform,
            phoneNumber = phoneNumber
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

}