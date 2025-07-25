package com.sungil.login

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sungil.login.ui.LoginScreen
import com.sungil.login.ui.PreviewOneThing
import com.sungil.login.ui.SplashScreen

@Composable
internal fun LoginNav(
    viewModel: LoginViewModel,
    kakao: () -> Unit,
    notification: () -> Unit,
    home: () -> Unit,
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = NAV_SPLASH
    ) {
        composable(NAV_SPLASH) {
            SplashScreen(
                viewModel = viewModel,
                login = {
                    navController.navigate(NAV_LOGIN) {
                        popUpTo(NAV_LOGIN) { inclusive = true }
                    }
                },
                notification = notification,
                home = home
            )
        }

        composable(
            NAV_LOGIN, enterTransition = {
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
            LoginScreen(
                viewModel = viewModel,
                kakaoLogin = kakao,
                preview = {
                    navController.navigate(NAV_PREVIEW) {
                        popUpTo(NAV_PREVIEW) { inclusive = true }
                    }
                }
            )
        }

        composable(
            NAV_PREVIEW, enterTransition = {
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
            PreviewOneThing(
                onclick = {
                    navController.popBackStack()
                }
            )
        }
    }
}