package com.clip.editprofile.ui.signout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.clip.core.ButtonXXL
import com.clip.core.ColorStyle
import com.clip.core.CustomSnackBar
import com.clip.core.TopAppBarNumber
import com.clip.editprofile.ProfileEditViewModel
import com.clip.editprofile.R

@Composable
internal fun SignOutView(
    viewModel: ProfileEditViewModel,
    onBackClick: () -> Unit,
    goToLoginPage: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    Scaffold(
        topBar = {
            TopAppBarNumber(
                title = stringResource(R.string.top_sign_out),
                currentPage = 0,
                totalPage = 0,
                isPageTextShow = false,
                onBackClick = {
                    viewModel.initSuccessError()
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
                    .padding(
                        start = 17.dp, end = 16.dp, bottom = WindowInsets.navigationBars
                            .asPaddingValues()
                            .calculateTopPadding() + 70.dp
                    )
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .navigationBarsPadding()
            ) {
                HorizontalDivider(thickness = 1.dp, color = ColorStyle.GRAY_200)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(start = 17.dp, end = 16.dp)
                ) {
                    ButtonXXL(
                        onClick = {
                            viewModel.isDialogShow(true)
                        },
                        text = stringResource(R.string.btn_finish),
                        isEnable = uiState.buttonRun
                    )
                }
            }
        },
        contentColor = ColorStyle.WHITE_100
    ) { paddingValues ->
        SignOutMainView(
            viewModel = viewModel,
            onBackClick = onBackClick,
            goToLogin = goToLoginPage,
            paddingValues = paddingValues,
            snackBarHost = snackBarHostState
        )
    }
}