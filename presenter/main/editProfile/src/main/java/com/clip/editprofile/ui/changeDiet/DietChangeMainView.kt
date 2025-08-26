package com.clip.editprofile.ui.changeDiet


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
import com.clip.core.AppTextStyles
import com.clip.core.ButtonL
import com.clip.core.ColorStyle
import com.clip.core.TextFieldComponent
import com.clip.editprofile.DIET
import com.clip.editprofile.ERROR_FAIL_SAVE
import com.clip.editprofile.ERROR_FAIL_TO_UPDATE_LOVE
import com.clip.editprofile.ERROR_TOKEN_NULL
import com.clip.editprofile.ProfileEditViewModel
import com.clip.editprofile.R
import com.clip.editprofile.UiError
import com.clip.editprofile.UiSuccess


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
        ButtonL(
            text = stringResource(R.string.txt_diet_all),
            onClick = {
                viewModel.changeDiet(DIET.ALL)
            },
            isSelected = uiState.diet == DIET.ALL.displayName,
            borderUse = uiState.diet == DIET.ALL.displayName,
            borderColor = ColorStyle.PURPLE_200,
            buttonColor = if (uiState.diet == DIET.ALL.displayName) ColorStyle.PURPLE_100 else ColorStyle.GRAY_100,
            textCenter = false
        )
        Spacer(Modifier.height(10.dp))
        ButtonL(
            text = stringResource(R.string.txt_diet_vg),
            onClick = {
                viewModel.changeDiet(DIET.VG)
            },
            isSelected = uiState.diet == DIET.VG.displayName,
            borderUse = uiState.diet == DIET.VG.displayName,
            borderColor = ColorStyle.PURPLE_200,
            buttonColor = if (uiState.diet == DIET.VG.displayName) ColorStyle.PURPLE_100 else ColorStyle.GRAY_100,
            textCenter = false
        )
        Spacer(Modifier.height(10.dp))
        ButtonL(
            text = stringResource(R.string.txt_diet_vt),
            onClick = {
                viewModel.changeDiet(DIET.VT)
            },
            isSelected = uiState.diet == DIET.VT.displayName,
            borderUse = uiState.diet == DIET.VT.displayName,
            borderColor = ColorStyle.PURPLE_200,
            buttonColor = if (uiState.diet == DIET.VT.displayName) ColorStyle.PURPLE_100 else ColorStyle.GRAY_100,
            textCenter = false
        )
        Spacer(Modifier.height(10.dp))
        ButtonL(
            text = stringResource(R.string.txt_diet_gf),
            onClick = {
                viewModel.changeDiet(DIET.GF)
            },
            isSelected = uiState.diet == DIET.GF.displayName,
            borderUse = uiState.diet == DIET.GF.displayName,
            borderColor = ColorStyle.PURPLE_200,
            buttonColor = if (uiState.diet == DIET.GF.displayName) ColorStyle.PURPLE_100 else ColorStyle.GRAY_100,
            textCenter = false
        )
        Spacer(Modifier.height(10.dp))
        Spacer(Modifier.height(10.dp))
        ButtonL(
            text = stringResource(R.string.txt_diet_etc),
            onClick = {
                viewModel.changeDiet(DIET.ETC)
            },
            isSelected = uiState.diet == DIET.ETC.displayName,
            borderUse = uiState.diet == DIET.ETC.displayName,
            borderColor = ColorStyle.PURPLE_200,
            buttonColor = if (uiState.diet == DIET.ETC.displayName) ColorStyle.PURPLE_100 else ColorStyle.GRAY_100,
            textCenter = false
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
                text = "${uiState.dietContent.length}/100",
                style = AppTextStyles.CAPTION_10_14_MEDIUM,
                color = ColorStyle.GRAY_700,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )
        }
    }
}
