package com.sungil.network

import android.app.Activity

interface FirebaseRepo {
    suspend fun requestSMS(phoneNumber : String , activity : Activity) : Boolean
    suspend fun verifyCode(code : String)
}