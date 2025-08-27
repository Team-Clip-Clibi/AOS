package com.clip.editprofile.ui.editProfile

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.clip.core.TopAppbar
import com.clip.editprofile.ProfileEditViewModel
import com.clip.editprofile.R

@Composable
internal fun EditProfileView(
    viewModel: ProfileEditViewModel,
    actionButtonClick: () -> Unit,
    editNickNameClick: () -> Unit,
    editJobClick: () -> Unit,
    editLoveClick: () -> Unit,
    editLanguageClick: () -> Unit,
    goToLoginPage : () -> Unit,
    signOutPage : () -> Unit,
    dietPage : () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppbar(
                title = stringResource(R.string.top_bar_profile),
                onBackClick = actionButtonClick,
            )
        }
    ) { paddingValues ->
        EditProfileMainView(
            paddingValues,
            editNickNameClick = editNickNameClick,
            editJobClick = editJobClick,
            viewModel = viewModel,
            loveClick = editLoveClick,
            editLanguageClick = editLanguageClick,
            goToLoginPage = goToLoginPage,
            signOutPage = signOutPage,
            dietPage = dietPage,
            actionButtonClick = actionButtonClick
        )
    }
}