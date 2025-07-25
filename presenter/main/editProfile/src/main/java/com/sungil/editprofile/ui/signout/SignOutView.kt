package com.sungil.editprofile.ui.signout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
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
import com.example.core.ButtonXXLPurple400
import com.example.core.ColorStyle
import com.example.core.CustomSnackBar
import com.example.core.TopAppBarNumber
import com.sungil.editprofile.ProfileEditViewModel
import com.sungil.editprofile.R

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
            Column(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp).navigationBarsPadding()) {
                HorizontalDivider(thickness = 1.dp, color = ColorStyle.GRAY_200)
                ButtonXXLPurple400(
                    onClick = {
                        viewModel.isDialogShow(true)
                    },
                    buttonText = stringResource(R.string.btn_finish),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(start = 17.dp, end = 16.dp),
                    isEnable = uiState.buttonRun
                )
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