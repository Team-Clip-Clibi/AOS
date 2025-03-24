package com.sungil.network.http

import com.sungil.network.BuildConfig
import com.sungil.network.model.PhoneNumberRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PATCH

interface HttpApi {

    @PATCH(BuildConfig.PHONE_NUMBER_CHECK)
    suspend fun checkAlreadySignUpNumber(
        @Body phoneNumber: PhoneNumberRequest,
    ): Response<Unit>
}