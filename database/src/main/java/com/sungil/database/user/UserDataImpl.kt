package com.sungil.database.user

import com.sungil.database.room.dao.UserInfoDao
import com.sungil.database.room.model.UserInfo
import javax.inject.Inject

class UserDataImpl @Inject constructor(private val userDao: UserInfoDao) : UserData {

    private var cachedUserInfo: UserInfo? = null

    override suspend fun getUserData(): UserInfo {
        if (cachedUserInfo == null) {
            cachedUserInfo =
                userDao.getUserInfo() ?: throw IllegalStateException("User data is missing.")
        }
        return cachedUserInfo!!
    }

    override suspend fun updateUserInfo(
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

        return try {
            userDao.insert(userInfo)
            cachedUserInfo = userInfo
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun deleteUserInfo(): Boolean {
        return try {
            userDao.deleteAll()
            cachedUserInfo = null
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}