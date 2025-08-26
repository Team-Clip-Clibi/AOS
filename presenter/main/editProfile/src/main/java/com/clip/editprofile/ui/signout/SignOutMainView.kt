package com.clip.editprofile.ui.signout

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
import com.clip.editprofile.ProfileEditViewModel
import com.clip.editprofile.R
import com.clip.editprofile.SignOutData
import com.clip.editprofile.UiError
import com.clip.editprofile.UiSuccess
import com.clip.editprofile.ui.CustomDialog


@Composable
internal fun SignOutMainView(
    viewModel: ProfileEditViewModel,
    onBackClick: () -> Unit,
    goToLogin: () -> Unit,
    paddingValues: PaddingValues,
    snackBarHost: SnackbarHostState,
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(uiState.success, uiState.error) {
        when (uiState.error) {
            is UiError.Error -> {
                snackBarHost.showSnackbar(
                    message = context.getString(R.string.txt_delete_fail),
                    duration = SnackbarDuration.Short
                )
            }

            UiError.None -> Unit
        }
        when (uiState.success) {
            UiSuccess.None -> Unit
            is UiSuccess.Success -> {
                goToLogin()
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
    ) {
        Text(
            text = stringResource(R.string.txt_sign_out_title),
            style = AppTextStyles.TITLE_20_28_SEMI,
            color = ColorStyle.GRAY_800
        )
        Spacer(modifier = Modifier.height(24.dp))
        ButtonL(
            text = stringResource(R.string.txt_sign_out_content_service),
            onClick = {
                viewModel.changeSignOut(SignOutData.SERVICE)
            },
            isSelected = uiState.signOut == SignOutData.SERVICE.name,
            borderUse = uiState.signOut == SignOutData.SERVICE.name,
            borderColor = ColorStyle.PURPLE_200,
            buttonColor = if (uiState.signOut == SignOutData.SERVICE.name) ColorStyle.PURPLE_100 else ColorStyle.GRAY_100,
            textCenter = false
        )
        Spacer(modifier = Modifier.height(10.dp))
        ButtonL(
            text = stringResource(R.string.txt_sign_out_content_apply),
            onClick = {
                viewModel.changeSignOut(SignOutData.APPLY)
            },
            isSelected = uiState.signOut == SignOutData.APPLY.name,
            borderUse = uiState.signOut == SignOutData.APPLY.name,
            borderColor = ColorStyle.PURPLE_200,
            buttonColor = if (uiState.signOut == SignOutData.APPLY.name) ColorStyle.PURPLE_100 else ColorStyle.GRAY_100,
            textCenter = false
        )
        Spacer(modifier = Modifier.height(10.dp))
        ButtonL(
            text = stringResource(R.string.txt_sign_out_content_not_good),
            onClick = {
                viewModel.changeSignOut(SignOutData.NOT_GOOD)
            },
            isSelected = uiState.signOut == SignOutData.NOT_GOOD.name,
            borderUse = uiState.signOut == SignOutData.NOT_GOOD.name,
            borderColor = ColorStyle.PURPLE_200,
            buttonColor = if (uiState.signOut == SignOutData.NOT_GOOD.name) ColorStyle.PURPLE_100 else ColorStyle.GRAY_100,
            textCenter = false
        )
        Spacer(modifier = Modifier.height(10.dp))
        ButtonL(
            text = stringResource(R.string.txt_sign_out_content_not_need),
            onClick = {
                viewModel.changeSignOut(SignOutData.NOT_NEED)
            },
            isSelected = uiState.signOut == SignOutData.NOT_NEED.name,
            borderUse = uiState.signOut == SignOutData.NOT_NEED.name,
            borderColor = ColorStyle.PURPLE_200,
            buttonColor = if (uiState.signOut == SignOutData.NOT_NEED.name) ColorStyle.PURPLE_100 else ColorStyle.GRAY_100,
            textCenter = false
        )
        Spacer(modifier = Modifier.height(10.dp))
        ButtonL(
            text = stringResource(R.string.txt_sign_out_content_etc),
            onClick = {
                viewModel.changeSignOut(SignOutData.ETC)
            },
            isSelected = uiState.signOut == SignOutData.ETC.name,
            borderUse = uiState.signOut == SignOutData.ETC.name,
            borderColor = ColorStyle.PURPLE_200,
            buttonColor = if (uiState.signOut == SignOutData.ETC.name) ColorStyle.PURPLE_100 else ColorStyle.GRAY_100,
            textCenter = false
        )
        Spacer(modifier = Modifier.height(10.dp))
        HorizontalDivider(thickness = 1.dp, color = ColorStyle.GRAY_200)
        Spacer(modifier = Modifier.height(10.dp))
        if (uiState.signOut == SignOutData.ETC.name) {
            TextFieldComponent(
                value = uiState.signOutContent,
                onValueChange = { text ->
                    viewModel.changeSignOutContent(text)
                },
                maxLine = 1,
                maxLength = 100,
                hint = stringResource(R.string.txt_sign_out_content_etc_hint)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${uiState.signOutContent}/100",
                style = AppTextStyles.CAPTION_10_14_MEDIUM,
                color = ColorStyle.GRAY_700,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )
        }
        if (uiState.isDialogShow) {
            CustomDialog(
                onDismiss = {
                    viewModel.isDialogShow(false)
                    onBackClick()
                },
                buttonClick = { viewModel.signOut() },
                titleText = stringResource(R.string.dialog_sign_out_title),
                contentText = stringResource(R.string.dialog_sign_out_content),
                buttonText = stringResource(R.string.dialog_okay),
                subButtonText = stringResource(R.string.dialog_cancel)
            )
        }
    }
}

