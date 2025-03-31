package com.sungil.network.http

import com.sungil.network.BuildConfig
import com.sungil.network.model.AuthToken
import com.sungil.network.model.NickNameCheckRequest
import com.sungil.network.model.TermData
import com.sungil.network.model.UserInfoResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface HttpApi {

    /**
     * 이미 가입된 번호인지 확인하는 URL
     */
    @GET(BuildConfig.PHONE_NUMBER_CHECK_FIRST + "{phoneNumber}" + BuildConfig.PHONE_NUMBER_CHECK_LAST)
    suspend fun checkAlreadySignUpNumber(
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
        @Body request : NickNameCheckRequest
    ) : Response<Unit>

}