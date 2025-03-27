package com.example.data.repositoryImpl

import android.app.Activity
import com.example.fcm.FirebaseFCM
import com.sungil.domain.repository.NetworkRepository
import com.sungil.network.FirebaseSMSRepo
import com.sungil.network.http.HttpApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(
    private val firebase: FirebaseSMSRepo,
    private val api: HttpApi,
    private val fcmRepo : FirebaseFCM
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
        val response = api.checkAlreadySignUpNumber(number)
        return response.code().toString()
    }

    override suspend fun getFCMToken(): String {
        return fcmRepo.getFirebaseToken()
    }
}