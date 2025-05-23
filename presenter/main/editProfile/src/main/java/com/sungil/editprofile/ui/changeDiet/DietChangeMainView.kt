package com.sungil.editprofile.ui.changeDiet

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.core.AppTextStyles
import com.example.core.ButtonLeftLarge
import com.example.core.ColorStyle
import com.example.core.TextFieldComponent
import com.sungil.editprofile.DIET
import com.sungil.editprofile.ERROR_FAIL_SAVE
import com.sungil.editprofile.ERROR_FAIL_TO_UPDATE_LOVE
import com.sungil.editprofile.ERROR_TOKEN_NULL
import com.sungil.editprofile.ProfileEditViewModel
import com.sungil.editprofile.R
import com.sungil.editprofile.UiError
import com.sungil.editprofile.UiSuccess


@Composable
internal fun DietChangeMainView(
    viewModel: ProfileEditViewModel,
    paddingValues: PaddingValues,
    snackBarHost: SnackbarHostState,
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
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

                    ERROR_FAIL_TO_UPDATE_LOVE -> {
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
                viewModel.initSuccessError()
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
                start = 17.dp,
                end = 16.dp,
                bottom = paddingValues.calculateBottomPadding()
            )
    ) {
        ButtonLeftLarge(
            text = stringResource(R.string.txt_diet_vg),
            isSelected = uiState.diet == DIET.VG.displayName,
            onClick = {
                viewModel.changeDiet(DIET.VG)
            }
        )
        Spacer(Modifier.height(10.dp))
        ButtonLeftLarge(
            text = stringResource(R.string.txt_diet_vt),
            isSelected = uiState.diet == DIET.VT.displayName,
            onClick = {
                viewModel.changeDiet(DIET.VT)
            }
        )
        Spacer(Modifier.height(10.dp))
        ButtonLeftLarge(
            text = stringResource(R.string.txt_diet_gf),
            isSelected = uiState.diet == DIET.GF.displayName,
            onClick = {
                viewModel.changeDiet(DIET.GF)
            }
        )
        Spacer(Modifier.height(10.dp))
        ButtonLeftLarge(
            text = stringResource(R.string.txt_diet_all),
            isSelected = uiState.diet == DIET.ALL.displayName,
            onClick = {
                viewModel.changeDiet(DIET.ALL)
            }
        )
        Spacer(Modifier.height(10.dp))
        ButtonLeftLarge(
            text = stringResource(R.string.txt_diet_etc),
            isSelected = uiState.diet == DIET.ETC.displayName,
            onClick = {
                viewModel.changeDiet(DIET.ETC)
            }
        )
        Spacer(modifier = Modifier.height(10.dp))
        HorizontalDivider(thickness = 1.dp, color = ColorStyle.GRAY_200)
        Spacer(modifier = Modifier.height(10.dp))
        if (uiState.diet == DIET.ETC.displayName) {
            TextFieldComponent(
                value = uiState.dietContent,
                onValueChange = viewModel::changeDietETCContent,
                hint = stringResource(R.string.hint_diet_data),
                maxLine = 1,
                maxLength = 100,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${uiState.dietContent}/100",
                style = AppTextStyles.CAPTION_10_14_MEDIUM,
                color = ColorStyle.GRAY_700,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )
        }
    }
}
