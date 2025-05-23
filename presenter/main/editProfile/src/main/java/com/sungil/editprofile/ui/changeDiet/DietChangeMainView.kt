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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.core.ButtonLeftLarge
import com.example.core.ButtonXXLPurple400
import com.example.core.ColorStyle
import com.sungil.editprofile.DIET
import com.sungil.editprofile.ERROR_FAIL_SAVE
import com.sungil.editprofile.ERROR_FAIL_TO_UPDATE_LOVE
import com.sungil.editprofile.ERROR_TOKEN_NULL
import com.sungil.editprofile.ProfileEditViewModel
import com.sungil.editprofile.R
import com.sungil.editprofile.UiError
import com.sungil.editprofile.UiSuccess
import com.sungil.editprofile.ui.CustomButton
import com.sungil.editprofile.ui.CustomChangeDataTextField
import com.sungil.editprofile.ui.CustomItemPick

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

    Box(
        Modifier
            .background(ColorStyle.WHITE_100)
            .fillMaxSize()
            .padding(top = paddingValues.calculateTopPadding() + 32.dp, bottom = 8.dp)
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .align(Alignment.TopCenter)
                .padding(start = 17.dp, end = 16.dp)
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
            HorizontalDivider(thickness = 1.dp, color = Color(0xFFEFEFEF))

            Spacer(modifier = Modifier.height(10.dp))

            if (uiState.diet == DIET.ETC.displayName) {
                CustomChangeDataTextField(
                    beforeText = uiState.diet,
                    inputType = KeyboardType.Text,
                    onValueChange = { data ->
                        viewModel.changeDietETCContent(data)
                    }
                )
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .background(Color(0xFFFFFFFF))
                .fillMaxWidth()
        ) {
            HorizontalDivider(thickness = 1.dp, color = Color(0xFFEFEFEF))
            Spacer(modifier = Modifier.height(8.dp))
            ButtonXXLPurple400(
                onClick = { viewModel.updateDiet() },
                buttonText = stringResource(R.string.btn_finish),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                isEnable = uiState.diet != DIET.NONE.displayName
            )
        }
    }
}
