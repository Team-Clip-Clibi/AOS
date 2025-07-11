package com.sungil.editprofile.ui.changeLanguage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core.ButtonLeftLarge
import com.sungil.editprofile.ERROR_FAIL_SAVE
import com.sungil.editprofile.ERROR_NETWORK
import com.sungil.editprofile.ERROR_TOKEN_NULL
import com.sungil.editprofile.ERROR_USER_DATA_NULL
import com.sungil.editprofile.LANGUAGE
import com.sungil.editprofile.ProfileEditViewModel
import com.sungil.editprofile.R
import com.sungil.editprofile.UiError
import com.sungil.editprofile.UiSuccess

@Composable
internal fun ChangeLanguageMainView(
    viewModel: ProfileEditViewModel,
    paddingValues: PaddingValues,
    snackBarHost: SnackbarHostState,
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(uiState.success, uiState.error) {
        when (val error = uiState.error) {
            is UiError.Error -> {
                when (error.message) {
                    ERROR_TOKEN_NULL -> {
                        snackBarHost.showSnackbar(
                            message = context.getString(R.string.txt_network_error),
                            duration = SnackbarDuration.Short
                        )
                    }

                    ERROR_USER_DATA_NULL -> {
                        snackBarHost.showSnackbar(
                            message = context.getString(R.string.msg_user_data_null),
                            duration = SnackbarDuration.Short
                        )
                    }

                    ERROR_NETWORK -> {
                        snackBarHost.showSnackbar(
                            message = context.getString(R.string.txt_network_error),
                            duration = SnackbarDuration.Short
                        )
                    }

                    ERROR_FAIL_SAVE -> {
                        snackBarHost.showSnackbar(
                            message = context.getString(R.string.msg_save_error),
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            }

            UiError.None -> Unit
        }
        when (uiState.success) {
            UiSuccess.None -> Unit
            is UiSuccess.Success -> {
                snackBarHost.showSnackbar(
                    message = context.getString(R.string.msg_save_success),
                    duration = SnackbarDuration.Short
                )
                viewModel.initSuccessError()
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = paddingValues.calculateTopPadding() + 32.dp,
                bottom = paddingValues.calculateBottomPadding(),
                start = 17.dp,
                end = 16.dp
            )
            .verticalScroll(rememberScrollState())
    ) {
        ButtonLeftLarge(
            text = stringResource(R.string.txt_language_kor),
            isSelected = uiState.language == LANGUAGE.KOREAN.name,
            onClick = {
                viewModel.setLanguage(LANGUAGE.KOREAN)
            }
        )
        Spacer(modifier = Modifier.height(10.dp))
        ButtonLeftLarge(
            text = stringResource(R.string.txt_language_eng),
            isSelected = uiState.language == LANGUAGE.ENGLISH.name,
            onClick = {
                viewModel.setLanguage(LANGUAGE.ENGLISH)
            }
        )
        Spacer(modifier = Modifier.height(10.dp))
        ButtonLeftLarge(
            text = stringResource(R.string.txt_language_both),
            isSelected = uiState.language == LANGUAGE.BOTH.name,
            onClick = {
                viewModel.setLanguage(LANGUAGE.BOTH)
            }
        )
    }
}