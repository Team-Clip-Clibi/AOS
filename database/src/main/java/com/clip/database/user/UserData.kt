package com.clip.database.user

import com.clip.database.room.model.UserInfo

interface UserData {
    suspend fun getUserData(): UserInfo
    suspend fun updateUserInfo(
        name: String,
        nickName: String,
        platform: String,
        phoneNumber: String,
        job: String,
        loveState: Pair<String, Boolean>,
        diet: String,
        language: String,
    ): Boolean

    suspend fun deleteUserInfo(): Boolean

    suspend fun isUserDataIsNull() : Boolean
}