package com.sungil.onethingmatch.nav

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
import com.sungil.onethingmatch.NAV_CATEGORY
import com.sungil.onethingmatch.NAV_INTRO
import com.sungil.onethingmatch.NAV_SUBJECT
import com.sungil.onethingmatch.OneThingViewModel
import com.sungil.onethingmatch.R
import com.sungil.onethingmatch.component.TopAppBarWithProgress
import com.sungil.onethingmatch.ui.categort.CategoryView
import com.sungil.onethingmatch.ui.intro.IntroView
import com.sungil.onethingmatch.ui.subject.InputSubjectView

@Composable
fun OneThingNav(
    viewModel: OneThingViewModel,
    home: () -> Unit,
) {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    val pageInfo = when (currentRoute) {
        NAV_SUBJECT -> 1
        NAV_CATEGORY -> 2
        NAV_INTRO -> -1
        else -> -1
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBarWithProgress(
            title = stringResource(R.string.top_app_bar),
            currentPage = pageInfo,
            totalPage = 5,
            onBackClick = {
                if (!navController.popBackStack()) home()
            }
        )

        NavHost(
            navController = navController,
            startDestination = NAV_INTRO,
            modifier = Modifier.weight(1f)
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
                IntroView(
                    goSubjectPage = { navController.navigate(NAV_SUBJECT) },
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
                    viewModel = viewModel,
                    goNextPage = { navController.navigate(NAV_CATEGORY) }
                )
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
                    viewModel = viewModel,
                    goNextPage = {  }
                )
            }
        }
    }
}