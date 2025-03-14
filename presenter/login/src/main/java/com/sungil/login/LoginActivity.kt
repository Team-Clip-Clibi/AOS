package com.sungil.login

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.sungil.domain.model.Router
import com.sungil.login.ui.LoginScreen
import dagger.hilt.android.AndroidEntryPoint
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
                    router.navigationToSMS("KAKAO")
                }
            )
        }
    }

    override fun onPause() {
        super.onPause()
        Log.d(javaClass.name.toString() , "The view is Pause")
    }

    override fun onResume() {
        super.onResume()
        Log.d(javaClass.name.toString() , "The view is resume")
    }

}