package com.oneThing.editprofile.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.oneThing.editprofile.NAV_CHANGE_JOB
import com.oneThing.editprofile.NAV_CHANGE_LANGUAGE
import com.oneThing.editprofile.NAV_CHANGE_LOVE
import com.oneThing.editprofile.NAV_CHANGE_NICK_NAME
import com.oneThing.editprofile.NAV_DIET
import com.oneThing.editprofile.NAV_PROFILE_MAIN
import com.oneThing.editprofile.NAV_SIGN_OUT
import com.oneThing.editprofile.ProfileEditViewModel
import com.oneThing.editprofile.ui.changeDiet.DietView
import com.oneThing.editprofile.ui.changeJob.ChangeJobView
import com.oneThing.editprofile.ui.changeLanguage.ChangeLanguageView
import com.oneThing.editprofile.ui.changeNickName.ChangeNickNameView
import com.oneThing.editprofile.ui.editProfile.EditProfileView
import com.oneThing.editprofile.ui.loveState.LoveStateView
import com.oneThing.editprofile.ui.signout.SignOutView

@Composable
internal fun ProfileEditNav(
    viewModel: ProfileEditViewModel,
    goToLoginPage: () -> Unit,
    goToMainPage: () -> Unit,
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NAV_PROFILE_MAIN
    ) {
        composable(NAV_PROFILE_MAIN) {
            EditProfileView(
                viewModel = viewModel,
                actionButtonClick = {goToMainPage()},
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
                },
                goToLoginPage = {
                    goToLoginPage()
                },
                signOutPage = {
                    navController.navigate(NAV_SIGN_OUT)
                },
                dietPage = {
                    navController.navigate(NAV_DIET)
                }
            )
        }

        composable(NAV_CHANGE_NICK_NAME) {
            ChangeNickNameView(
                viewModel = viewModel,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
        composable(NAV_CHANGE_JOB) {
            ChangeJobView(
                viewModel = viewModel,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
        composable(NAV_CHANGE_LOVE) {
            LoveStateView(
                viewModel = viewModel,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(NAV_CHANGE_LANGUAGE) {
            ChangeLanguageView(
                viewModel = viewModel,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
        composable(NAV_SIGN_OUT) {
            SignOutView(
                viewModel = viewModel,
                onBackClick = {
                    navController.popBackStack()
                },
                goToLoginPage = {
                    goToLoginPage()
                }
            )
        }
        composable(NAV_DIET) {
            DietView(
                viewModel = viewModel,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

    }
}