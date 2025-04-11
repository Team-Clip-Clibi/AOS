package com.sungil.editprofile.ui.changeNickName

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sungil.editprofile.ProfileEditViewModel
import com.sungil.editprofile.R
import com.sungil.editprofile.ui.CustomChangeDataAppBar
import com.sungil.editprofile.ui.CustomSnackBar

@Composable
internal fun ChangeNickNameView(
    viewModel: ProfileEditViewModel,
    onBackClick: () -> Unit,
    changeDataFinished: () -> Unit,
) {
    val snackBarHostState = remember { SnackbarHostState() }
    Scaffold(
        topBar = {
            CustomChangeDataAppBar(
                text = stringResource(R.string.top_bar_change_nick_name),
                onBackClick = {
                    viewModel.initFlow()
                    onBackClick()
                }
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState,
                snackbar = { data ->
                    CustomSnackBar(data)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 17.dp , end = 16.dp , bottom = 91.dp)
            )
        }
    ) { paddingValues ->
        ChangeNickNameMainView(
            paddingValues = paddingValues,
            viewModel = viewModel,
            finishedView = { changeDataFinished() },
            snackBarHost = snackBarHostState
        )
    }
}