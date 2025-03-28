package com.example.data.repositoryImpl

import com.sungil.database.SharedPreference
import com.sungil.database.room.UserInfo
import com.sungil.database.room.UserInfoDao
import com.sungil.domain.repository.DatabaseRepository
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(
    private val database: SharedPreference,
    private val userInfoDao: UserInfoDao,
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
    ): Boolean {
        val userInfo = UserInfo(
            name = name,
            termAllChecked = termAllCheck,
            termServicePermission =  servicePermission,
            privatePermission = privatePermission,
            marketingPermission = marketingPermission,
            nickName = nickName,
            birtYear = birthYear,
            birthMonth = birthMonth,
            birthDay = birthDay,
            city = city,
            area = area,
            gender = gender
        )
        try{
            userInfoDao.insert(userInfo)
            return true
        }catch (e : Exception){
            e.printStackTrace()
            return false
        }
    }

}