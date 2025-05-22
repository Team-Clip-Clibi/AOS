package com.sungil.editprofile.ui.signout


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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.core.AppTextStyles
import com.example.core.ButtonLeftLarge
import com.example.core.ButtonXXLPurple400
import com.example.core.ColorStyle
import com.sungil.editprofile.ERROR_DELETE_FAIL
import com.sungil.editprofile.ERROR_NETWORK
import com.sungil.editprofile.ProfileEditViewModel
import com.sungil.editprofile.R
import com.sungil.editprofile.SignOutData
import com.sungil.editprofile.UiError
import com.sungil.editprofile.UiSuccess
import com.sungil.editprofile.ui.CustomChangeDataTextField
import com.sungil.editprofile.ui.CustomDialog


@Composable
internal fun SignOutMainView(
    viewModel: ProfileEditViewModel,
    onBackClick: () -> Unit,
    goToLogin: () -> Unit,
    paddingValues: PaddingValues,
    snackBarHost : SnackbarHostState
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(uiState.success , uiState.error) {
        when(val error = uiState.error){
            is UiError.Error -> {
                snackBarHost.showSnackbar(
                    message = context.getString(R.string.txt_delete_fail),
                    duration = SnackbarDuration.Short
                )
            }
            UiError.None -> Unit
        }
        when(uiState.success){
            UiSuccess.None -> Unit
            is UiSuccess.Success -> {
                goToLogin()
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = paddingValues.calculateTopPadding() + 32.dp,
                bottom = 8.dp
            )
            .verticalScroll(rememberScrollState())
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(start = 17.dp, end = 16.dp)
        ) {
            Text(
                text = stringResource(R.string.txt_sign_out_title),
                style = AppTextStyles.TITLE_20_28_SEMI,
                color = ColorStyle.GRAY_800
            )
            Spacer(modifier = Modifier.height(24.dp))
            ButtonLeftLarge(
                text = stringResource(R.string.txt_sign_out_content_service),
                isSelected = uiState.signOut == SignOutData.SERVICE.name,
                onClick = {
                    viewModel.changeSignOut(SignOutData.SERVICE)
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
            ButtonLeftLarge(
                text = stringResource(R.string.txt_sign_out_content_apply),
                isSelected = uiState.signOut == SignOutData.APPLY.name,
                onClick = {
                    viewModel.changeSignOut(SignOutData.APPLY)
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
            ButtonLeftLarge(
                text = stringResource(R.string.txt_sign_out_content_not_good),
                isSelected = uiState.signOut == SignOutData.NOT_GOOD.name,
                onClick = {
                    viewModel.changeSignOut(SignOutData.NOT_GOOD)
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
            ButtonLeftLarge(
                text = stringResource(R.string.txt_sign_out_content_not_need),
                isSelected = uiState.signOut == SignOutData.NOT_NEED.name,
                onClick = {
                    viewModel.changeSignOut(SignOutData.NOT_NEED)
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
            ButtonLeftLarge(
                text = stringResource(R.string.txt_sign_out_content_etc),
                isSelected = uiState.signOut == SignOutData.ETC.name,
                onClick = {
                    viewModel.changeSignOut(SignOutData.ETC)
                }
            )
            Spacer(modifier = Modifier.height(10.dp))

            HorizontalDivider(thickness = 1.dp, color = Color(0xFFEFEFEF))

            Spacer(modifier = Modifier.height(10.dp))
            if (uiState.signOut == SignOutData.ETC.name) {
                CustomChangeDataTextField(
                    beforeText = uiState.signOutContent,
                    inputType = KeyboardType.Text,
                    onValueChange = { data ->
                       viewModel.changeSignOutContent(data)
                    }
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            HorizontalDivider(thickness = 1.dp, color = ColorStyle.GRAY_200)
            Spacer(modifier = Modifier.height(8.dp))
            ButtonXXLPurple400(
                onClick = { viewModel.isDialogShow(true) },
                buttonText = stringResource(R.string.btn_finish),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                isEnable = uiState.buttonRun
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
