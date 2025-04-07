package com.sungil.editprofile

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

@Composable
internal fun EditProfileView(
    actionButtonClick: () -> Unit,
    buttonClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            CustomTopBar(
                title = stringResource(R.string.top_bar_profile),
                onBackClick = { actionButtonClick() }
            )
        }
    ) { paddingValues ->
        EditProfileMainView(paddingValues, buttonClick = {})
    }
}