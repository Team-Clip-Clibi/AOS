package com.sungil.domain.repository

import android.app.Activity
import com.sungil.domain.model.PhoneNumberCheckResult
import com.sungil.domain.model.UserInfo
import kotlinx.coroutines.flow.Flow

interface NetworkRepository {
    suspend fun requestSMS(phoneNumber: String, activity: Activity): Boolean
    suspend fun verifySMS(code: String)
    suspend fun collectFirebaseResult(): Flow<String>
    suspend fun collectTimer(): Flow<Int>
    suspend fun checkAlreadySignUpNumber(number: String, token: String): PhoneNumberCheckResult
    suspend fun getFCMToken(): String
    suspend fun requestSignUp(
        servicePermission: Boolean,
        privatePermission: Boolean,
        marketingPermission: Boolean,
        socialId: String,
        osVersion: String,
        firebaseToken: String,
        isAllowNotify: Boolean,
    ): Pair<String?, String?>

    suspend fun checkNickName(data: String, token: String): Int
    suspend fun inputPhoneNumber(data: String, token: String): Int
    suspend fun inputName(data: String, token: String): Int
    suspend fun inputNickName(data: String, token: String): Int
    suspend fun inputUserDetail(
        token: String,
        gender: String,
        birth: String,
        city: String,
        county: String,
    ): Int

    suspend fun login(
        socialId: String,
        osVersion: String,
        firebaseToken: String,
        isAllowNotify: Boolean,
    ): Pair<String?, String?>

    suspend fun requestUserData(accessToken: String): UserInfo?

    suspend fun requestUpdateFcmToken(accessToken: String): Int

    suspend fun requestUpdateJob(accessToken: String, data: List<String>): Int
}