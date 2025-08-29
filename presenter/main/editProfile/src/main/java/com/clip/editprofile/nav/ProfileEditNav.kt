package com.clip.editprofile.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.clip.editprofile.NAV_CHANGE_JOB
import com.clip.editprofile.NAV_CHANGE_LANGUAGE
import com.clip.editprofile.NAV_CHANGE_LOVE
import com.clip.editprofile.NAV_CHANGE_NICK_NAME
import com.clip.editprofile.NAV_DIET
import com.clip.editprofile.NAV_PROFILE_MAIN
import com.clip.editprofile.NAV_SIGN_OUT
import com.clip.editprofile.ProfileEditViewModel
import com.clip.editprofile.ui.changeDiet.DietView
import com.clip.editprofile.ui.changeJob.ChangeJobView
import com.clip.editprofile.ui.changeLanguage.ChangeLanguageView
import com.clip.editprofile.ui.changeNickName.ChangeNickNameView
import com.clip.editprofile.ui.editProfile.EditProfileView
import com.clip.editprofile.ui.loveState.LoveStateView
import com.clip.editprofile.ui.signout.SignOutView

@Composable
fun ProfileEditNav(
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