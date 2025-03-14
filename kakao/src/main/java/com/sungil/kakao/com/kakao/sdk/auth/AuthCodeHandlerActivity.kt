package com.sungil.kakao.com.kakao.sdk.auth

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient

class AuthCodeHandlerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startKakaoLogin()
    }

    private fun startKakaoLogin() {
        UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
            if (error != null || token == null) {
                return@loginWithKakaoTalk
            }
            Log.i(javaClass.name.toString(), "로그인 성공 ${token.accessToken}")
        }
    }

}