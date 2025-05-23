package com.sungil.kakao.com.kakao.sdk.auth

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kakao.sdk.user.UserApiClient
import com.sungil.domain.model.Router
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


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
                Log.e(javaClass.name.toString(), "error $error")
                viewModel.saveKaKaoId("3975324589")
                return@loginWithKakaoTalk
            }
            Log.i(javaClass.name.toString(), "로그인 성공 ${token.accessToken}")
            UserApiClient.instance.me { user, error ->
                if (user == null) {
                    Log.e(javaClass.name.toString(), "the user data is null $error")
                    return@me
                }
                Log.d(javaClass.name.toString(), "userId : ${user.id}")
                viewModel.saveKaKaoId(user.id.toString())
            }
        }
    }

    private fun addListener() {
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.actionFlow.collect { result ->
                when (result) {
                    is SMSViewModel.Action.SaveSuccess -> {
                        viewModel.checkAlreadySignUp(result.kakaoId)
                    }

                    is SMSViewModel.Action.AlreadySignUp -> {
                        router.navigation("Main")
                    }

                    is SMSViewModel.Action.NotSignUp -> {
                        router.navigation("SignUp")
                    }

                    is SMSViewModel.Action.Error -> {
                        Log.e(javaClass.name.toString(), result.errorMessage)
                    }
                }
            }
        }
    }
}