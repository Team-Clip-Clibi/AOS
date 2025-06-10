package com.example.signup.ui.phone

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.core.AppTextStyles
import com.example.core.ColorStyle
import com.example.signup.R
import com.example.signup.SignUpViewModel
import com.example.signup.ui.component.CustomTextField

@Composable
internal fun PhoneNumberView(
    viewModel: SignUpViewModel,
    ) {
    val phoneNumber by viewModel.userInfoState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = ColorStyle.WHITE_100)
            .padding(top = 32.dp, start = 17.dp, end = 16.dp)
    ) {
        Text(
            text = stringResource(R.string.txt_phone_title),
            style = AppTextStyles.SUBTITLE_16_24_SEMI,
            color = ColorStyle.GRAY_600,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = stringResource(R.string.txt_phone_title_sub),
            style = AppTextStyles.HEAD_28_40_BOLD,
            color = ColorStyle.GRAY_800,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(32.dp))
        CustomTextField(
            text = phoneNumber.phoneNumber,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = {number -> viewModel.inputPhoneNumber(number)},
            inputType = KeyboardType.Phone,
            hint = stringResource(R.string.hint_input_phone_number)
        )
    }
//
//    var buttonIsEnable = false
//    val context = LocalContext.current
//    LaunchedEffect(Unit) {
//        viewModel.userInfoState.collectLatest { userInfo ->
//            when (val phoneState = userInfo.phoneNumberCheckState) {
//                is SignUpViewModel.CheckState.StanBy -> {
//                    Log.d(javaClass.name.toString(), "stand by check phone Number")
//                }
//
//                is SignUpViewModel.CheckState.ValueOkay -> {
//                    Log.d(javaClass.name.toString(), "Success to set Phone Number")
//                    viewModel.resetPhoneNumberState()
//                    buttonClick()
//                }
//
//                is SignUpViewModel.CheckState.ValueNotOkay -> {
//                    when (phoneState.errorMessage) {
//                        ERROR_ALREADY_SIGN_UP -> {
//                            signUpPage()
//                        }
//
//                        RE_LOGIN -> {
//                            snackBarHostState.showSnackbar(
//                                message = context.getString(R.string.msg_reLogin),
//                                duration = SnackbarDuration.Short
//                            )
//                            goLoginPage()
//                        }
//
//                        ERROR_NETWORK_ERROR -> {
//                            snackBarHostState.showSnackbar(
//                                message = context.getString(R.string.msg_network_error),
//                                duration = SnackbarDuration.Short
//                            )
//                            viewModel.resetPhoneNumberState()
//                            goLoginPage()
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(
//                top = paddingValues.calculateTopPadding() + 32.dp,
//                bottom = 8.dp
//            )
//            .background(color = Color(0xFFFFFFFF))
//            .navigationBarsPadding()
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .verticalScroll(rememberScrollState())
//        ) {
//            Text(
//                text = stringResource(R.string.txt_sign_up_title),
//                style = AppTextStyles.SUBTITLE_16_24_SEMI,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(start = 17.dp, end = 17.dp),
//                color = Color(0xFF666666)
//            )
//
//            Spacer(modifier = Modifier.height(4.dp))
//
//            Text(
//                text = stringResource(R.string.txt_phone_title_sub),
//                style = AppTextStyles.HEAD_28_40_BOLD,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(start = 17.dp, end = 17.dp),
//                color = Color(0xFF171717)
//            )
//
//            Spacer(modifier = Modifier.height(32.dp))
//
//            CustomTextField(
//                text = phoneNumber.phoneNumber,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(start = 17.dp, end = 17.dp),
//                onValueChange = { number -> viewModel.inputPhoneNumber(number) },
//                inputType = KeyboardType.Phone,
//                hint = stringResource(R.string.hint_input_phone_number)
//            )
//
//            Spacer(modifier = Modifier.weight(1f))
//
//        }
//
//        Column(
//            modifier = Modifier
//                .align(Alignment.BottomCenter)
//                .fillMaxWidth()
//        ) {
//            HorizontalDivider(
//                thickness = 1.dp,
//                color = Color(0xFFEFEFEF)
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//            CustomButton(
//                text = stringResource(R.string.btn_next),
//                onclick = { viewModel.checkSignUpNumber() },
//                enable = phoneNumber.phoneNumber.isNotEmpty(),
//            )
//        }
//    }
}
