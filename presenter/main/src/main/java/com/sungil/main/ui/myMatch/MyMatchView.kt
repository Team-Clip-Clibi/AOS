package com.sungil.main.ui.myMatch

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core.AppTextStyles
import com.example.core.ColorStyle
import com.example.core.CustomSnackBar
import com.sungil.domain.model.UserData
import com.sungil.main.ERROR_NETWORK_ERROR
import com.sungil.main.MainViewModel
import com.sungil.main.R
import com.sungil.main.component.CustomMainPageTopBar
import com.sungil.onethingmatch.ERROR_RE_LOGIN

@Composable
fun MyMatchView(viewModel: MainViewModel, login: () -> Unit) {
    val snackBarHostState = remember { SnackbarHostState() }
    val state by viewModel.userState.collectAsState()
    val latestDay = state.latestDay
    val context = LocalContext.current
    val userName = when (state.userDataState) {
        is MainViewModel.UiState.Success -> (state.userDataState as MainViewModel.UiState.Success<UserData>).data.nickName
        is MainViewModel.UiState.Loading -> ""
        is MainViewModel.UiState.Error -> "오류 발생"
    }
    LaunchedEffect(latestDay) {
        when (latestDay) {
            is MainViewModel.UiState.Error -> {
                when (latestDay.message) {
                    ERROR_RE_LOGIN -> {
                        login()
                    }

                    ERROR_NETWORK_ERROR -> {
                        snackBarHostState.showSnackbar(
                            message = context.getString(R.string.msg_network_error),
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            }

            else -> Unit
        }
    }
    Scaffold(
        topBar = {
            CustomMainPageTopBar(text = stringResource(R.string.my_match_top_bar))
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
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding()
                )
        ) {
            //Top View
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = ColorStyle.WHITE_100)
                    .padding(top = 16.dp, start = 17.dp, end = 16.dp)
            ) {
                when (latestDay) {
                    is MainViewModel.UiState.Error -> {
                        Text(
                            text = stringResource(R.string.my_match_title_not_approve , userName ?: "error"),
                            style = AppTextStyles.TITLE_20_28_SEMI,
                            color = ColorStyle.GRAY_800
                        )
                    }

                    is MainViewModel.UiState.Success -> {
                        if (latestDay.data.applyTime == 0 && latestDay.data.confirmTime == 0) {
                            Text(
                                text = stringResource(R.string.my_match_title_not_apply),
                                style = AppTextStyles.TITLE_20_28_SEMI,
                                color = ColorStyle.GRAY_800
                            )
                            return@Scaffold
                        }
                        if (latestDay.data.applyTime != 0 && latestDay.data.confirmTime == 0) {
                            Text(
                                text = stringResource(
                                    R.string.my_match_title_not_approve,
                                    userName ?: "error"
                                ),
                                style = AppTextStyles.TITLE_20_28_SEMI,
                                color = ColorStyle.GRAY_800
                            )
                            return@Scaffold
                        }
                        Text(
                            text = stringResource(R.string.my_match_title, userName ?: "error"),
                            style = AppTextStyles.HEAD_28_40_BOLD,
                            color = ColorStyle.GRAY_800,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text(
                            text = stringResource(R.string.my_match_content, latestDay.data),
                            style = AppTextStyles.HEAD_28_40_BOLD,
                            color = ColorStyle.PURPLE_400,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    else -> Unit
                }
            }
        }
    }
}