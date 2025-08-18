package com.sungil.login

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.sungil.domain.model.AppVersionProvider
import com.sungil.domain.model.Router
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import com.sungil.domain.model.DebugProvider
import androidx.core.net.toUri

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    private val viewModel: LoginViewModel by viewModels()

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var debugProvider: DebugProvider

    @Inject
    lateinit var appVersion: AppVersionProvider

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
                },
                appVersion = appVersion.provideAppVersion(),
                playStore = {
                    playStore()
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

    private fun playStore() {
        val packageName = this.packageName
        val marketUri = "market://details?id=$packageName".toUri()
        val marketIntent = Intent(Intent.ACTION_VIEW, marketUri).apply {
            setPackage("com.android.vending")
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        try {
            this.startActivity(marketIntent)
        } catch (e: ActivityNotFoundException) {
            val webUri = "https://play.google.com/store/apps/details?id=$packageName".toUri()
            val webIntent = Intent(Intent.ACTION_VIEW, webUri).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            this.startActivity(webIntent)
        }
    }
}