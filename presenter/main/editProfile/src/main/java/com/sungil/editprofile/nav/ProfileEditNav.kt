package com.sungil.editprofile.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sungil.editprofile.NAV_CHANGE_NICK_NAME
import com.sungil.editprofile.NAV_PROFILE_MAIN
import com.sungil.editprofile.ProfileEditViewModel
import com.sungil.editprofile.ui.changeNickName.ChangeNickNameView
import com.sungil.editprofile.ui.editProfile.EditProfileView

@Composable
internal fun ProfileEditNav(viewModel: ProfileEditViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NAV_PROFILE_MAIN
    ) {
        composable(NAV_PROFILE_MAIN) {
            EditProfileView(
                viewModel = viewModel,
                actionButtonClick = {},
                buttonClick = {
                    navController.navigate(NAV_CHANGE_NICK_NAME)
                }
            )
        }

        composable(NAV_CHANGE_NICK_NAME) {
            ChangeNickNameView(
                viewModel = viewModel,
                onBackClick = {},
                changeDataFinished = {}
            )
        }
    }
}