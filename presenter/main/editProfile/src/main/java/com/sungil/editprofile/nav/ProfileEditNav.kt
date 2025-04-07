package com.sungil.editprofile.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sungil.editprofile.NAV_PROFILE_MAIN
import com.sungil.editprofile.ui.editProfile.EditProfileView

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