package com.sungil.onethingmatch.nav

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sungil.onethingmatch.NAV_INPUT_DATA
import com.sungil.onethingmatch.NAV_INTRO
import com.sungil.onethingmatch.OneThingViewModel
import com.sungil.onethingmatch.ui.InputMatchDataView
import com.sungil.onethingmatch.ui.IntroView

@Composable
internal fun OneThingNav(
    viewModel: OneThingViewModel,
    home: () -> Unit,
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NAV_INTRO
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
                goOneThingPage = { navController.navigate(NAV_INPUT_DATA) }
            )
        }

        composable(NAV_INPUT_DATA, enterTransition = {
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
            InputMatchDataView(
                nextPage = {},
                home = home,
                viewModel = viewModel
            )
        }
    }
}