package com.sungil.network.http

import android.media.session.MediaSession.Token
import com.sungil.network.BuildConfig
import com.sungil.network.model.AuthToken
import com.sungil.network.model.Banner
import com.sungil.network.model.Diet
import com.sungil.network.model.Job
import com.sungil.network.model.Language
import com.sungil.network.model.LoginRequest
import com.sungil.network.model.MatchingResponse
import com.sungil.network.model.NickNameCheckRequest
import com.sungil.network.model.Notification
import com.sungil.network.model.OneThinNotify
import com.sungil.network.model.OneThingNotificationDTO
import com.sungil.network.model.RelationShip
import com.sungil.network.model.Report
import com.sungil.network.model.RequestUserInfo
import com.sungil.network.model.TermData
import com.sungil.network.model.UserDetailRequest
import com.sungil.network.model.UserInfoResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

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
        bearerToken: String,
    ): Response<AuthToken>

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
    @GET(BuildConfig.UN_READ_NOTIFY_URL + "/"+"{phoneNumber}")
    suspend fun requestNewNotify(
        @Header("Authorization") bearerToken: String,
    ): Response<List<OneThingNotificationDTO>>
}