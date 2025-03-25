package com.example.signup.nav

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.signup.NAV_NAME
import com.example.signup.NAV_NICK
import com.example.signup.NAV_PHONE
import com.example.signup.NAV_TERM
import com.example.signup.SignUpViewModel
import com.example.signup.ui.name.InputNameScreen
import com.example.signup.ui.nickname.InputNickNameScreen
import com.example.signup.ui.phone.PhoneNumberScreen
import com.example.signup.ui.term.TermScreen

@Composable
internal fun SignUpNavigation(viewModel: SignUpViewModel, clear: () -> Unit, activity: Activity) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NAV_TERM
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

        composable(NAV_PHONE) {
            PhoneNumberScreen(
                viewModel = viewModel,
                actionClick = { navController.navigateUp() },
                buttonClick = {
                    navController.navigate(NAV_NAME)
                },
                activity = activity
            )
        }

        composable(NAV_NAME) {
            InputNameScreen(
                viewModel = viewModel,
                actionClick = { navController.navigateUp() },
                buttonClick = {
                    navController.navigate(NAV_NICK)
                }
            )
        }

        composable(NAV_NICK) {
            InputNickNameScreen(
                viewModel = viewModel,
                actionClick = { navController.navigateUp() },
                buttonClick = {

                }
            )
        }

    }
}