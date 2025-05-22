package com.sungil.editprofile.ui.loveState

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core.AppTextStyles
import com.example.core.ButtonLeftLarge
import com.example.core.ColorStyle
import com.sungil.editprofile.ERROR_FAIL_SAVE
import com.sungil.editprofile.ERROR_FAIL_TO_UPDATE_LOVE
import com.sungil.editprofile.ERROR_TOKEN_NULL
import com.sungil.editprofile.LOVE
import com.sungil.editprofile.MEETING
import com.sungil.editprofile.ProfileEditViewModel
import com.sungil.editprofile.R
import com.sungil.editprofile.UiError
import com.sungil.editprofile.UiSuccess
import com.sungil.editprofile.ui.CustomButton
import com.sungil.editprofile.ui.CustomItemPick
import com.sungil.editprofile.ui.CustomUnderTextFieldText

@Composable
internal fun LoveStateMainView(
    viewModel: ProfileEditViewModel,
    dataChangedFinished: () -> Unit,
    paddingValues: PaddingValues,
    snackBarHost: SnackbarHostState,
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(uiState.error, uiState.success) {
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
            }
            UiError.None -> Unit
        }

        when (uiState.success) {
            UiSuccess.None -> Unit
            is UiSuccess.Success -> {
                viewModel.initSuccessError()
                snackBarHost.showSnackbar(
                    message = context.getString(R.string.msg_save_success),
                    duration = SnackbarDuration.Short
                )
            }
        }
    }

    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = paddingValues.calculateTopPadding() + 32.dp, bottom = 8.dp)
            .verticalScroll(scrollState)
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 17.dp, end = 16.dp)
        ) {
            Text(
                text = stringResource(R.string.txt_love_state),
                style = AppTextStyles.CAPTION_12_18_SEMI,
                modifier = Modifier.fillMaxWidth(),
                color = ColorStyle.GRAY_800
            )
            Spacer(modifier = Modifier.height(10.dp))
            ButtonLeftLarge(
                text = stringResource(R.string.txt_love_single),
                isSelected = uiState.loveState == LOVE.SINGLE.name,
                onClick = {
                    viewModel.changeLoveState(LOVE.SINGLE)
                }
            )

            Spacer(modifier = Modifier.height(10.dp))
            ButtonLeftLarge(
                text = stringResource(R.string.txt_love_ing),
                isSelected = uiState.loveState == LOVE.COUPLE.name,
                onClick = {
                    viewModel.changeLoveState(LOVE.COUPLE)
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
            ButtonLeftLarge(
                text = stringResource(R.string.txt_love_married),
                isSelected = uiState.loveState == LOVE.MARRIAGE.name,
                onClick = {
                    viewModel.changeLoveState(LOVE.MARRIAGE)
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
            ButtonLeftLarge(
                text = stringResource(R.string.txt_love_no_show),
                isSelected = uiState.loveState == LOVE.SECRET.name,
                onClick = {
                    viewModel.changeLoveState(LOVE.SECRET)
                }
            )
            Spacer(modifier = Modifier.height(24.dp))
            HorizontalDivider(
                thickness = 1.dp,
                color = ColorStyle.GRAY_200
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 17.dp, end = 16.dp, top = 24.dp)
            ) {
                CustomUnderTextFieldText(
                    text = stringResource(R.string.txt_love_matching),
                    color = Color(0xFF171717)
                )
                Spacer(modifier = Modifier.height(10.dp))
                ButtonLeftLarge(
                    text = stringResource(R.string.txt_love_same),
                    isSelected = MEETING.fromDisplayName(uiState.meetSame) == MEETING.SAME,
                    onClick = {
                        viewModel.changeMeetState(MEETING.SAME)
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
                ButtonLeftLarge(
                    text = stringResource(R.string.txt_love_never_mind),
                    isSelected = MEETING.fromDisplayName(uiState.meetSame) == MEETING.OKAY,
                    onClick = {
                        viewModel.changeMeetState(MEETING.OKAY)
                    }
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                HorizontalDivider(thickness = 1.dp, color = Color(0xFFEFEFEF))
                Spacer(modifier = Modifier.height(8.dp))
                CustomButton(
                    stringResource(R.string.btn_finish),
                    onclick = { viewModel.sendLoveState() },
                    enable = uiState.buttonRun
                )
            }
        }
    }
}