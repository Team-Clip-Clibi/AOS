package com.sungil.editprofile.ui.signout

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.sungil.editprofile.ProfileEditViewModel
import com.sungil.editprofile.R
import com.sungil.editprofile.SignOutData
import com.sungil.editprofile.ui.CustomButton
import com.sungil.editprofile.ui.CustomChangeDataTextField
import com.sungil.editprofile.ui.CustomDialog
import com.sungil.editprofile.ui.CustomItemPick
import com.sungil.editprofile.ui.CustomTitle


@Composable
internal fun SignOutMainView(
    viewModel: ProfileEditViewModel,
    onBackClick: () -> Unit,
    goToLogin: () -> Unit,
    paddingValues: PaddingValues,
) {
    val signOutContent = viewModel.signOutContent.collectAsState()
    val button = viewModel.button.collectAsState()
    var inputValue by remember { mutableStateOf("") }
    val dialog = viewModel.showLogoutDialog.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.editProfileState.collect { state ->
            when (state) {
                is ProfileEditViewModel.EditProfileState.GoodBye -> {
                    goToLogin()
                }

                else -> Unit
            }
        }
    }
    val scrollState = rememberScrollState()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = paddingValues.calculateTopPadding() + 32.dp,
                bottom = 8.dp
            )
            .verticalScroll(scrollState)
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 17.dp, end = 16.dp)
        ) {
            CustomTitle(
                text = stringResource(R.string.txt_sign_out_title),
                color = Color(0xFF171717)
            )
            Spacer(modifier = Modifier.height(24.dp))
            CustomItemPick(
                modifier = signOutItemModifier(signOutContent.value == SignOutData.SERVICE),
                text = stringResource(R.string.txt_sign_out_content_service),
                clickable = { viewModel.changeSignOutContent(SignOutData.SERVICE) }
            )
            Spacer(modifier = Modifier.height(10.dp))
            CustomItemPick(
                modifier = signOutItemModifier(signOutContent.value == SignOutData.APPLY),
                text = stringResource(R.string.txt_sign_out_content_apply),
                clickable = { viewModel.changeSignOutContent(SignOutData.APPLY) }
            )
            Spacer(modifier = Modifier.height(10.dp))
            CustomItemPick(
                modifier = signOutItemModifier(signOutContent.value == SignOutData.NOT_GOOD),
                text = stringResource(R.string.txt_sign_out_content_not_good),
                clickable = { viewModel.changeSignOutContent(SignOutData.NOT_GOOD) }
            )
            Spacer(modifier = Modifier.height(10.dp))
            CustomItemPick(
                modifier = signOutItemModifier(signOutContent.value == SignOutData.NOT_NEED),
                text = stringResource(R.string.txt_sign_out_content_not_need),
                clickable = { viewModel.changeSignOutContent(SignOutData.NOT_NEED) }
            )
            Spacer(modifier = Modifier.height(10.dp))
            CustomItemPick(
                modifier = signOutItemModifier(signOutContent.value == SignOutData.ETC),
                text = stringResource(R.string.txt_sign_out_content_etc),
                clickable = { viewModel.changeSignOutContent(SignOutData.ETC) }
            )
            Spacer(modifier = Modifier.height(10.dp))

            HorizontalDivider(thickness = 1.dp, color = Color(0xFFEFEFEF))

            Spacer(modifier = Modifier.height(10.dp))

            if(signOutContent.value == SignOutData.ETC){
                CustomChangeDataTextField(
                    beforeText = inputValue,
                    inputType = KeyboardType.Text,
                    onValueChange = { data ->
                        inputValue = data
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
            HorizontalDivider(thickness = 1.dp, color = Color(0xFFEFEFEF))
            Spacer(modifier = Modifier.height(8.dp))
            CustomButton(
                stringResource(R.string.btn_finish),
                onclick = {
                    viewModel.setDialogTrue()
                },
                enable = button.value
            )
        }
        if (dialog.value) {
            CustomDialog(
                onDismiss = {
                    viewModel.disMissDialog()
                    onBackClick() },
                buttonClick = { viewModel.signOut() },
                titleText = stringResource(R.string.dialog_sign_out_title),
                contentText = stringResource(R.string.dialog_sign_out_content),
                buttonText = stringResource(R.string.dialog_okay),
                subButtonText = stringResource(R.string.dialog_cancel)
            )
        }
    }
}

@Composable
fun signOutItemModifier(isSelected: Boolean): Modifier {
    return Modifier
        .fillMaxWidth()
        .height(48.dp)
        .background(
            color = if (isSelected) Color(0xFFF9F0FF) else Color(0xFFF7F7F7),
            shape = RoundedCornerShape(8.dp)
        )
        .then(
            if (isSelected) {
                Modifier.border(
                    width = 1.dp,
                    color = Color(0xFFD3ADF7),
                    shape = RoundedCornerShape(12.dp)
                )
            } else Modifier
        )
}