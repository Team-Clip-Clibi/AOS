package com.clip.editprofile.ui.loveState

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.unit.dp
import com.clip.core.AppTextStyles
import com.clip.core.ButtonL
import com.clip.core.ColorStyle
import com.clip.editprofile.ERROR_FAIL_SAVE
import com.clip.editprofile.ERROR_FAIL_TO_UPDATE_LOVE
import com.clip.editprofile.ERROR_TOKEN_NULL
import com.clip.editprofile.LOVE
import com.clip.editprofile.MEETING
import com.clip.editprofile.ProfileEditViewModel
import com.clip.editprofile.R
import com.clip.editprofile.UiError
import com.clip.editprofile.UiSuccess

@Composable
internal fun LoveStateMainView(
    viewModel: ProfileEditViewModel,
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
            .background(color = ColorStyle.WHITE_100)
            .padding(
                top = paddingValues.calculateTopPadding() + 32.dp,
                bottom = paddingValues.calculateBottomPadding(),
                start = 17.dp,
                end = 16.dp
            )
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = stringResource(R.string.txt_love_state),
            style = AppTextStyles.CAPTION_12_18_SEMI,
            color = ColorStyle.GRAY_800
        )
        Spacer(modifier = Modifier.height(10.dp))
        ButtonL(
            text = stringResource(R.string.txt_love_single),
            onClick = {
                viewModel.changeLoveState(LOVE.SINGLE)
            },
            isSelected = uiState.loveState == LOVE.SINGLE.name,
            borderUse = uiState.loveState == LOVE.SINGLE.name,
            borderColor = ColorStyle.PURPLE_200,
            buttonColor = if (uiState.loveState == LOVE.SINGLE.name) ColorStyle.PURPLE_100 else ColorStyle.GRAY_100,
            textCenter = false
        )
        Spacer(modifier = Modifier.height(10.dp))
        ButtonL(
            text = stringResource(R.string.txt_love_ing),
            onClick = {
                viewModel.changeLoveState(LOVE.COUPLE)
            },
            isSelected = uiState.loveState == LOVE.COUPLE.name,
            borderUse = uiState.loveState == LOVE.COUPLE.name,
            borderColor = ColorStyle.PURPLE_200,
            buttonColor = if (uiState.loveState == LOVE.COUPLE.name) ColorStyle.PURPLE_100 else ColorStyle.GRAY_100,
            textCenter = false
        )
        Spacer(modifier = Modifier.height(10.dp))
        ButtonL(
            text = stringResource(R.string.txt_love_married),
            onClick = {
                viewModel.changeLoveState(LOVE.MARRIAGE)
            },
            isSelected = uiState.loveState == LOVE.MARRIAGE.name,
            borderUse = uiState.loveState == LOVE.MARRIAGE.name,
            borderColor = ColorStyle.PURPLE_200,
            buttonColor = if (uiState.loveState == LOVE.MARRIAGE.name) ColorStyle.PURPLE_100 else ColorStyle.GRAY_100,
            textCenter = false
        )
        Spacer(modifier = Modifier.height(10.dp))
        ButtonL(
            text = stringResource(R.string.txt_love_no_show),
            onClick = {
                viewModel.changeLoveState(LOVE.SECRET)
            },
            isSelected = uiState.loveState == LOVE.SECRET.name,
            borderUse = uiState.loveState == LOVE.SECRET.name,
            borderColor = ColorStyle.PURPLE_200,
            buttonColor = if (uiState.loveState == LOVE.SECRET.name) ColorStyle.PURPLE_100 else ColorStyle.GRAY_100,
            textCenter = false
        )
        Spacer(modifier = Modifier.height(24.dp))
        HorizontalDivider(
            thickness = 1.dp,
            color = ColorStyle.GRAY_200
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = stringResource(R.string.txt_love_matching),
            style = AppTextStyles.CAPTION_12_18_SEMI,
            color = ColorStyle.GRAY_800
        )
        Spacer(modifier = Modifier.height(10.dp))
        ButtonL(
            text = stringResource(R.string.txt_love_same),
            onClick = {
                viewModel.changeMeetState(MEETING.SAME)
            },
            isSelected = MEETING.fromDisplayName(uiState.meetSame) == MEETING.SAME,
            borderUse = MEETING.fromDisplayName(uiState.meetSame) == MEETING.SAME,
            borderColor = ColorStyle.PURPLE_200,
            buttonColor = if (MEETING.fromDisplayName(uiState.meetSame) == MEETING.SAME) ColorStyle.PURPLE_100 else ColorStyle.GRAY_100,
            textCenter = false
        )
        Spacer(modifier = Modifier.height(10.dp))
        ButtonL(
            text = stringResource(R.string.txt_love_never_mind),
            onClick = {
                viewModel.changeMeetState(MEETING.OKAY)
            },
            isSelected = MEETING.fromDisplayName(uiState.meetSame) == MEETING.OKAY,
            borderUse = MEETING.fromDisplayName(uiState.meetSame) == MEETING.OKAY,
            borderColor = ColorStyle.PURPLE_200,
            buttonColor = if (MEETING.fromDisplayName(uiState.meetSame) == MEETING.OKAY) ColorStyle.PURPLE_100 else ColorStyle.GRAY_100,
            textCenter = false
        )
    }
}