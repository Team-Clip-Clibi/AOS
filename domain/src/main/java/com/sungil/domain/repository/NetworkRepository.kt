package com.sungil.domain.repository

import android.app.Activity
import android.icu.text.TimeZoneFormat.ParseOption
import com.sungil.domain.model.Banner
import com.sungil.domain.model.Match
import com.sungil.domain.model.MatchData
import com.sungil.domain.model.Notification
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

    suspend fun requestUpdateLoveState(accessToken: String, love: String, relation: Boolean): Int

    suspend fun requestUpdateLanguage(accessToken: String, language: String): Int

    suspend fun requestSignOut(refreshToken: String): Int

    suspend fun requestUpdateDiet(accessToken: String, diet: String): Int

    suspend fun requestDiet(accessToken: String): String

    suspend fun requestJob(accessToken: String): List<String>?

    suspend fun requestLove(accessToken: String): Pair<String?, Boolean?>

    suspend fun requestLanguage(accessToken: String): String?

    suspend fun requestReport(accessToken: String, content: String, reportCategory: String): Int

    suspend fun requestNotification(accessToken: String): Notification

    suspend fun requestBanner(
        accessToken: String,
        bannerType: String,
    ): Banner

    suspend fun requestMatchingData(
        accessToken: String
    ) : Match

}