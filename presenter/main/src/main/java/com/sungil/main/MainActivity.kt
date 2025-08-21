package com.sungil.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.sungil.domain.model.Router
import com.sungil.main.ui.MainScreenView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var router: Router
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            MainScreenView(
                navController = navController,
                viewModel = viewModel,
                profileButtonClick = { router.navigation(NAV_EDIT_PROFILE) },
                reportClick = { router.navigation(NAV_REPORT) },
                lowClick = { router.navigation(NAV_LOW) },
                alarmClick = { router.navigation(NAV_ALARM) },
                oneThingClick = { router.navigation(NAV_ONE_THING) },
                firstMatchClick = { destination ->
                    val bundle = Bundle().apply {
                        putString(BuildConfig.KEY_MATCH, destination)
                    }
                    router.navigation(NAV_FIRST_MATCH, bundle)
                },
                randomMatchClick = { router.navigation(NAV_RANDOM_MATCH) },
                login = { router.navigation(NAV_LOGIN) },
                guide = { router.navigation(NAV_GUIDE) }
            )
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.homeBanner()
        viewModel.requestUserInfo()
        viewModel.oneThingNotify()
        viewModel.serviceNotify()
        viewModel.requestMatch()
        viewModel.getLatestMatch()
    }
}
