package com.sungil.editprofile.ui.changeNickName

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.sungil.editprofile.ProfileEditViewModel
import com.sungil.editprofile.R
import com.sungil.editprofile.ui.CustomChangeDataAppBar

@Composable
internal fun ChangeNickNameView(
    viewModel: ProfileEditViewModel,
    onBackClick: () -> Unit,
    changeDataFinished: () -> Unit,
) {
    Scaffold(
        topBar = {
            CustomChangeDataAppBar(
                text = stringResource(R.string.top_bar_change_nick_name),
                onBackClick = { onBackClick() }
            )
        }
    ) { paddingValues ->
        ChangeNickNameMainView(
            paddingValues = paddingValues,
            viewModel = viewModel,
            finishedView = { changeDataFinished() }
        )
    }
}