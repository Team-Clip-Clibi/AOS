package com.example.data.repositoryImpl

import android.app.Activity
import com.example.fcm.FirebaseFCM
import com.sungil.domain.model.PhoneNumberCheckResult
import com.sungil.domain.model.UserInfo
import com.sungil.domain.repository.NetworkRepository
import com.sungil.network.FirebaseSMSRepo
import com.sungil.network.http.HttpApi
import com.sungil.network.model.Diet
import com.sungil.network.model.Job
import com.sungil.network.model.Language
import com.sungil.network.model.LoginRequest
import com.sungil.network.model.NickNameCheckRequest
import com.sungil.network.model.RelationShip
import com.sungil.network.model.Report
import com.sungil.network.model.RequestUserInfo
import com.sungil.network.model.SLANG
import com.sungil.network.model.TermData
import com.sungil.network.model.UserDetailRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(
    private val firebase: FirebaseSMSRepo,
    private val api: HttpApi,
    private val fcmRepo: FirebaseFCM,
) :
    NetworkRepository {
    override suspend fun requestSMS(phoneNumber: String, activity: Activity): Boolean {
        return firebase.requestSMS(phoneNumber, activity)
    }

    override suspend fun verifySMS(code: String) {
        firebase.verifyCode(code)
    }

    override suspend fun collectFirebaseResult(): Flow<String> = firebase.smsFlow()

    override suspend fun collectTimer(): Flow<Int> = firebase.timeFlow()

    override suspend fun checkAlreadySignUpNumber(
        number: String,
        token: String,
    ): PhoneNumberCheckResult {
        val response = api.checkAlreadySignUpNumber(token, number)
        val userName = response.body()?.userName
        val platform = response.body()?.platform
        val createdAt = response.body()?.createdAt
        return PhoneNumberCheckResult(
            code = response.code(),
            userName = userName,
            platform = platform,
            createdAt = createdAt
        )
    }

    override suspend fun getFCMToken(): String {
        return fcmRepo.getFirebaseToken()
    }

    override suspend fun requestSignUp(
        servicePermission: Boolean,
        privatePermission: Boolean,
        marketingPermission: Boolean,
        socialId: String,
        osVersion: String,
        firebaseToken: String,
        isAllowNotify: Boolean,
    ): Pair<String?, String?> {
        val response = api.requestSendTerm(
            TermData(
                servicePermission = servicePermission,
                privatePermission = privatePermission,
                marketingPermission = marketingPermission,
                socialId = socialId,
                platform = "KAKAO",
                deviceType = "ANDROID",
                osVersion = osVersion,
                firebaseToken = firebaseToken,
                isAllowNotify = isAllowNotify
            )
        )
        val accessToken = response.body()?.accessToken
        val refreshToken = response.body()?.refreshToken
        return accessToken to refreshToken
    }

    override suspend fun checkNickName(data: String, token: String): Int {
        val response = api.requestCheckNickName(token, NickNameCheckRequest(data))
        return response.code()
    }

    override suspend fun inputPhoneNumber(data: String, token: String): Int {
        val response = api.requestSendPhoneNumber(token, mapOf("phoneNumber" to data))
        return response.code()
    }

    override suspend fun inputName(data: String, token: String): Int {
        val response = api.requestSendName(token, mapOf("userName" to data))

        return response.code()
    }

    override suspend fun inputNickName(data: String, token: String): Int {
        val response = api.requestSendNickName(token, mapOf("nickname" to data))
        return response.code()
    }

    override suspend fun inputUserDetail(
        token: String,
        gender: String,
        birth: String,
        city: String,
        county: String,
    ): Int {
        val response = api.requestSendDetail(
            token, UserDetailRequest(
                gender = gender,
                birth = birth,
                city = city,
                county = county
            )
        )
        return response.code()
    }

    override suspend fun login(
        socialId: String,
        osVersion: String,
        firebaseToken: String,
        isAllowNotify: Boolean,
    ): Pair<String?, String?> {
        val tokenData = api.requestLogin(
            LoginRequest(
                socialId = socialId,
                platform = "KAKAO",
                deviceType = "ANDROID",
                osVersion = osVersion,
                firebaseToken = firebaseToken,
                isAllowNotify = isAllowNotify
            )
        )
        val accessToken = tokenData.body()?.accessToken
        val refreshToken = tokenData.body()?.refreshToken
        return accessToken to refreshToken
    }

    override suspend fun requestUserData(accessToken: String): UserInfo? {
        val result = api.requestUserInfo(accessToken)
        return result.body()?.toDomain()
    }

    override suspend fun requestUpdateFcmToken(accessToken: String): Int {
        val result = api.requestUpdateFcmToken(accessToken)
        return result.code()
    }

    override suspend fun requestUpdateJob(accessToken: String, data: List<String>): Int {
        val result = api.requestChangeJob(accessToken, Job(data))
        return result.code()
    }

    override suspend fun requestUpdateLoveState(
        accessToken: String,
        love: String,
        relation: Boolean,
    ): Int {
        val result = api.requestUpdateRelationShip(
            accessToken,
            RelationShip(
                love,
                relation
            )
        )
        return result.code()
    }

    override suspend fun requestUpdateLanguage(accessToken: String, language: String): Int {
        val result = api.requestUpdateLanguage(
            accessToken,
            Language(language)
        )
        return result.code()
    }

    override suspend fun requestSignOut(refreshToken: String): Int {
        val result = api.requestSignOut(refreshToken)
        return result.code()
    }

    override suspend fun requestUpdateDiet(accessToken: String, diet: String): Int {
        val result = api.requestUpdateDiet(accessToken, Diet(diet))
        return result.code()
    }

    override suspend fun requestDiet(accessToken: String): String {
        TODO("Not yet implemented")
    }

    override suspend fun requestJob(accessToken: String): List<String> {
        TODO("Not yet implemented")
    }

    override suspend fun requestLove(accessToken: String): Pair<String, Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun requestLanguage(accessToken: String): String {
        TODO("Not yet implemented")
    }

    override suspend fun requestReport(
        accessToken: String,
        content: String,
        reportCategory: String,
    ): Int {
        val category = SLANG.entries.find { it.name == reportCategory }
            ?: throw IllegalArgumentException("Invalid report category: $reportCategory")
        val result = api.requestReport(
            accessToken, Report(
                content = content,
                reportCategory = category
            )
        )
        return result.code()
    }

    private fun RequestUserInfo.toDomain(): UserInfo {
        return UserInfo(
            userName = username,
            nickName = nickname,
            phoneNumber = phoneNumber,
            job = Pair("NONE", "NONE"),
            loveState = Pair("NONE", "NONE"),
            diet = "NONE",
            language = "KOREAN"
        )
    }
}