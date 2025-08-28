package com.clip.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.compose.rememberNavController
import com.clip.domain.model.Router
import com.clip.main.ui.MainScreenView
import com.clip.onethingmatch.NAV_PAY
import com.clip.onethingmatch.OneThingViewModel
import com.clip.onethingmatch.nav.OneThingNav
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val homeViewModel: MainViewModel by viewModels()
    private val oneThingViewModel: OneThingViewModel by viewModels()

    @Inject
    lateinit var router: Router
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val homeNavController = rememberNavController()
            val oneThingController = rememberNavController()
            var root by rememberSaveable { mutableStateOf(NAV_ROOT.HOME) }
            BackHandler {
                when (root) {
                    NAV_ROOT.HOME -> {
                        finish()
                    }

                    NAV_ROOT.ONE_THING -> {
                        root = NAV_ROOT.HOME
                    }
                }
            }
            AnimatedContent(
                targetState = root,
                transitionSpec = {
                    if (targetState == NAV_ROOT.ONE_THING) {
                        // HOME -> ONE_THING : 오른쪽 -> 왼쪽
                        slideInHorizontally { it } + fadeIn() togetherWith
                                slideOutHorizontally { -it } + fadeOut()
                    } else {
                        // ONE_THING -> HOME : 왼쪽 -> 오른쪽
                        slideInHorizontally { -it } + fadeIn() togetherWith
                                slideOutHorizontally { it } + fadeOut()
                    }
                },
                label = "rootSwitch"
            ) { current ->
                when (current) {
                    NAV_ROOT.HOME -> {
                        MainScreenView(
                            navController = homeNavController,
                            viewModel = homeViewModel,
                            profileButtonClick = { router.navigation(NAV_EDIT_PROFILE) },
                            reportClick = { router.navigation(NAV_REPORT) },
                            lowClick = { router.navigation(NAV_LOW) },
                            alarmClick = { router.navigation(NAV_ALARM) },
                            oneThingClick = { root = NAV_ROOT.ONE_THING },
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

                    NAV_ROOT.ONE_THING -> {
                        OneThingNav(
                            viewModel = oneThingViewModel,
                            home = { root = NAV_ROOT.HOME },
                            pay = { orderId, userId, amount, matchInfo ->
                                val bundle = Bundle().apply {
                                    putString(BuildConfig.KEY_USER, userId)
                                    putString(BuildConfig.KEY_ORDER, orderId)
                                    putInt(BuildConfig.KEY_AMOUNT, amount)
                                    putString(
                                        BuildConfig.KEY_MATCH,
                                        matchInfo
                                    )
                                }
                                router.navigation(NAV_PAY, bundle)
                            },
                            goLoginPage = { router.navigation(NAV_LOGIN) }
                        )
                    }
                }
            }
        }
    }

}
