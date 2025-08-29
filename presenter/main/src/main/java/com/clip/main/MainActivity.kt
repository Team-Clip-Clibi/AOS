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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.compose.rememberNavController
import com.clip.alarm.AlarmViewModel
import com.clip.alarm.ui.AlarmView
import com.clip.domain.model.Router
import com.clip.editprofile.ProfileEditViewModel
import com.clip.editprofile.nav.ProfileEditNav
import com.clip.low.LowView
import com.clip.main.ui.MainScreenView
import com.clip.onethingmatch.NAV_PAY
import com.clip.onethingmatch.OneThingViewModel
import com.clip.onethingmatch.nav.OneThingNav
import com.clip.report.ReportViewModel
import com.clip.report.nav.ReportNav
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import com.oneThing.first_matrch.FirstMatchViewModel
import com.oneThing.first_matrch.nav.FirstMatchNav
import com.oneThing.guide.GuideView
import com.oneThing.random.RandomMatchViewModel
import com.oneThing.random.nav.RandomMatchNav

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val homeViewModel: MainViewModel by viewModels()
    private val oneThingViewModel: OneThingViewModel by viewModels()
    private val firstMatchViewModel: FirstMatchViewModel by viewModels()
    private val randomMatchViewModel: RandomMatchViewModel by viewModels()
    private val profileEditViewModel: ProfileEditViewModel by viewModels()
    private val reportViewModel: ReportViewModel by viewModels()
    private val alarmViewModel: AlarmViewModel by viewModels()

    @Inject
    lateinit var router: Router
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val homeNavController = rememberNavController()
            var root by rememberSaveable { mutableStateOf(ROOT.HOME) }
            BackHandler {
                when (root) {
                    ROOT.HOME -> {
                        finish()
                    }

                    else -> {
                        root = ROOT.HOME
                    }
                }
            }
            AnimatedContent(
                targetState = root,
                transitionSpec = {
                    if (targetState == ROOT.HOME) {
                        slideInHorizontally { -it } + fadeIn() togetherWith
                                slideOutHorizontally { it } + fadeOut()
                    } else {
                        slideInHorizontally { it } + fadeIn() togetherWith
                                slideOutHorizontally { -it } + fadeOut()
                    }
                },
                label = "rootSwitch"
            ) { current ->
                when (current) {
                    ROOT.HOME -> {
                        LaunchedEffect(current) {
                            homeViewModel.requestUserInfo()
                        }
                        MainScreenView(
                            navController = homeNavController,
                            viewModel = homeViewModel,
                            profileButtonClick = {
                                root = ROOT.EDIT_PROFILE
                            },
                            reportClick = {
                                root = ROOT.REPORT
                            },
                            lowClick = {
                                root = ROOT.LOW
                            },
                            alarmClick = {
                                root = ROOT.ALARM
                            },
                            oneThingClick = {
                                root = ROOT.ONE_THING
                            },
                            firstMatchClick = { destination ->
                                firstMatchViewModel.destination(destination)
                                root = ROOT.FIRST_MATCH
                                homeViewModel.initFirstMatch()
                            },
                            randomMatchClick = {
                                root = ROOT.RANDOM_MATCH
                            },
                            login = {
                                router.navigation(NAV_LOGIN)
                            },
                            guide = {
                                root = ROOT.GUIDE
                            }
                        )
                    }

                    ROOT.EDIT_PROFILE -> {
                        ProfileEditNav(
                            viewModel = profileEditViewModel,
                            goToLoginPage = { router.navigation(NAV_LOGIN) },
                            goToMainPage = { root = ROOT.HOME }
                        )
                    }

                    ROOT.REPORT -> {
                        ReportNav(
                            viewModel = reportViewModel,
                            onProfilePage = {
                                root = ROOT.EDIT_PROFILE
                            }
                        )
                    }

                    ROOT.ONE_THING -> {
                        OneThingNav(
                            viewModel = oneThingViewModel,
                            home = { root = ROOT.HOME },
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

                    ROOT.FIRST_MATCH -> {
                        FirstMatchNav(
                            viewModel = firstMatchViewModel,
                            goMatchPage = {
                                when (firstMatchViewModel.getDestination()) {
                                    BuildConfig.ONE_THING -> {
                                        root = ROOT.ONE_THING
                                    }

                                    BuildConfig.RANDOM -> {
                                        root = ROOT.RANDOM_MATCH
                                    }
                                }
                            },
                            home = { root = ROOT.HOME }
                        )
                    }

                    ROOT.RANDOM_MATCH -> {
                        RandomMatchNav(
                            onBack = {
                                root = ROOT.HOME
                            },
                            loginPage = {
                                router.navigation(NAV_LOGIN)
                            },
                            viewModel = randomMatchViewModel,
                            goMeeting = {
                                /**
                                 * TODO 기능 미구현
                                 */
                            },
                            pay = { orderId, userId, amount, matchInfo ->
                                val bundle = Bundle().apply {
                                    putString(com.oneThing.random.BuildConfig.KEY_USER, userId)
                                    putString(com.oneThing.random.BuildConfig.KEY_ORDER, orderId)
                                    putInt(com.oneThing.random.BuildConfig.KEY_AMOUNT, amount)
                                    putString(com.oneThing.random.BuildConfig.KEY_MATCH, matchInfo)
                                }
                                router.navigation(com.oneThing.random.component.NAV_PAY, bundle)
                            }
                        )
                    }

                    ROOT.GUIDE -> {
                        GuideView(onClose = { root = ROOT.HOME })
                    }

                    ROOT.LOW -> {
                        LowView(
                            onBackClick = {
                                root = ROOT.HOME
                            }
                        )
                    }

                    ROOT.ALARM -> {
                        AlarmView(
                            viewModel = alarmViewModel,
                            backClick = {
                                root = ROOT.HOME
                            },
                            reLogin = {
                                router.navigation(NAV_LOGIN)
                            }
                        )
                    }
                }
            }
        }
    }
}
