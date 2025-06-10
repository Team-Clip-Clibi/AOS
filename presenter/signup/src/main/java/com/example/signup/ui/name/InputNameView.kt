package com.example.signup.ui.name

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
internal fun InputNameView(
    viewModel: SignUpViewModel,
) {
    val name by viewModel.userInfoState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = ColorStyle.WHITE_100)
            .padding(top = 32.dp, start = 17.dp, end = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = stringResource(R.string.txt_name_title),
            style = AppTextStyles.SUBTITLE_16_24_SEMI,
            color = ColorStyle.GRAY_600,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = stringResource(R.string.txt_name_sub_title),
            style = AppTextStyles.HEAD_28_40_BOLD,
            color = ColorStyle.GRAY_800,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(32.dp))
        CustomTextField(
            text = name.name,
            modifier = Modifier.fillMaxWidth().height(60.dp),
            onValueChange = { input ->
                viewModel.inputName(input)
            },
            inputType = KeyboardType.Password,
            hint = stringResource(R.string.hint_name)
        )
    }
//    val context = LocalContext.current
//    LaunchedEffect(Unit) {
//        viewModel.userInfoState.collectLatest { userInfoState ->
//            when (userInfoState.nameCheck) {
//                is SignUpViewModel.CheckState.StanBy -> {
//                    isValidName = R.string.txt_name_safe
//                }
//
//                is SignUpViewModel.CheckState.ValueNotOkay -> {
//                    when ((userInfoState.nameCheck as SignUpViewModel.CheckState.ValueNotOkay).errorMessage) {
//                        ERROR_NETWORK_ERROR -> {
//                            snackBarHostState.showSnackbar(
//                                message = context.getString(R.string.msg_network_error),
//                                duration = SnackbarDuration.Short
//                            )
//                        }
//
//                        RE_LOGIN -> {
//                            snackBarHostState.showSnackbar(
//                                message = context.getString(R.string.msg_reLogin),
//                                duration = SnackbarDuration.Short
//                            )
//                            viewModel.resetName()
//                            reLogin()
//                        }
//
//                        else -> isValidName = R.string.txt_name_korean_english
//                    }
//
//                }
//
//                is SignUpViewModel.CheckState.ValueOkay -> {
//                    buttonClick()
//                    viewModel.resetName()
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
//            CustomTitleText(
//                stringResource(R.string.txt_name_title),
//                Modifier
//                    .fillMaxWidth()
//                    .padding(start = 17.dp, end = 16.dp)
//            )
//
//            Spacer(modifier = Modifier.height(4.dp))
//
//            CustomContentText(
//                stringResource(R.string.txt_name_content),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(start = 17.dp, end = 16.dp)
//            )
//
//            Spacer(modifier = Modifier.height(32.dp))
//
//            CustomTextField(
//                text = name.name,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(60.dp)
//                    .padding(start = 17.dp, end = 16.dp),
//                onValueChange = { input ->
//                    viewModel.inputName(input)
//                },
//                inputType = KeyboardType.Password,
//                hint = stringResource(R.string.hint_name)
//            )
//
//            Spacer(modifier = Modifier.height(10.dp))
//
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(start = 17.dp, end = 16.dp),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    CustomUnderTextFieldText(
//                        text = stringResource(isValidName),
//                        color = if (isValidName == R.string.txt_name_safe)
//                            Color(0xFF666666)
//                        else
//                            Color(0xFFFB4F4F)
//                    )
//                }
//            }
//
//            Spacer(modifier = Modifier.weight(1f))
//        }
//
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
//                stringResource(R.string.btn_next),
//                onclick = { viewModel.checkName(name.name) },
//                enable = name.name.isNotEmpty()
//            )
//        }
//    }
}