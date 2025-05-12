package com.sungil.onethingmatch.nav

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sungil.onethingmatch.NAV_CATEGORY
import com.sungil.onethingmatch.NAV_INTRO
import com.sungil.onethingmatch.NAV_SUBJECT
import com.sungil.onethingmatch.OneThingViewModel
import com.sungil.onethingmatch.ui.categort.CategoryView
import com.sungil.onethingmatch.ui.intro.IntroView
import com.sungil.onethingmatch.ui.subject.InputSubjectView

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
            }){
            InputSubjectView(
                viewModel = viewModel,
                goNextPage = {}
            )
        }

        composable(NAV_CATEGORY ,
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
            }){
            CategoryView(
                viewModel = viewModel,
                goNextPage = { navController.navigate(NAV_SUBJECT)}
            )
        }

    }
}