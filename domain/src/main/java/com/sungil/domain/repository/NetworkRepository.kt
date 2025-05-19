package com.sungil.domain.repository

import android.app.Activity
import androidx.paging.PagingData
import com.sungil.domain.model.BannerResponse
import com.sungil.domain.model.DietResponse
import com.sungil.domain.model.JobList
import com.sungil.domain.model.LoveResponse
import com.sungil.domain.model.Match
import com.sungil.domain.model.Notification
import com.sungil.domain.model.NotificationResponse
import com.sungil.domain.model.OneThineNotification
import com.sungil.domain.model.PhoneNumberCheckResult
import com.sungil.domain.model.UserInfo
import com.sungil.domain.model.WeekData
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

    suspend fun requestDiet(accessToken: String): DietResponse

    suspend fun requestJob(accessToken: String): JobList

    suspend fun requestLove(accessToken: String): LoveResponse

    suspend fun requestLanguage(accessToken: String): String?

    suspend fun requestReport(accessToken: String, content: String, reportCategory: String): Int

    suspend fun requestNotification(accessToken: String): NotificationResponse

    suspend fun requestBanner(
        accessToken: String,
        bannerType: String,
    ): BannerResponse

    suspend fun requestMatchingData(
        accessToken: String,
    ): Match

    suspend fun requestUpdateToken(
        refreshToken: String,
    ): Triple<Int, String?, String?>

    suspend fun requestOneThineNotification(
        accessToken: String,
    ): OneThineNotification

    fun requestNotificationPaging(): Flow<PagingData<Notification>>

    fun requestReadNotificationPaging(): Flow<PagingData<Notification>>

    suspend fun requestOneThingOrder(
        token: String,
        topic: String,
        districts: List<String>,
        date: List<WeekData>,
        tmiContent: String,
        oneThingBudgetRange: String,
    ): Triple<Int, String?, Int?>

    suspend fun requestPayConfirm(
        token : String,
        paymentKey: String,
        orderId: String,
        orderType: String,
    ): Int
}