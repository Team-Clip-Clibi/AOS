package com.sungil.login

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.sungil.domain.model.Router
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import com.kakao.sdk.auth.model.Prompt

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    private val viewModel: LoginViewModel by viewModels()

    @Inject
    lateinit var router: Router

    val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e(javaClass.name.toString(), "카카오계정으로 로그인 실패", error)
        } else if (token != null) {
            Log.i(javaClass.name.toString(), "카카오계정으로 로그인 성공 ${token.accessToken}")
        }
    }
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupNotificationPermission()
        setContent {
            LoginNav(
                viewModel = viewModel,
                kakao = {
//                    router.navigation(NAV_KAKAO)
                    test()
                },
                notification = {
                    requestNotification()
                },
                home = {
                    router.navigation(NAV_MAIN)
                })

        }
    }

    private fun test(){
        UserApiClient.instance.loginWithKakaoAccount(this@LoginActivity, prompts = listOf(Prompt.LOGIN) , callback = callback)
    }

    private fun setupNotificationPermission() {
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            viewModel.setNotification(isGranted)
        }
    }

    private fun requestNotification() {
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> {
                permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }

            else -> {
                viewModel.setNotification(true)
            }
        }
    }
}