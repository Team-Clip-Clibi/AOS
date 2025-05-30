package com.oneThing.first_matrch.nav

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.oneThing.first_matrch.FirstMatchViewModel
import com.oneThing.first_matrch.NAV_DIET
import com.oneThing.first_matrch.NAV_INTRO
import com.oneThing.first_matrch.NAV_JOB
import com.oneThing.first_matrch.NAV_LANGUAGE
import com.oneThing.first_matrch.R
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
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    val pageInfo = when (currentRoute) {
        NAV_JOB -> 1
        NAV_DIET -> 2
        NAV_LANGUAGE -> 3
        else -> -1
    }
    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBarWithProgress(
            title = stringResource(R.string.top_app_bar_first_match),
            currentPage = pageInfo,
            totalPage = 4,
            onBackClick = {
                if (!navController.popBackStack()) home()
            }
        )
        NavHost(
            navController = navController,
            startDestination = NAV_INTRO,
            modifier = Modifier.weight(1f)
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
                    goNextPage = {
                        navController.navigate(NAV_JOB)
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
                    goNextPage = {
                        navController.navigate(NAV_DIET)
                    },
                    viewModel = viewModel
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
                    goNextPage = {
                        navController.navigate(NAV_LANGUAGE)
                    },
                    viewModel = viewModel
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
                    goNextPage = {
                        goMatchPage()
                    },
                    viewModel = viewModel
                )
            }
        }
    }
}