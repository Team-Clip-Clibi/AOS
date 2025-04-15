package com.example.data.repositoryImpl

import android.app.Activity
import com.example.fcm.FirebaseFCM
import com.sungil.domain.model.Banner
import com.sungil.domain.model.Match
import com.sungil.domain.model.MatchData
import com.sungil.domain.model.Notification
import com.sungil.domain.model.PhoneNumberCheckResult
import com.sungil.domain.model.UserInfo
import com.sungil.domain.repository.NetworkRepository
import com.sungil.network.FirebaseSMSRepo
import com.sungil.network.http.HttpApi
import com.sungil.network.model.Diet
import com.sungil.network.model.Job
import com.sungil.network.model.Language
import com.sungil.network.model.LoginRequest
import com.sungil.network.model.MatchingDto
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
        val result = api.requestDiet(accessToken)
        return result.body().toString()
    }

    override suspend fun requestJob(accessToken: String): List<String>? {
        val result = api.requestJob(accessToken)
        return result.body()?.jobList
    }

    override suspend fun requestLove(accessToken: String): Pair<String?, Boolean?> {
        val result = api.requestRelationShip(accessToken)
        return Pair(result.body()?.relationshipStatus, result.body()?.isSameRelationshipConsidered)
    }

    override suspend fun requestLanguage(accessToken: String): String? {
        val result = api.requestGetLanguage(accessToken)
        return result.body()?.language
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

    override suspend fun requestNotification(accessToken: String): Notification {
        val result = api.requestNotification(accessToken)
        if (result.code() != 204) {
            return Notification(
                responseCode = result.code(),
                noticeType = "",
                content = "",
                link = ""
            )
        }
        return Notification(
            responseCode = result.code(),
            noticeType = result.body()!!.noticeType,
            content = result.body()!!.content,
            link = result.body()!!.link
        )
    }

    override suspend fun requestBanner(
        accessToken: String,
        bannerType: String,
    ): Banner {
        val result = api.requestBanner(accessToken, bannerType)
        if (result.code() != 204) {
            return Banner(result.code(), image = "", headText = "", subText = "")
        }
        return Banner(
            result.code(),
            image = result.body()!!.imagePresignedUrl,
            headText = result.body()!!.headText,
            subText = result.body()!!.subText
        )
    }

    override suspend fun requestMatchingData(accessToken: String): Match {
        val result = api.requestMatchData(accessToken)
        if (result.code() != 200) {
            return Match(
                responseCode = result.code(),
                oneThingMatch = emptyList(),
                randomMatch = emptyList()
            )
        }
        if (result.body() == null) {
            return Match(
                responseCode = -100,
                oneThingMatch = emptyList(),
                randomMatch = emptyList()
            )
        }
        return Match(
            responseCode = result.code(),
            oneThingMatch = result.body()!!.oneThingMatchings.map { it.toDomainMatchData() },
            randomMatch = result.body()!!.randomMatchings.map { it.toDomainMatchData() }
        )
    }

    private fun RequestUserInfo.toDomain(): UserInfo {
        return UserInfo(
            userName = username,
            nickName = nickname,
            phoneNumber = phoneNumber,
            job = Pair("NONE", "NONE"),
            loveState = Pair("NONE", false),
            diet = "NONE",
            language = "KOREAN"
        )
    }

    private fun MatchingDto.toDomainMatchData(): MatchData {
        return MatchData(
            matchingId = matchingId,
            daysUntilMeeting = daysUntilMeeting,
            meetingPlace = meetingPlace
        )
    }
}