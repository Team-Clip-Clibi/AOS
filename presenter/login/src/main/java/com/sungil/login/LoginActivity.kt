package com.sungil.login

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.sungil.domain.model.Router
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import com.sungil.domain.model.DebugProvider

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    private val viewModel: LoginViewModel by viewModels()

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var debugProvider: DebugProvider

    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupNotificationPermission()
        setContent {
            LoginNav(
                viewModel = viewModel,
                notification = {
                    requestNotification()
                },
                home = {
                    router.navigation(NAV_MAIN)
                },
                isDebug = debugProvider.provide(),
                activity = this@LoginActivity,
                signUp = {
                    router.navigation(NAV_SIGNUP)
                }
            )
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