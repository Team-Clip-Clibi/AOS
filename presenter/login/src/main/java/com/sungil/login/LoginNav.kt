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
    notification: () -> Unit,
    home: () -> Unit,
    isDebug : Boolean,
    activity: LoginActivity,
    signUp : () -> Unit,
    appVersion : String,
    playStore : () -> Unit
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
                home = home,
                appVersion = appVersion,
                isDebug = isDebug,
                playStore = playStore
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
                isDebug = isDebug,
                activity = activity,
                home = home,
                preview = {
                    navController.navigate(NAV_PREVIEW) {
                        popUpTo(NAV_PREVIEW) { inclusive = true }
                    }
                },
                signUp = {
                    signUp()
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