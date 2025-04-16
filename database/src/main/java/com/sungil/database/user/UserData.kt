package com.sungil.database.user

import com.sungil.database.room.model.UserInfo

interface UserData {
    suspend fun getUserData(): UserInfo
    suspend fun updateUserInfo(
        name: String,
        nickName: String,
        platform: String,
        phoneNumber: String,
        jobList: Pair<String, String>,
        loveState: Pair<String, Boolean>,
        diet: String,
        language: String,
    ): Boolean

    suspend fun deleteUserInfo(): Boolean
}