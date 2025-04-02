package com.example.signup.ui.phone

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material3.Button
import androidx.wear.compose.material3.ButtonDefaults
import com.example.core.AppTextStyles
import com.example.signup.ERROR_ALREADY_SIGN_UP
import com.example.signup.R
import com.example.signup.SignUpViewModel
import com.example.signup.ui.component.CustomButton
import com.example.signup.ui.component.CustomDialog
import com.example.signup.ui.component.CustomTextField
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun PhoneNumberScreenMain(
    paddingValues: PaddingValues,
    viewModel: SignUpViewModel,
    buttonClick: () -> Unit,
    activity: Activity,
    signUpPage: () -> Unit,
) {
    val phoneNumber by viewModel.userInfoState.collectAsState()
    var alreadySignUpDialog by remember { mutableStateOf(false) }

    var buttonIsEnable = false

    LaunchedEffect(Unit) {
        viewModel.userInfoState.collectLatest { userInfo ->
            when (val phoneState = userInfo.phoneNumberCheckState) {
                is SignUpViewModel.CheckState.StanBy -> {
                    Log.d(javaClass.name.toString(), "stand by check phone Number")
                }

                is SignUpViewModel.CheckState.ValueOkay -> {
                    Log.d(javaClass.name.toString(), "Success to set Phone Number")
                    viewModel.resetPhoneNumberState()
                    buttonClick()
                }

                is SignUpViewModel.CheckState.ValueNotOkay -> {
                    when (phoneState.errorMessage) {
                        ERROR_ALREADY_SIGN_UP -> {
                            alreadySignUpDialog = true
                        }
                    }
                }
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = paddingValues.calculateTopPadding() + 32.dp,
                bottom = 21.dp
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = stringResource(R.string.txt_sign_up_title),
                style = AppTextStyles.SUBTITLE_16_24_SEMI,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 17.dp, end = 17.dp),
                color = Color(0xFF666666)
            )

            Text(
                text = stringResource(R.string.txt_phone_title_sub),
                style = AppTextStyles.HEAD_28_40_BOLD,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 17.dp, end = 17.dp),
                color = Color(0xFF171717)
            )

            Spacer(modifier = Modifier.height(32.dp))

            CustomTextField(
                text = phoneNumber.phoneNumber,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 17.dp, end = 17.dp),
                onValueChange = { number -> viewModel.inputPhoneNumber(number) },
                inputType = KeyboardType.Phone,
                hint = stringResource(R.string.hint_input_phone_number)
            )

            Spacer(modifier = Modifier.weight(1f))

            if (alreadySignUpDialog) {
                CustomDialog(
                    onDismiss = { alreadySignUpDialog = false },
                    buttonClick = {
                        alreadySignUpDialog = false
                        signUpPage()
                    },
                    titleText = stringResource(R.string.dialog_title_already_signup),
                    contentText = stringResource(R.string.dialog_content_already_signup),
                    buttonText = stringResource(R.string.dialog_btn_already_signup)
                )
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            HorizontalDivider(
                thickness = 1.dp,
                color = Color(0xFFEFEFEF)
            )
            Spacer(modifier = Modifier.height(8.dp))
            CustomButton(
                text = stringResource(R.string.btn_next),
                onclick = { viewModel.checkSignUpNumber() },
                enable = phoneNumber.phoneNumber.isNotEmpty(),
            )
        }
    }
}
