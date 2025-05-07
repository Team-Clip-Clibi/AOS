package com.sungil.login

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.material3.SnackbarHostState
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

    private val snackbarHostState = SnackbarHostState()
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginScreen(
                kakaoLogin = {
                    viewModel.getKAKAOId()
                },
                viewModel = viewModel
            )
        }
        setupNotificationPermission()
        requestNotification()
        addListener()
    }

    private fun addListener() {
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.actionFlow.collect {
                when (it) {
                    is LoginViewModel.Action.GetSuccess -> {
                        viewModel.checkSignUp()
                    }

                    is LoginViewModel.Action.NotSignUp -> {
                        router.navigation(SIGNUP_VIEW)
                    }

                    is LoginViewModel.Action.SignUp -> {
                        viewModel.requestLogin()
                    }

                    is LoginViewModel.Action.FCMToken -> {
                        Log.d(javaClass.name.toString(), it.message)
                    }

                    is LoginViewModel.Action.Login -> {
                        router.navigation(MAIN_VIEW)
                    }

                    is LoginViewModel.Action.Error -> {
                        when (it.errorMessage) {
                            ERROR_KAKAO_ID_NULL -> {
                                router.navigation(KAKAO_VIEW)
                            }

                            ERROR_NOTIFICATION -> {
                                finish()
                            }

                            ERROR_FCM_TOKEN -> {
                                finish()
                            }

                            ERROR_NOTIFY_NOT_SAVE ->{
                                finish()
                            }

                            else -> {
                                Toast.makeText(this@LoginActivity , it.errorMessage , Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setupNotificationPermission() {
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            viewModel.setNotification(isGranted)
        }
    }

    private fun requestNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        } else {
            viewModel.setNotification(true)
        }
    }
}