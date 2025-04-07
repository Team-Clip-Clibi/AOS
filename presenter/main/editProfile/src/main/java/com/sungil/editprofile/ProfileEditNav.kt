package com.sungil.editprofile

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
internal fun ProfileEditNav() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NAV_PROFILE_MAIN
    ) {
        composable(NAV_PROFILE_MAIN) {
            EditProfileView(
                actionButtonClick = {},
                buttonClick = {}
            )
        }
    }
}