package com.oneThing.random.nav

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
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
import com.example.core.CustomSnackBar
import com.example.core.TopAppBarWithCloseButton
import com.oneThing.random.R
import com.oneThing.random.RandomMatchViewModel
import com.oneThing.random.UiError
import com.oneThing.random.UiSuccess
import com.oneThing.random.component.BottomBar
import com.oneThing.random.component.DuplicateBottomBar
import com.oneThing.random.component.ERROR_NETWORK_ERROR
import com.oneThing.random.component.ERROR_RE_LOGIN
import com.oneThing.random.component.Location
import com.oneThing.random.component.NAV_RANDOM_LOCATION
import com.oneThing.random.component.NAV_RANDOM_MATCH_DUPLICATE
import com.oneThing.random.component.NAV_RANDOM_MATCH_INTRO
import com.oneThing.random.component.NAV_RANDOM_TMI
import com.oneThing.random.component.NEXT_DATE_EMPTY
import com.oneThing.random.component.TopAppBarWithProgress
import com.oneThing.random.ui.DuplicateMatch
import com.oneThing.random.ui.RandomLocation
import com.oneThing.random.ui.RandomMatchIntro
import com.oneThing.random.ui.RandomTmi

@Composable
internal fun RandomMatchNav(
    onBack: () -> Unit,
    loginPage: () -> Unit,
    viewModel: RandomMatchViewModel,
    goMeeting: () -> Unit,
) {
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(uiState.error) {
        when (val error = uiState.error) {
            is UiError.Error -> {
                when (error.message) {
                    ERROR_NETWORK_ERROR -> {
                        snackbarHostState.showSnackbar(
                            message = context.getString(R.string.msg_network_error),
                            duration = SnackbarDuration.Short
                        )
                    }

                    ERROR_RE_LOGIN -> {
                        loginPage()
                    }

                    else -> {
                        throw IllegalArgumentException("Unhandled error message: ${error.message}")
                    }
                }
            }

            else -> Unit
        }
    }
    LaunchedEffect(uiState.success) {
        when (val success = uiState.success) {
            is UiSuccess.DuplicateSuccess -> {
                when (success.nextDate) {
                    NEXT_DATE_EMPTY -> {
                        navController.navigate(NAV_RANDOM_LOCATION) {
                            popUpTo(NAV_RANDOM_LOCATION) { inclusive = true }
                        }
                    }

                    else -> {
                        navController.navigate(NAV_RANDOM_MATCH_DUPLICATE) {
                            popUpTo(NAV_RANDOM_MATCH_DUPLICATE) { inclusive = true }
                        }
                    }
                }
            }

            else -> Unit
        }
    }
    val pageInfo = when (currentRoute) {
        NAV_RANDOM_MATCH_INTRO -> -1
        NAV_RANDOM_MATCH_DUPLICATE -> -1
        NAV_RANDOM_LOCATION -> 1
        else -> -1
    }
    Scaffold(
        topBar = {
            when (currentRoute) {
                NAV_RANDOM_MATCH_DUPLICATE -> {
                    TopAppBarWithCloseButton(
                        title = stringResource(R.string.top_app_bar_random_match),
                        onBackClick = {
                            if (!navController.popBackStack()) onBack()
                        },
                        isNavigationShow = false,
                        isActionShow = true
                    )
                }

                else -> {
                    TopAppBarWithProgress(
                        title = stringResource(R.string.top_app_bar_random_match),
                        currentPage = pageInfo,
                        totalPage = 4,
                        onBackClick = {
                            if (!navController.popBackStack()) onBack()
                        }
                    )
                }
            }
        },
        bottomBar = {
            when (currentRoute) {
                NAV_RANDOM_MATCH_DUPLICATE -> {
                    DuplicateBottomBar(
                        goMeeting = goMeeting,
                        goHome = onBack
                    )
                }

                else -> {
                    BottomBar(
                        isEnable = when (currentRoute) {
                            NAV_RANDOM_MATCH_INTRO -> true
                            NAV_RANDOM_LOCATION -> uiState.location != Location.NONE
                            NAV_RANDOM_TMI -> uiState.tmi.trim().isNotEmpty()
                            else -> false
                        },
                        buttonText = when (currentRoute) {
                            NAV_RANDOM_MATCH_INTRO -> stringResource(R.string.random_btn_next)
                            else -> stringResource(R.string.random_btn_next)
                        },
                        onClick = {
                            when (currentRoute) {
                                NAV_RANDOM_MATCH_INTRO -> {
                                    viewModel.duplicateCheck()
                                }
                                NAV_RANDOM_LOCATION ->{
                                    navController.navigate(NAV_RANDOM_TMI) {
                                        popUpTo(NAV_RANDOM_TMI) { inclusive = true }
                                    }
                                }
                            }
                        }
                    )
                }
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
        },
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = NAV_RANDOM_MATCH_INTRO,
            modifier = Modifier.padding(
                top = paddingValues.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding()
            )
        ) {
            composable(NAV_RANDOM_MATCH_INTRO,
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
                RandomMatchIntro()
            }

            composable(
                NAV_RANDOM_MATCH_DUPLICATE,
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
                DuplicateMatch(
                    viewModel = viewModel
                )
            }

            composable(
                NAV_RANDOM_LOCATION,
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
                RandomLocation(
                    viewModel = viewModel,
                )
            }

            composable(
                NAV_RANDOM_TMI,
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
                RandomTmi(
                    viewModel = viewModel
                )
            }
        }
    }
}
