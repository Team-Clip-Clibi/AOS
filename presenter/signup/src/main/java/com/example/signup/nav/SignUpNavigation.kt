package com.example.signup.nav

import android.app.Activity
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.signup.NAV_ALREADY_SIGN_UP
import com.example.signup.NAV_INFO
import com.example.signup.NAV_NAME
import com.example.signup.NAV_NICK
import com.example.signup.NAV_PHONE
import com.example.signup.NAV_TERM
import com.example.signup.SignUpViewModel
import com.example.signup.ui.alreadySignUp.AlreadySignUpScreen
import com.example.signup.ui.detail.InputDetailInfoScreen
import com.example.signup.ui.name.InputNameScreen
import com.example.signup.ui.nickname.InputNickNameScreen
import com.example.signup.ui.phone.PhoneNumberScreen
import com.example.signup.ui.term.TermScreen

@Composable
internal fun SignUpNavigation(viewModel: SignUpViewModel, clear: () -> Unit, main : () -> Unit) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NAV_INFO
    ) {
        composable(NAV_TERM) {
            TermScreen(
                viewModel = viewModel,
                actionBarClick = {
                    clear()
                },
                buttonClick = {
                    navController.navigate(NAV_PHONE)
                })
        }

        composable(NAV_PHONE,
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
            PhoneNumberScreen(
                viewModel = viewModel,
                actionClick = { navController.navigateUp() },
                buttonClick = {
                    navController.navigate(NAV_NAME)
                },
                signUpPage = { navController.navigate(NAV_ALREADY_SIGN_UP) },
            )
        }

        composable(NAV_NAME,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(700)
                )
            }, popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(700)
                )
            }) {
            InputNameScreen(
                viewModel = viewModel,
                actionClick = { navController.navigateUp() },
                buttonClick = {
                    navController.navigate(NAV_NICK)
                }
            )
        }

        composable(NAV_NICK,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(700)
                )
            }, popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(700)
                )
            }) {
            InputNickNameScreen(
                viewModel = viewModel,
                actionClick = { navController.navigateUp() },
                buttonClick = {
                    navController.navigate(NAV_INFO)
                }
            )
        }

        composable(NAV_INFO,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(700)
                )
            }, popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(700)
                )
            }) {
            InputDetailInfoScreen(
                viewModel = viewModel,
                actionClick = { navController.navigateUp() },
                buttonClick = {
                    main()
                }
            )
        }

        composable(
            NAV_ALREADY_SIGN_UP,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(700)
                )
            }, popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(700)
                )
            }) {
            AlreadySignUpScreen(
                viewModel = viewModel,
                actionClick = { navController.navigateUp() },
                buttonClick = {
                    clear()
                }
            )
        }
    }
}