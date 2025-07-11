package com.sungil.onethingmatch.nav

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.core.ButtonXXLPurple400
import com.example.core.ColorStyle
import com.example.core.CustomSnackBar
import com.sungil.onethingmatch.Budget
import com.sungil.onethingmatch.BuildConfig
import com.sungil.onethingmatch.CATEGORY
import com.sungil.onethingmatch.ERROR_NETWORK_ERROR
import com.sungil.onethingmatch.ERROR_RE_LOGIN
import com.sungil.onethingmatch.ERROR_USER_ID_NULL
import com.sungil.onethingmatch.Location
import com.sungil.onethingmatch.NAV_BEFORE_PAY
import com.sungil.onethingmatch.NAV_BUDGET
import com.sungil.onethingmatch.NAV_CATEGORY
import com.sungil.onethingmatch.NAV_DATE
import com.sungil.onethingmatch.NAV_INTRO
import com.sungil.onethingmatch.NAV_LOCATION
import com.sungil.onethingmatch.NAV_SUBJECT
import com.sungil.onethingmatch.NAV_TMI
import com.sungil.onethingmatch.OneThingViewModel
import com.sungil.onethingmatch.R
import com.sungil.onethingmatch.UiError
import com.sungil.onethingmatch.component.NotifyMeeting
import com.sungil.onethingmatch.component.TopAppBarWithProgress
import com.sungil.onethingmatch.ui.budget.BudgetView
import com.sungil.onethingmatch.ui.categort.CategoryView
import com.sungil.onethingmatch.ui.day.OneThingDayView
import com.sungil.onethingmatch.ui.intro.IntroView
import com.sungil.onethingmatch.ui.location.LocationView
import com.sungil.onethingmatch.ui.pay.BeforePayView
import com.sungil.onethingmatch.ui.subject.InputSubjectView
import com.sungil.onethingmatch.ui.tmi.TmiView

@Composable
fun OneThingNav(
    viewModel: OneThingViewModel,
    home: () -> Unit,
    pay: (String, String, Int, String) -> Unit,
    startDestination: String = NAV_INTRO,
    goLoginPage: () -> Unit,
) {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    val snackbarHostState = remember { SnackbarHostState() }
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val pageInfo = when (currentRoute) {
        NAV_CATEGORY -> 1
        NAV_SUBJECT -> 2
        NAV_LOCATION -> 3
        NAV_BUDGET -> 4
        NAV_TMI -> 5
        NAV_DATE -> 6
        else -> -1
    }
    LaunchedEffect(uiState) {
        when (val error = uiState.error) {
            is UiError.FailOrder -> {
                when (error.message) {
                    ERROR_NETWORK_ERROR -> {
                        snackbarHostState.showSnackbar(
                            message = context.getString(R.string.msg_network_error),
                            duration = SnackbarDuration.Short
                        )
                        viewModel.initError()
                    }

                    ERROR_RE_LOGIN -> {
                        snackbarHostState.showSnackbar(
                            message = context.getString(R.string.msg_re_login),
                            duration = SnackbarDuration.Short
                        )
                        goLoginPage()
                    }

                    ERROR_USER_ID_NULL -> {
                        snackbarHostState.showSnackbar(
                            message = context.getString(R.string.msg_save_error),
                            duration = SnackbarDuration.Short
                        )
                        goLoginPage()
                    }
                }
            }
            else -> Unit
        }

        if (uiState.tosInstall.isNotEmpty()) {
            viewModel.initInstallResult()
            viewModel.order()
        }

        if (uiState.orderNumber.isNotEmpty()) {
            pay(
                uiState.orderNumber,
                uiState.userId,
                uiState.amount,
                BuildConfig.MATCH_INFO
            )
            viewModel.initOrderNumber()
        }
    }
    Scaffold(
        topBar = {
            TopAppBarWithProgress(
                title = stringResource(R.string.top_app_bar),
                currentPage = pageInfo,
                totalPage = 6,
                onBackClick = {
                    if (!navController.popBackStack()) home()
                }
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(bottom = 8.dp)
            ) {
                if (currentRoute == NAV_DATE) {
                    NotifyMeeting()
                    Spacer(modifier = Modifier.height(12.dp))
                }
                HorizontalDivider(
                    thickness = 1.dp,
                    color = ColorStyle.GRAY_200
                )
                Spacer(modifier = Modifier.height(8.dp))
                ButtonXXLPurple400(
                    onClick = {
                        when (currentRoute) {
                            NAV_INTRO -> {
                                navController.navigate(NAV_CATEGORY) {
                                    popUpTo(NAV_CATEGORY) {
                                        inclusive = true
                                    }
                                }
                            }

                            NAV_CATEGORY -> {
                                navController.navigate(NAV_SUBJECT) {
                                    popUpTo(NAV_SUBJECT) {
                                        inclusive = true
                                    }
                                }
                            }

                            NAV_SUBJECT -> {
                                navController.navigate(NAV_LOCATION) {
                                    popUpTo(NAV_LOCATION) {
                                        inclusive = true
                                    }
                                }
                            }

                            NAV_LOCATION -> {
                                navController.navigate(NAV_BUDGET) {
                                    popUpTo(NAV_BUDGET) {
                                        inclusive = true
                                    }
                                }
                            }

                            NAV_BUDGET -> {
                                navController.navigate(NAV_TMI) {
                                    popUpTo(NAV_TMI) {
                                        inclusive = true
                                    }
                                }
                            }

                            NAV_TMI -> {
                                navController.navigate(NAV_DATE) {
                                    popUpTo(NAV_DATE) {
                                        inclusive = true
                                    }
                                }
                            }

                            NAV_DATE -> {
                                navController.navigate(NAV_BEFORE_PAY) {
                                    popUpTo(NAV_BEFORE_PAY) {
                                        inclusive = true
                                    }
                                }
                            }
                            NAV_BEFORE_PAY -> {
                                viewModel.checkInstall()
                            }
                        }
                    },
                    buttonText = when (currentRoute) {
                        NAV_DATE -> stringResource(R.string.btn_finish)
                        else -> stringResource(R.string.btn_next)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 17.dp),
                    isEnable = when (currentRoute) {
                        NAV_INTRO -> true
                        NAV_CATEGORY -> uiState.selectedCategories != CATEGORY.NONE
                        NAV_SUBJECT -> uiState.topic.trim().isNotEmpty() &&
                                uiState.topic.length <= 50

                        NAV_LOCATION -> uiState.location != Location.NONE
                        NAV_BUDGET -> uiState.budget != Budget.RANGE_NONE
                        NAV_TMI -> uiState.tmi.trim()
                            .isNotEmpty() && uiState.tmi.trim().length <= 50

                        NAV_DATE -> uiState.selectDate.isNotEmpty()
                        NAV_BEFORE_PAY -> true
                        else -> false
                    }
                )
            }
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                snackbar = { data ->
                    CustomSnackBar(data)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 17.dp,
                        end = 16.dp,
                        bottom = WindowInsets.navigationBars
                            .asPaddingValues()
                            .calculateBottomPadding() + 32.dp
                    )
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding()
                )
        ) {
            composable(NAV_INTRO,
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(700)
                    )
                },
                popEnterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(700)
                    )
                }) {
                IntroView()
            }

            composable(NAV_CATEGORY,
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(700)
                    )
                },
                popEnterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(700)
                    )
                }) {
                CategoryView(
                    viewModel = viewModel
                )
            }

            composable(NAV_SUBJECT,
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(700)
                    )
                },
                popEnterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(700)
                    )
                }) {
                InputSubjectView(
                    viewModel = viewModel
                )
            }

            composable(NAV_LOCATION,
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(700)
                    )
                },
                popEnterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(700)
                    )
                }) {
                LocationView(
                    viewModel = viewModel
                )
            }

            composable(NAV_BUDGET,
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(700)
                    )
                },
                popEnterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(700)
                    )
                }) {
                BudgetView(
                    viewModel = viewModel
                )
            }

            composable(NAV_TMI,
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(700)
                    )
                },
                popEnterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(700)
                    )
                }) {
                TmiView(
                    viewModel = viewModel
                )
            }

            composable(NAV_DATE,
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(700)
                    )
                },
                popEnterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(700)
                    )
                }) {
                OneThingDayView(
                    viewModel = viewModel
                )
            }

            composable(NAV_BEFORE_PAY,
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(700)
                    )
                },
                popEnterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(700)
                    )
                }) {
                BeforePayView(
                    viewModel = viewModel,
                    goDayPage = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}