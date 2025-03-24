package com.sungil.network

import android.app.Activity
import kotlinx.coroutines.flow.Flow

interface FirebaseRepo {
    suspend fun requestSMS(phoneNumber: String, activity: Activity): Boolean
    suspend fun verifyCode(code: String)
    suspend fun smsFlow(): Flow<String>
    suspend fun timeFlow(): Flow<Int>
}