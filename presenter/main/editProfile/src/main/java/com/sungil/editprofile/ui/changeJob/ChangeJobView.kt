package com.sungil.editprofile.ui.changeJob

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.sungil.editprofile.ProfileEditViewModel
import com.sungil.editprofile.R
import com.sungil.editprofile.ui.CustomChangeDataAppBar

@Composable
internal fun ChangeJobView(
    viewModel: ProfileEditViewModel,
    onBackClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            CustomChangeDataAppBar(
                text = stringResource(R.string.top_job_change_job),
                onBackClick = { onBackClick() }
            )
        }
    ) { paddingValues ->
        ChangeJobMainView(
            paddingValues = paddingValues,
            viewModel = viewModel
        )
    }
}