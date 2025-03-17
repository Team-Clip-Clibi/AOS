package com.sungil.login

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.sungil.domain.model.Router
import com.sungil.login.ui.LoginScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    private val viewModel: LoginViewModel by viewModels()

    @Inject
    lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginScreen(
                kakaoLogin = {
//                    viewModel.getToken()
                    router.navigationToSMS("SignUp")
                }
            )
        }

        addListener()
    }

    private fun addListener() {
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.actionFlow.collect {
                when (it) {
                    is LoginViewModel.Action.GetSuccess -> {
//                        router.navigationToSMS("SignUp") 메인으로 가는 루프문
                    }

                    is LoginViewModel.Action.Error -> {
                        when (it.errorMessage) {
                            ERROR_KAKAO_ID_NULL -> {
                                router.navigationToSMS("SignUp")
                            }

                            else -> throw IllegalArgumentException("UNKNOW ERROR")
                        }
                    }
                }
            }
        }
    }

}