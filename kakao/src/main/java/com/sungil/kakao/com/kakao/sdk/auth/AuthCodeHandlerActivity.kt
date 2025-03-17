package com.sungil.kakao.com.kakao.sdk.auth

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import com.sungil.domain.model.Router
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

//import androidx.activity.viewModels

@AndroidEntryPoint
class AuthCodeHandlerActivity : AppCompatActivity() {
    private val viewModel: SMSViewModel by viewModels()

    @Inject
    lateinit var router: Router
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startKakaoLogin()
        addListener()
    }

    private fun startKakaoLogin() {
        UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
            if (error != null || token == null) {
                return@loginWithKakaoTalk
            }
            Log.i(javaClass.name.toString(), "로그인 성공 ${token.accessToken}")
            viewModel.saveToken("testData")
        }
    }

    private fun addListener() {
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.actionFlow.collect {
                when (it) {
                    is SMSViewModel.Action.SaveSuccess -> {
                        router.navigationToSMS("SignUp")
                    }

                    else -> {
                        Log.e(javaClass.name.toString(), "Error")
                    }
                }
            }
        }
    }
}