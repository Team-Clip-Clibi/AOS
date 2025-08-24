package com.sungil.editprofile.ui.changeJob

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
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
import com.example.core.ButtonXXL
import com.example.core.ColorStyle
import com.example.core.CustomSnackBar
import com.example.core.TopAppBarWithCloseButton
import com.sungil.editprofile.ProfileEditViewModel
import com.sungil.editprofile.R


@Composable
internal fun ChangeJobView(
    viewModel: ProfileEditViewModel,
    onBackClick: () -> Unit,
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            TopAppBarWithCloseButton(
                title = stringResource(R.string.top_job_change_job),
                onBackClick = {
                    viewModel.initSuccessError()
                    onBackClick()
                },
                isNavigationShow = false,
                isActionShow = true
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
                        start = 17.dp,
                        end = 16.dp,
                        bottom = WindowInsets.navigationBars
                            .asPaddingValues()
                            .calculateTopPadding()
                    )
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = ColorStyle.WHITE_100)
                    .padding(
                        bottom = WindowInsets.navigationBars
                            .asPaddingValues()
                            .calculateBottomPadding() + 8.dp
                    )
            ) {
                HorizontalDivider(thickness = 1.dp, color = ColorStyle.GRAY_200)
                Spacer(modifier = Modifier.height(8.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(start = 17.dp, end = 16.dp)
                ) {
                    ButtonXXL(
                        onClick = viewModel::changeJob,
                        text = stringResource(R.string.btn_finish),
                        isEnable = uiState.buttonRun
                    )
                }
            }
        },
    ) { paddingValues ->
        ChangeJobMainView(
            paddingValues = paddingValues,
            viewModel = viewModel,
            snackBarHost = snackBarHostState
        )
    }
}