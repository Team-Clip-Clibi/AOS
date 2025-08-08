package com.sungil.editprofile.ui.editProfile

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.core.TopAppBarNumber
import com.sungil.editprofile.ProfileEditViewModel
import com.sungil.editprofile.R

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
            TopAppBarNumber(
                title = stringResource(R.string.top_bar_profile),
                currentPage = 0,
                totalPage =  0,
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