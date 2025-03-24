package com.sungil.domain.repository

import android.app.Activity
import kotlinx.coroutines.flow.Flow

interface NetworkRepository {
    suspend fun requestSMS(phoneNumber: String, activity: Activity): Boolean
    suspend fun verifySMS(code: String)
    suspend fun collectFirebaseResult(): Flow<String>
    suspend fun collectTimer() : Flow<Int>
    suspend fun checkAlreadySignUpNumber(number : String) : String
}