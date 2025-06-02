package com.oneThing.first_matrch.ui.language

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.HorizontalDivider
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
import com.example.core.ButtonLeftLarge
import com.example.core.ButtonXXLPurple400
import com.example.core.ColorStyle
import com.example.core.CustomSnackBar
import com.oneThing.first_matrch.DomainError
import com.oneThing.first_matrch.FirstMatchViewModel
import com.oneThing.first_matrch.LANGUAGE
import com.oneThing.first_matrch.MESSAGE_SAVE_SUCCESS
import com.oneThing.first_matrch.MESSAGE_SUCCESS
import com.oneThing.first_matrch.R
import com.oneThing.first_matrch.UiError
import com.oneThing.first_matrch.UiSuccess

@Composable
internal fun LanguageView(
    viewModel: FirstMatchViewModel,
    goNextPage: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    LaunchedEffect(uiState.error, uiState.success) {
        when (val success = uiState.success) {
            is UiSuccess.Success -> {
                when (success.message) {
                    MESSAGE_SUCCESS -> {
                        viewModel.initSuccessError()
                        viewModel.saveFirstMatch()
                    }

                    MESSAGE_SAVE_SUCCESS -> {
                        viewModel.initSuccessError()
                        goNextPage()
                    }
                }
            }

            UiSuccess.None -> Unit
        }

        when (val error = uiState.error) {
            is UiError.Error -> {
                viewModel.initSuccessError()
                when (DomainError.fromCode(error.message)) {
                    DomainError.ERROR_NETWORK_ERROR -> {
                        snackbarHostState.showSnackbar(
                            message = context.getString(R.string.msg_network_error),
                            duration = SnackbarDuration.Short
                        )

                    }

                    DomainError.ERROR_SAVE_ERROR -> {
                        snackbarHostState.showSnackbar(
                            message = context.getString(R.string.msg_save_error),
                            duration = SnackbarDuration.Short
                        )
                    }

                    else -> Unit
                }
            }

            UiError.None -> Unit
        }
    }

    Scaffold(
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(bottom = 8.dp)
            ) {
                HorizontalDivider(
                    thickness = 1.dp,
                    color = ColorStyle.GRAY_200
                )
                Spacer(modifier = Modifier.height(8.dp))
                ButtonXXLPurple400(
                    onClick = {
                        if (uiState.dataChange) viewModel.updateLanguage()
                        else viewModel.saveFirstMatch()
                    },
                    buttonText = stringResource(R.string.btn_finish),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 17.dp),
                    isEnable = uiState.language != ""
                )
            }
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
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
                            .calculateBottomPadding() + 32.dp
                    )
            )
        },
        contentColor = ColorStyle.WHITE_100
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .background(color = ColorStyle.WHITE_100)
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding() + 32.dp,
                    start = 17.dp,
                    end = 16.dp
                )
        ) {
            Text(
                text = stringResource(R.string.txt_language_title),
                style = AppTextStyles.HEAD_28_40_BOLD,
                color = ColorStyle.GRAY_800,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(R.string.txt_language_sub_title),
                style = AppTextStyles.SUBTITLE_16_24_SEMI,
                color = ColorStyle.GRAY_600,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))
            ButtonLeftLarge(
                text = stringResource(R.string.txt_language_item_korean),
                isSelected = uiState.language == LANGUAGE.KOREAN.name,
                onClick = {
                    viewModel.language(LANGUAGE.KOREAN.name)
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
            ButtonLeftLarge(
                text = stringResource(R.string.txt_language_item_english),
                isSelected = uiState.language == LANGUAGE.ENGLISH.name,
                onClick = {
                    viewModel.language(LANGUAGE.ENGLISH.name)
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
            ButtonLeftLarge(
                text = stringResource(R.string.txT_language_item_both),
                isSelected = uiState.language == LANGUAGE.BOTH.name,
                onClick = {
                    viewModel.language(LANGUAGE.BOTH.name)
                }
            )
        }
    }
}