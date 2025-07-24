package com.oneThing.onething

import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import com.oneThing.fcm.FirebaseIntentProvider
import com.oneThing.fcm.FirebaseMessage
import com.google.firebase.Firebase
import com.google.firebase.initialize
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_KEY)
        Firebase.initialize(this)
        val keyHash = Utility.getKeyHash(this)
        Log.i("kjwTest", "keyHash: $keyHash")
        FirebaseMessage.intentProvider = object : FirebaseIntentProvider {
            override fun getFCMIntent(context: Context): Intent {
                return Intent(context, MainActivity::class.java)
            }
        }
    }
}