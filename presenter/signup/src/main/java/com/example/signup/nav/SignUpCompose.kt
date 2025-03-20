package com.example.signup.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.signup.NAV_PHONE
import com.example.signup.NAV_TERM
import com.example.signup.SignUpViewModel
import com.example.signup.ui.PhoneNumberScreen
import com.example.signup.ui.term.TermScreen

@Composable
internal fun SignUpCompose(viewModel : SignUpViewModel , viewFinish : () -> Unit) {
    val navController = rememberNavController()
    NavHost(navController = navController , startDestination = NAV_TERM){
        composable(NAV_TERM) {
            TermScreen(
              viewModel,
                actionButtonClick = {
                    viewFinish()
                },
                nextButtonClick = {
                    navController.navigate(NAV_PHONE)
                }
            )
        }
        composable(NAV_PHONE) {
            PhoneNumberScreen()
        }
    }
}