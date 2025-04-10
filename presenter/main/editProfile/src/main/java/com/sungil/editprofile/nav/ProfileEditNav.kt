package com.sungil.editprofile.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sungil.editprofile.NAV_CHANGE_JOB
import com.sungil.editprofile.NAV_CHANGE_LANGUAGE
import com.sungil.editprofile.NAV_CHANGE_LOVE
import com.sungil.editprofile.NAV_CHANGE_NICK_NAME
import com.sungil.editprofile.NAV_PROFILE_MAIN
import com.sungil.editprofile.ProfileEditViewModel
import com.sungil.editprofile.ui.changeJob.ChangeJobView
import com.sungil.editprofile.ui.changeLanguage.ChangeLanguageView
import com.sungil.editprofile.ui.changeNickName.ChangeNickNameView
import com.sungil.editprofile.ui.editProfile.EditProfileView
import com.sungil.editprofile.ui.loveState.LoveStateView

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
                editNickNameClick = {
                    navController.navigate(NAV_CHANGE_NICK_NAME)
                },
                editJobClick = {
                    navController.navigate(NAV_CHANGE_JOB)
                },
                editLoveClick = {
                    navController.navigate(NAV_CHANGE_LOVE)
                },
                editLanguageClick = {
                    navController.navigate(NAV_CHANGE_LANGUAGE)
                }
            )
        }

        composable(NAV_CHANGE_NICK_NAME) {
            ChangeNickNameView(
                viewModel = viewModel,
                onBackClick = {
                    navController.navigateUp()
                },
                changeDataFinished = {
                    navController.navigateUp()
                }
            )
        }
        composable(NAV_CHANGE_JOB) {
            ChangeJobView(
                viewModel = viewModel,
                onBackClick = {
                    navController.navigateUp()
                }
            )
        }
        composable(NAV_CHANGE_LOVE) {
            LoveStateView(
                viewModel = viewModel,
                onBackClick = {
                    navController.navigateUp()
                },
                changeDataFinished = {
                    navController.navigateUp()
                }
            )
        }

        composable(NAV_CHANGE_LANGUAGE) {
            ChangeLanguageView(
                viewModel = viewModel,
                onBackClick = {
                    navController.navigateUp()
                }
            )
        }

    }
}