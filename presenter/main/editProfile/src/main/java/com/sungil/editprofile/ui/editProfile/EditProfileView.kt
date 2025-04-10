package com.sungil.editprofile.ui.editProfile

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.sungil.editprofile.ProfileEditViewModel
import com.sungil.editprofile.R
import com.sungil.editprofile.ui.CustomTopBar

@Composable
internal fun EditProfileView(
    viewModel: ProfileEditViewModel,
    actionButtonClick: () -> Unit,
    editNickNameClick: () -> Unit,
    editJobClick: () -> Unit,
    editLoveClick: () -> Unit,
    editLanguageClick: () -> Unit,
    goToLoginPage : () -> Unit
) {
    Scaffold(
        topBar = {
            CustomTopBar(
                title = stringResource(R.string.top_bar_profile),
                onBackClick = { actionButtonClick() }
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
            goToLoginPage = goToLoginPage
        )
    }
}