package com.sungil.network.http

import com.sungil.network.BuildConfig
import com.sungil.network.model.AppVersionDTO
import com.sungil.network.model.AuthToken
import com.sungil.network.model.Banner
import com.sungil.network.model.Diet
import com.sungil.network.model.Job
import com.sungil.network.model.Language
import com.sungil.network.model.LoginRequest
import com.sungil.network.model.MatchOverView
import com.sungil.network.model.Matching
import com.sungil.network.model.MatchingResponse
import com.sungil.network.model.NickNameCheckRequest
import com.sungil.network.model.Notification
import com.sungil.network.model.OneThinNotify
import com.sungil.network.model.OneThingNotificationDTO
import com.sungil.network.model.OneThingOrder
import com.sungil.network.model.OneThingOrderResponse
import com.sungil.network.model.Payment
import com.sungil.network.model.RandomDuplicate
import com.sungil.network.model.RandomOrder
import com.sungil.network.model.RandomOrderResponse
import com.sungil.network.model.RefreshToken
import com.sungil.network.model.RelationShip
import com.sungil.network.model.Report
import com.sungil.network.model.RequestUserInfo
import com.sungil.network.model.TermData
import com.sungil.network.model.UserDetailRequest
import com.sungil.network.model.UserInfoResponse
import com.sungil.network.model.MatchDetailResponse
import com.sungil.network.model.MatchNoticeDto
import com.sungil.network.model.MatchProgressDTO
import com.sungil.network.model.MatchReviewDTO
import com.sungil.network.model.ParticipantsDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface HttpApi {
    /**
     * 이미 가입된 번호인지 확인하는 URL
     */
    @GET(BuildConfig.PHONE_NUMBER_CHECK_FIRST + "{phoneNumber}" + BuildConfig.PHONE_NUMBER_CHECK_LAST)
    suspend fun checkAlreadySignUpNumber(
        @Header("Authorization") bearerToken: String,
        @Path("phoneNumber") userId: String,
    ): Response<UserInfoResponse>

    /**
     * 동의항목 SEND URL
     */
    @POST(BuildConfig.USER_SIGNUP)
    suspend fun requestSendTerm(
        @Body signUpRequest: TermData,
    ): Response<AuthToken>

    /**
     * 닉네임 중복 검사 URL
     */
    @POST(BuildConfig.NICK_NAME_URL)
    suspend fun requestCheckNickName(
        @Header("Authorization") bearerToken: String,
        @Body request: NickNameCheckRequest,
    ): Response<Unit>

    /**
     * 핸드폰 입력 API
     */
    @PATCH(BuildConfig.USER_PHONE_SEND_URL)
    suspend fun requestSendPhoneNumber(
        @Header("Authorization") bearerToken: String,
        @Body body: Map<String, String>,
    ): Response<Unit>

    /**
     * 이름 입력 API
     */
    @PATCH(BuildConfig.NAME_SEND_URL)
    suspend fun requestSendName(
        @Header("Authorization") bearerToken: String,
        @Body body: Map<String, String>,
    ): Response<Unit>

    /**
     * 닉네임 입력 API
     */
    @PATCH(BuildConfig.NICK_NAME_SEND_URL)
    suspend fun requestSendNickName(
        @Header("Authorization") bearerToken: String,
        @Body body: Map<String, String>,
    ): Response<Unit>

    /**
     * 회원정보 상세 입력 API
     */
    @PATCH(BuildConfig.USER_DETAIL_URL)
    suspend fun requestSendDetail(
        @Header("Authorization") bearerToken: String,
        @Body request: UserDetailRequest,
    ): Response<Unit>

    /**
     * 로그인
     */
    @POST(BuildConfig.LOGIN_URL)
    suspend fun requestLogin(
        @Body loginData: LoginRequest,
    ): Response<AuthToken>

    /**
     * 사용자 정보 조회
     */
    @GET(BuildConfig.USERINFO_URL)
    suspend fun requestUserInfo(
        @Header("Authorization") bearerToken: String,
    ): Response<RequestUserInfo>

    /**
     * FCM Token 업데이트
     */
    @PATCH(BuildConfig.UPDATE_FCM_TOKEN)
    suspend fun requestUpdateFcmToken(
        @Header("Authorization") bearerToken: String,
        @Body body: Map<String, String>,
    ): Response<Unit>

    /**
     * 직업 업데이트
     */
    @PATCH(BuildConfig.CHANGE_JOB_URL)
    suspend fun requestChangeJob(
        @Header("Authorization") bearerToken: String,
        @Body job: Job,
    ): Response<Unit>

    /**
     * 직업 GET API
     */
    @GET(BuildConfig.CHANGE_JOB_URL)
    suspend fun requestJob(
        @Header("Authorization") bearerToken: String,
    ): Response<Job>

    /**
     * 연애 상태 변경 API
     */
    @PATCH(BuildConfig.UPDATE_RELEATION_URL)
    suspend fun requestUpdateRelationShip(
        @Header("Authorization") bearerToken: String,
        @Body data: RelationShip,
    ): Response<Unit>

    /**
     * 연애 상태 GET API
     */
    @GET(BuildConfig.UPDATE_RELEATION_URL)
    suspend fun requestRelationShip(
        @Header("Authorization") bearerToken: String,
    ): Response<RelationShip>

    /**
     * 사용 언어 변경 API
     */
    @PATCH(BuildConfig.LANGUAGE_UPDATE_URL)
    suspend fun requestUpdateLanguage(
        @Header("Authorization") bearerToken: String,
        @Body body: Language,
    ): Response<Unit>

    /**
     * 사용 언어 GET API
     */
    @GET(BuildConfig.LANGUAGE_UPDATE_URL)
    suspend fun requestGetLanguage(
        @Header("Authorization") bearerToken: String,
    ): Response<Language>

    /**
     * 식단 업데이트 URL
     */
    @PATCH(BuildConfig.DIET_URL)
    suspend fun requestUpdateDiet(
        @Header("Authorization") bearerToken: String,
        @Body diet: Diet,
    ): Response<Unit>

    /**
     * 식닥 GET URL
     */
    @GET(BuildConfig.DIET_URL)
    suspend fun requestDiet(
        @Header("Authorization") bearerToken: String,
    ): Response<Diet>

    /**
     * 회원 털퇴 API
     */
    @DELETE(BuildConfig.SIGNOUT_URL)
    suspend fun requestSignOut(
        @Header("Authorization") bearerToken: String,
    ): Response<Unit>

    /**
     * 신고 API
     */
    @POST(BuildConfig.REPORT_URL)
    suspend fun requestReport(
        @Header("Authorization") bearerToken: String,
        @Body data: Report,
    ): Response<Unit>

    /**
     * 공지사항 API
     */
    @GET(BuildConfig.ANNOUNCEMENT_URL)
    suspend fun requestNotification(
        @Header("Authorization") bearerToken: String,
    ): Response<List<Notification>>

    /**
     * 배너 API
     */
    @GET(BuildConfig.BANNER_URL + "{bannerType}")
    suspend fun requestBanner(
        @Header("Authorization") bearerToken: String,
        @Path("bannerType") bannerType: String,
    ): Response<List<Banner>>


    /**
     * 매칭 조회 API
     */
    @GET(BuildConfig.MATCH_URL)
    suspend fun requestMatchData(
        @Header("Authorization") bearerToken: String,
    ): Response<MatchingResponse>

    /**
     * 원띵 알람 조회 API
     */
    @GET(BuildConfig.ONE_THING_NOTI_URL)
    suspend fun requestNewNotification(
        @Header("Authorization") bearerToken: String,
    ): Response<List<OneThinNotify>>

    /**
     * 토큰 갱신 url
     */
    @POST(BuildConfig.REFRESH_URL)
    suspend fun requestRefreshToken(
        @Body body: Map<String, String>,
    ): Response<RefreshToken>

    /**
     * 새로운 알람 조회 api
     */
    @GET(BuildConfig.UN_READ_NOTIFY_URL)
    suspend fun requestNotify(
        @Header("Authorization") bearerToken: String,
    ): Response<List<OneThingNotificationDTO>>

    /**
     * 새로운 알람 조회 API 다음 페이지
     */
    @GET(BuildConfig.UN_READ_NOTIFY_URL + "/" + "{lastNotificationId}")
    suspend fun requestNewNotify(
        @Header("Authorization") bearerToken: String,
        @Path("lastNotificationId") lastNotificationId: String,
    ): Response<List<OneThingNotificationDTO>>


    /**
     * 이미 읽은 알람 조회
     */
    @GET(BuildConfig.READ_NOTIFY_URL)
    suspend fun requestReadNotify(
        @Header("Authorization") bearerToken: String,
    ): Response<List<OneThingNotificationDTO>>

    /**
     * 이미 읽은 알람 다음 조회
     */
    @GET(BuildConfig.READ_NOTIFY_URL + "/" + "{lastNotificationId}")
    suspend fun requestReadNotifyNext(
        @Header("Authorization") bearerToken: String,
        @Path("lastNotificationId") lastNotificationId: String,
    ): Response<List<OneThingNotificationDTO>>

    /**
     * OneThing 결제서 발행
     */
    @POST(BuildConfig.ONETHING_ORDER_URL)
    suspend fun requestOneThingOrder(
        @Header("Authorization") bearerToken: String,
        @Body order: OneThingOrder,
    ): Response<OneThingOrderResponse>

    /**
     * 결제 승인 API
     */
    @POST(BuildConfig.PAYMENT_URL)
    suspend fun requestPayment(
        @Header("Authorization") bearerToken: String,
        @Body payment: Payment,
    ): Response<Unit>

    /**
     * 랜덤 매칭 신청 중복 검사
     */
    @GET(BuildConfig.RANDOM_DUPLICATE_URL)
    suspend fun requestCheckRandomDuplicate(
        @Header("Authorization") bearerToken: String,
    ): Response<RandomDuplicate>

    /**
     * 랜덤 매칭 신청
     */
    @POST(BuildConfig.RANDOM_ORDER_URL)
    suspend fun requestRandomOrder(
        @Header("Authorization") bearerToken: String,
        @Body order: RandomOrder,
    ): Response<RandomOrderResponse>

    /**
     * 모임 현황 조회
     */
    @GET(BuildConfig.MATCH_OVERVIEW_URL)
    suspend fun requestMatchOverView(
        @Header("Authorization") bearerToken: String,
    ): Response<MatchOverView>

    /**
     * 알람 상태 업데이트 API
     */
    @PATCH(BuildConfig.NOTIFY_UPDATE_URL)
    suspend fun requestUpdateNotify(
        @Header("Authorization") bearerToken: String,
        @Body body: Map<String, Boolean>,
    ): Response<Unit>

    /**
     * 내모임 매칭 정보 조회
     */
    @GET(BuildConfig.MATCH_ING_URL)
    suspend fun requestMatchingData(
        @Header("Authorization") bearerToken: String,
        @Query("matchingStatus") matchingStatus: String,
        @Query("lastMeetingTime") lastMeetingTime: String,
    ): Response<List<Matching>>

    @GET(BuildConfig.MATCH_ING_URL + "/" + "{matchingType}" + "/" + "{id}")
    suspend fun requestMatchDetail(
        @Header("Authorization") bearerToken: String,
        @Path("matchingType") matchingType: String,
        @Path("id") id: Int,
    ): Response<MatchDetailResponse>

    /**
     * 매치 안내문 조회 API
     */
    @GET(BuildConfig.MATCH_NOTICE_URL)
    suspend fun requestMatchNotice(
        @Header("Authorization") bearerToken: String,
        @Query("lastMeetingTime") lastMeetingTime: String,
    ): Response<List<MatchNoticeDto>>

    /**
     * 지각 URL
     */
    @POST(BuildConfig.MATCH_ING_URL + "/" + "{matchingType}" + "/" + "{id}")
    suspend fun sendLateMatch(
        @Header("Authorization") bearerToken: String,
        @Path("matchingType") matchingType: String,
        @Path("id") id: Int,
        @Body body: Map<String, Int>,
    ): Response<Unit>

    /**
     * 매치 리뷰 api
     */
    @POST(BuildConfig.MATCH_REVIEW_URL + "/" + "{matchingId}" + "/" + "{matchingType}")
    suspend fun sendReview(
        @Header("Authorization") bearerToken: String,
        @Path("matchingId") id: Int,
        @Path("matchingType") matchingType: String,
        @Body review: MatchReviewDTO,
    ): Response<Unit>

    /**
     * 참여자 조회 API
     */
    @GET(BuildConfig.MATCH_REVIEW_URL + "/" + "{matchingId}" + "/" + "{matchingType}" + "/participants")
    suspend fun requestParticipants(
        @Header("Authorization") bearerToken: String,
        @Path("matchingId") id: Int,
        @Path("matchingType") matchingType: String,
    ): Response<List<ParticipantsDTO>>

    /**
     * 진행중인 모임 조회
     */
    @GET(BuildConfig.MATCH_PROGRESS_MATCH_URL + "/" + "{matchingType}" + "/" + "{id}" + "/" + BuildConfig.MATCH_PROGRESS_PROGRESS_URL)
    suspend fun requestProgressMatchInfo(
        @Header("Authorization") bearerToken: String,
        @Path("matchingType") type: String,
        @Path("id") id: Int,
    ): Response<MatchProgressDTO>
    /**
     * APP Version check
     */
    @GET(BuildConfig.VERSION_CHECK_URL)
    suspend fun requestAppVersion() : Response<AppVersionDTO>
}