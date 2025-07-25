package com.example.data.repositoryImpl

import android.app.Activity
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.data.mapper.toDomain
import com.example.data.mapper.toMatchData
import com.example.data.mapper.toMatchProgress
import com.example.data.paging.MatchNoticePagingSource
import com.example.data.paging.MatchPagingSource
import com.example.data.paging.NotificationPagingSource
import com.example.data.paging.NotificationReadPagingSource
import com.example.fcm.FirebaseToken
import com.sungil.database.token.TokenManager
import com.sungil.domain.model.BannerData
import com.sungil.domain.model.DietData
import com.sungil.domain.model.DietResponse
import com.sungil.domain.model.JobList
import com.sungil.domain.model.Love
import com.sungil.domain.model.LoveResponse
import com.sungil.domain.model.MatchData
import com.sungil.domain.model.MatchDate
import com.sungil.domain.model.MatchDetail
import com.sungil.domain.model.MatchNotice
import com.sungil.domain.model.MatchOverView
import com.sungil.domain.model.MatchProgress
import com.sungil.domain.model.MatchingData
import com.sungil.domain.model.NetworkResult
import com.sungil.domain.model.NotificationData
import com.sungil.domain.model.NotificationResponse
import com.sungil.domain.model.OneThineNotification
import com.sungil.domain.model.Participants
import com.sungil.domain.model.PhoneNumberCheckResult
import com.sungil.domain.model.RandomInfo
import com.sungil.domain.model.UserInfo
import com.sungil.domain.model.WeekData
import com.sungil.domain.repository.NetworkRepository
import com.sungil.network.FirebaseSMSRepo
import com.sungil.network.http.HttpApi
import com.sungil.network.model.Diet
import com.sungil.network.model.Job
import com.sungil.network.model.Language
import com.sungil.network.model.LoginRequest
import com.sungil.network.model.MatchReviewDTO
import com.sungil.network.model.NickNameCheckRequest
import com.sungil.network.model.OneThingOrder
import com.sungil.network.model.Payment
import com.sungil.network.model.PreferredDate
import com.sungil.network.model.RandomOrder
import com.sungil.network.model.RelationShip
import com.sungil.network.model.Report
import com.sungil.network.model.SLANG
import com.sungil.network.model.TermData
import com.sungil.network.model.UserDetailRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(
    private val firebase: FirebaseSMSRepo,
    private val api: HttpApi,
    private val fcmRepo: FirebaseToken,
    private val tokenManger: TokenManager,
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
    ): Triple<Int, String?, String?> {
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
        return Triple(tokenData.code(), accessToken, refreshToken)
    }

    override suspend fun requestUserData(accessToken: String): UserInfo? {
        val result = api.requestUserInfo(accessToken)
        return result.body()?.toDomain(result.code())
    }

    override suspend fun requestUpdateFcmToken(accessToken: String, fcmToken: String): Int {
        return api.requestUpdateFcmToken(
            bearerToken = accessToken,
            body = mapOf("fcmToken" to fcmToken)
        )
            .code()
    }

    override suspend fun requestUpdateJob(accessToken: String, data: String): Int {
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

    override suspend fun requestDiet(accessToken: String): DietResponse {
        val result = api.requestDiet(accessToken)
        return DietResponse(
            result.code(),
            DietData(
                result.body().toString()
            )
        )
    }

    override suspend fun requestJob(accessToken: String): JobList {
        val result = api.requestJob(accessToken)
        return JobList(
            result.code(),
            result.body()?.job ?: ""
        )
    }

    override suspend fun requestLove(accessToken: String): LoveResponse {
        val result = api.requestRelationShip(accessToken)
        if (result.code() != 200) {
            return LoveResponse(
                responseCode = result.code(), Love(
                    relationShip = "", isSameRelationShip = false
                )
            )
        }
        return LoveResponse(
            responseCode = result.code(), Love(
                relationShip = result.body()?.relationshipStatus ?: "SINGLE",
                isSameRelationShip = result.body()?.isSameRelationshipConsidered ?: false
            )
        )
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

    override suspend fun requestNotification(accessToken: String): NetworkResult<NotificationResponse> {
        return try {
            val response = api.requestNotification(accessToken)
            if (!response.isSuccessful) return NetworkResult.Error(code = response.code())
            val body = response.body() ?: return NetworkResult.Error(code = response.code())
            return NetworkResult.Success(
                NotificationResponse(
                    responseCode = response.code(),
                    notificationDataList = body.map { data ->
                        NotificationData(
                            noticeType = data.noticeType,
                            content = data.content,
                            link = data.link ?: ""
                        )
                    }
                )
            )
        } catch (e: Exception) {
            NetworkResult.Error(code = 500, message = e.localizedMessage, throwable = e)
        }
    }

    override suspend fun requestBanner(
        accessToken: String,
        bannerType: String,
    ): Pair<Int, List<BannerData>> {
        val result = api.requestBanner(accessToken, bannerType)
        val bannerList = mutableListOf<BannerData>()
        result.body()?.let { bannerItems ->
            for (banner in bannerItems) {
                bannerList.add(
                    BannerData(
                        image = banner.imagePresignedUrl ?: "",
                        headText = banner.text ?: "",
                    )
                )
            }
        }
        return Pair(result.code(), bannerList)
    }


    override suspend fun requestMatchingData(accessToken: String): NetworkResult<MatchData> {
        try {
            val response = api.requestMatchData(accessToken)
            if (!response.isSuccessful) {
                return NetworkResult.Error(code = response.code(), message = response.message())
            }
            val body = response.body() ?: return NetworkResult.Error(
                code = response.code(),
                message = response.message()
            )
            return NetworkResult.Success(body.toMatchData())
        } catch (e: Exception) {
            return NetworkResult.Error(code = 500, message = e.localizedMessage, throwable = e)
        }
    }

    override fun requestMatchingData(
        matchStatus: String,
        lastTime: String,
    ): Flow<PagingData<MatchingData>> {
        return Pager(
            config = PagingConfig(pageSize = 50),
            pagingSourceFactory = {
                MatchPagingSource(
                    api = api,
                    token = tokenManger,
                    matchingStatus = matchStatus,
                    lastMeetingTime = lastTime
                )
            }
        ).flow
    }

    override suspend fun requestUpdateToken(refreshToken: String): Triple<Int, String?, String?> {
        val result = api.requestRefreshToken(mapOf("refreshToken" to refreshToken))
        return Triple(result.code() , result.body()?.accessToken ?: "", result.body()?.refreshToken)
    }

    override suspend fun requestOneThineNotification(accessToken: String): OneThineNotification {
        val result = api.requestNewNotification(accessToken)
        if (result.code() != 200) {
            return OneThineNotification(
                result.code(),
                emptyList()
            )
        }
        return OneThineNotification(
            result.code(),
            result.body()!!.map { it.toDomain() }
        )
    }

    override fun requestNotificationPaging(): Flow<PagingData<com.sungil.domain.model.Notification>> {
        return Pager(
            config = PagingConfig(pageSize = 50),
            pagingSourceFactory = {
                NotificationPagingSource(
                    api = api,
                    tokenManager = tokenManger
                )
            }
        ).flow
    }

    override fun requestReadNotificationPaging(): Flow<PagingData<com.sungil.domain.model.Notification>> {
        return Pager(
            config = PagingConfig(pageSize = 50),
            pagingSourceFactory = {
                NotificationReadPagingSource(
                    api = api,
                    tokenManager = tokenManger
                )
            }
        ).flow
    }

    override suspend fun requestOneThingOrder(
        token: String,
        topic: String,
        districts: String,
        date: List<WeekData>,
        tmiContent: String,
        oneThingBudgetRange: String,
        oneThingCategory: String,
    ): Triple<Int, String?, Int?> {
        val result = api.requestOneThingOrder(
            bearerToken = token,
            order = OneThingOrder(
                topic = topic,
                district = districts,
                preferredDates = date.flatMap { week ->
                    week.timeSlots.map { timeSlot ->
                        PreferredDate(date = week.date, timeSlot = timeSlot)
                    }
                },
                tmiContent = tmiContent,
                oneThingBudgetRange = oneThingBudgetRange,
                oneThingCategory = oneThingCategory
            )
        )
        return Triple(result.code(), result.body()?.orderId, result.body()?.amount)
    }

    override suspend fun requestPayConfirm(
        token: String,
        paymentKey: String,
        orderId: String,
        orderType: String,
    ): Int {
        return api.requestPayment(
            token,
            Payment(
                paymentKey = paymentKey,
                orderId = orderId,
                orderType = orderType
            )
        ).code()
    }

    override suspend fun requestRandomMatchDuplicate(token: String): Triple<Int, String?, Boolean?> {
        return api.requestCheckRandomDuplicate(
            token
        ).let { response ->
            Triple(
                response.code(),
                response.body()?.meetingTime,
                response.body()?.isDuplicated
            )
        }
    }

    override suspend fun requestRandomMatch(
        token: String,
        topic: String,
        tmiContent: String,
        district: String,
    ): RandomInfo {
        return api.requestRandomOrder(
            token, RandomOrder(
                topic = topic,
                tmiContent = tmiContent,
                district = district
            )
        ).let { response ->
            RandomInfo(
                responseCode = response.code(),
                orderId = response.body()?.orderId ?: "",
                amount = response.body()?.amount ?: 0,
                meetingPlace = response.body()?.meetingPlace ?: "",
                meetingTime = response.body()?.meetingTime ?: "",
                meetingLocation = response.body()?.meetingLocation ?: ""
            )
        }
    }

    override suspend fun requestMatchOverView(token: String): NetworkResult<MatchOverView> {
        return try {
            val response = api.requestMatchOverView(token)
            if (!response.isSuccessful) {
                return NetworkResult.Error(code = response.code())
            }
            val body = response.body() ?: return NetworkResult.Error(code = response.code())
            return NetworkResult.Success(
                MatchOverView(
                    responseCode = response.code(),
                    date = body.nextMatchingDate,
                    applyMatch = body.appliedMatchingCount,
                    confirmMatch = body.confirmedMatchingCount,
                    isAllNoticeRead = body.isAllNoticeRead
                )
            )
        } catch (e: Exception) {
            NetworkResult.Error(code = 500, message = e.localizedMessage, throwable = e)
        }
    }

    override suspend fun requestUpdateNotify(token: String, isAllowNotify: Boolean): Int {
        return try {
            api.requestUpdateNotify(
                bearerToken = token,
                body = mapOf("isAllowNotify" to isAllowNotify)
            ).code()
        } catch (e: Exception) {
            500
        }
    }

    override fun requestMatchNotice(lastTime: String): Flow<PagingData<MatchNotice>> {
       return Pager(
           config = PagingConfig(pageSize = 50),
           pagingSourceFactory = {
               MatchNoticePagingSource(
                   api = api,
                   token = tokenManger,
                   lastMeetingTime = lastTime
               )
           }
       ).flow
    }

    override suspend fun requestMatchDetail(
        token: String,
        matchingId: Int,
        matchType: String,
    ): NetworkResult<MatchDetail> {
        return try {
            val response = api.requestMatchDetail(
                bearerToken = token,
                id = matchingId,
                matchingType = matchType
            )
            if (!response.isSuccessful) {
                return NetworkResult.Error(code = response.code(), message = response.message())
            }
            val body = response.body() ?: return NetworkResult.Error(code = response.code())
            NetworkResult.Success(
                MatchDetail(
                    time = body.meetingTime,
                    matchStatus = body.matchingStatus.matchingStatusName,
                    matchType = body.matchingType,
                    matchTime = body.applicationInfo.preferredDates.map { responseData ->
                        MatchDate(
                            date = responseData.date,
                            time = responseData.timeSlot
                        )
                    },
                    matchCategory = body.applicationInfo.oneThingCategory,
                    matchBudget = body.applicationInfo.oneThingBudgetRange,
                    matchContent = body.myOneThingContent,
                    matchPrice = body.paymentInfo.matchingPrice,
                    paymentPrice = body.paymentInfo.paymentPrice,
                    requestTime = body.paymentInfo.requestedAt,
                    approveTime = body.paymentInfo.approvedAt,
                    district = body.applicationInfo.district,
                    job = body.myMatchingInfo.job,
                    loveState = body.myMatchingInfo.relationshipStatus,
                    diet = body.myMatchingInfo.dietaryOption,
                    language = body.myMatchingInfo.language
                )
            )
        } catch (e: Exception) {
            return NetworkResult.Error(code = 500, message = e.localizedMessage, throwable = e)
        }
    }

    override suspend fun sendLateMatch(
        token: String,
        matchId: Int,
        matchType: String,
        lateTime : Int
    ): NetworkResult<Int> {
        try {
            val sendLateMatch = api.sendLateMatch(
                bearerToken = token,
                matchingType = matchType,
                id = matchId,
                body = mapOf("lateMinutes" to lateTime)
            )
            if (!sendLateMatch.isSuccessful) {
                return NetworkResult.Error(code = sendLateMatch.code())
            }
            return NetworkResult.Success(sendLateMatch.code())
        } catch (e: Exception) {
            return NetworkResult.Error(code = 500, message = e.localizedMessage, throwable = e)
        }
    }

    override suspend fun sendReviewData(
        token: String,
        mood: String,
        positivePoints: String,
        negativePoints: String,
        reviewContent: String,
        noShowMembers: String,
        allAttend: Boolean,
        matchId: Int,
        matchType: String,
    ): NetworkResult<Int> {
        try {
            val body = MatchReviewDTO(
                mood = mood,
                negativePoints = negativePoints,
                noShowMembers = noShowMembers,
                positivePoints = positivePoints,
                reviewContent = reviewContent,
                is_member_all_attended = allAttend
            )
            val response = api.sendReview(
                bearerToken = token,
                matchingType = matchType,
                id = matchId,
                review = body
            )
            if (!response.isSuccessful) {
                return NetworkResult.Error(code = response.code(), message = response.message())
            }
            return NetworkResult.Success(response.code())
        } catch (e: Exception) {
            return NetworkResult.Error(code = 500, message = e.localizedMessage, throwable = e)
        }
    }

    override suspend fun requestParticipants(
        token: String,
        matchId: Int,
        matchType: String,
    ): NetworkResult<List<Participants>> {
        try {
            val response = api.requestParticipants(
                bearerToken = token,
                id = matchId,
                matchingType = matchType
            )
            if (!response.isSuccessful || response.body() == null) {
                return NetworkResult.Error(code = response.code(), message = response.message())
            }
            val participants = response.body()!!.map { data ->
                Participants(
                    id = data.id,
                    nickName = data.nickname
                )
            }
            return NetworkResult.Success(
                participants
            )
        } catch (e: Exception) {
            return NetworkResult.Error(code = 500, message = e.localizedMessage, throwable = e)
        }
    }

    override suspend fun requestProgressMatch(
        token: String,
        matchId: Int,
        matchType: String,
    ): NetworkResult<MatchProgress> {
        try {
            val request = api.requestProgressMatchInfo(
                bearerToken = token,
                id = matchId,
                type = matchType
            )
            if (!request.isSuccessful) {
                return NetworkResult.Error(code = request.code(), message = request.message())
            }
            if (request.code() != 200) {
                return NetworkResult.Error(code = request.code(), message = request.message())
            }
            if (request.body() == null) {
                return NetworkResult.Error(code = request.code(), message = request.message())
            }
            return NetworkResult.Success(request.body()!!.toMatchProgress())
        } catch (e: Exception) {
            return NetworkResult.Error(code = 500, message = e.localizedMessage, throwable = e)
        }
    }
}