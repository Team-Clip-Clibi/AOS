package com.example.data.repositoryImpl

import android.app.Activity
import com.sungil.domain.repository.NetworkRepository
import com.sungil.network.FirebaseRepo
import com.sungil.network.http.HttpApi
import com.sungil.network.model.PhoneNumberRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(
    private val firebase: FirebaseRepo,
    private val api: HttpApi,
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

    override suspend fun checkAlreadySignUpNumber(number: String): String {
        val response = api.checkAlreadySignUpNumber(PhoneNumberRequest(number))
        return response.code().toString()
    }
}