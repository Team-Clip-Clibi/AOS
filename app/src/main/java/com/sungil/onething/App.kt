package com.sungil.onething

import android.app.Application
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.initialize
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this , BuildConfig.KAKAO_NATIVE_KEY)
        Firebase.initialize(this)
        var keyHash = Utility.getKeyHash(this)
        Log.i("kjwTest", "keyHash: $keyHash")
    }
}