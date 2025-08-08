package com.oneThing.first_matrch.nav

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
import com.oneThing.first_matrch.DIET
import com.oneThing.first_matrch.DomainError
import com.oneThing.first_matrch.FirstMatchViewModel
import com.oneThing.first_matrch.JOB
import com.oneThing.first_matrch.NAV_DIET
import com.oneThing.first_matrch.NAV_INTRO
import com.oneThing.first_matrch.NAV_JOB
import com.oneThing.first_matrch.NAV_LANGUAGE
import com.oneThing.first_matrch.R
import com.oneThing.first_matrch.UiError
import com.oneThing.first_matrch.UiSuccess
import com.oneThing.first_matrch.component.TopAppBarWithProgress
import com.oneThing.first_matrch.ui.diet.DietView
import com.oneThing.first_matrch.ui.intro.FirstMatchIntroView
import com.oneThing.first_matrch.ui.job.JobView
import com.oneThing.first_matrch.ui.language.LanguageView

@Composable
internal fun FirstMatchNav(
    viewModel: FirstMatchViewModel,
    goMatchPage: () -> Unit,
    home: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    val snackBarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val pageInfo = when (currentRoute) {
        NAV_JOB -> 1
        NAV_DIET -> 2
        NAV_LANGUAGE -> 3
        else -> -1
    }
    LaunchedEffect(uiState.error) {
        when (val error = uiState.error) {
            is UiError.Error -> {
                when (DomainError.fromCode(error.message)) {
                    DomainError.ERROR_SELECT_NONE -> {
                        snackBarHostState.showSnackbar(
                            message = context.getString(R.string.msg_select_one),
                            duration = SnackbarDuration.Short
                        )
                    }

                    DomainError.ERROR_NETWORK_ERROR -> {
                        snackBarHostState.showSnackbar(
                            message = context.getString(R.string.msg_network_error),
                            duration = SnackbarDuration.Short
                        )

                    }

                    DomainError.ERROR_SAVE_ERROR -> {
                        snackBarHostState.showSnackbar(
                            message = context.getString(R.string.msg_save_error),
                            duration = SnackbarDuration.Short
                        )
                    }

                    else -> Unit
                }
                viewModel.initSuccessError()
            }

            UiError.None -> Unit
        }
    }
    LaunchedEffect(uiState.success) {
        when (uiState.success) {
            is UiSuccess.Success -> {
                when (currentRoute) {
                    NAV_JOB -> {
                        navController.navigate(NAV_DIET) {
                            popUpTo(NAV_DIET) { inclusive = true }
                        }
                    }

                    NAV_DIET -> {
                        navController.navigate(NAV_LANGUAGE) {
                            popUpTo(NAV_LANGUAGE) { inclusive = true }
                        }
                    }

                    NAV_LANGUAGE -> {
                        goMatchPage()
                    }
                }
                viewModel.initSuccessError()
            }

            UiSuccess.None -> Unit
        }
    }

    Scaffold(
        topBar = {
            TopAppBarWithProgress(
                title = stringResource(R.string.top_app_bar_first_match),
                currentPage = pageInfo,
                totalPage = 4,
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
                HorizontalDivider(
                    thickness = 1.dp,
                    color = ColorStyle.GRAY_200
                )
                Spacer(modifier = Modifier.height(8.dp))
                ButtonXXLPurple400(
                    onClick = {
                        when (currentRoute) {
                            NAV_INTRO -> {
                                navController.navigate(NAV_JOB) {
                                    popUpTo(NAV_JOB) { inclusive = true }
                                }
                            }

                            NAV_JOB -> {
                                if (uiState.dataChange) viewModel.updateJob()
                                else navController.navigate(NAV_DIET) {
                                    popUpTo(NAV_DIET) { inclusive = true }
                                }
                            }

                            NAV_DIET -> {
                                if (uiState.dataChange) viewModel.updateDiet()
                                else navController.navigate(NAV_LANGUAGE) {
                                    popUpTo(NAV_LANGUAGE) { inclusive = true }
                                }
                            }

                            NAV_LANGUAGE -> {
                                if (uiState.dataChange) viewModel.updateLanguage()
                                else goMatchPage()
                            }
                        }
                    },
                    buttonText = when (currentRoute) {
                        NAV_LANGUAGE -> stringResource(R.string.btn_finish)
                        else -> stringResource(R.string.btn_next)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 17.dp),
                    isEnable = when (currentRoute) {
                        NAV_INTRO -> true
                        NAV_JOB -> uiState.job != JOB.NONE
                        NAV_DIET -> uiState.diet != DIET.NONE.displayName
                        NAV_LANGUAGE -> uiState.language != ""
                        else -> false
                    },
                )
            }
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState,
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
                            .calculateBottomPadding()
                    )
            )
        },
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = NAV_INTRO,
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding(),
                )
        ) {
            composable(
                NAV_INTRO,
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
                FirstMatchIntroView(
                    onBackClick = {
                        home()
                    }
                )
            }

            composable(
                NAV_JOB,
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
                JobView(
                    viewModel = viewModel,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }

            composable(
                NAV_DIET,
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
                DietView(
                    viewModel = viewModel,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }

            composable(
                NAV_LANGUAGE,
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
                LanguageView(
                    viewModel = viewModel,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}