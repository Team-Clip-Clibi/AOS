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
import com.example.signup.ERROR_ALREADY_SIGN_UP
import com.example.signup.R
import com.example.signup.SignUpViewModel
import com.example.signup.ui.component.CustomDialog
import com.example.signup.ui.component.CustomTextField
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun PhoneNumberScreenMain(
    paddingValues: PaddingValues,
    viewModel: SignUpViewModel,
    buttonClick: () -> Unit,
    activity: Activity,
) {
    val phoneNumber by viewModel.phoneNumber.collectAsState()
    val smsCode by viewModel.smsCode.collectAsState()
    val smsViewShow by viewModel.smsViewShow.collectAsState()
    val firebaseSMSState by viewModel.firebaseSMSState.collectAsState()
    val smsTimer by viewModel.smsTime.collectAsState()

    var errorDialog by remember { mutableStateOf(false) }
    var alreadySignUpDialog by remember { mutableStateOf(false) }
    val buttonIsEnable = when (firebaseSMSState) {
        is SignUpViewModel.Action.VerifyFinish -> true
        else -> false
    }

    LaunchedEffect(Unit) {
        viewModel.firebaseSMSState.collectLatest { state ->
            when (state) {
                is SignUpViewModel.Action.Error -> {
                    when (state.message) {
                        ERROR_ALREADY_SIGN_UP -> {
                            alreadySignUpDialog = true
                        }

                        else -> errorDialog = true
                    }
                }

                is SignUpViewModel.Action.FailVerifySMS -> {
                    errorDialog = true
                }

                else -> {
                    Log.d(javaClass.name.toString(), "state is change $state")
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 17.dp,
                end = 17.dp,
                top = paddingValues.calculateTopPadding() + 32.dp,
                bottom = 21.dp
            )
    ) {
        // ✅ 스크롤 가능한 입력 영역
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 70.dp) // 버튼 높이만큼 패딩 추가
        ) {
            Text(
                text = stringResource(R.string.txt_sign_up_title),
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.medium)),
                fontWeight = FontWeight(600),
                modifier = Modifier.fillMaxWidth(),
                color = colorResource(R.color.dark_gray)
            )
            Text(
                text = stringResource(R.string.txt_phone_title_sub),
                fontSize = 28.sp,
                lineHeight = 40.sp,
                fontFamily = FontFamily(Font(R.font.medium)),
                fontWeight = FontWeight(700),
                modifier = Modifier.fillMaxWidth(),
                color = colorResource(R.color.black_gray)
            )
            Spacer(modifier = Modifier.height(32.dp))

            CustomTextField(
                text = phoneNumber,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { number ->
                    viewModel.inputPhoneNumber(number)
                },
                inputType = KeyboardType.Phone,
                hint = stringResource(R.string.hint_input_phone_number)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = { viewModel.smsRequest(phoneNumber, activity) },
                enabled = phoneNumber.isNotEmpty(),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .width(120.dp)
                    .height(36.dp)
                    .align(Alignment.End)
            ) {
                Text(
                    text = stringResource(R.string.btn_send_message),
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    fontFamily = FontFamily(Font(R.font.medium)),
                    fontWeight = FontWeight(500),
                    color = colorResource(R.color.purple),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (smsViewShow) {
                CustomTextField(
                    text = smsCode,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = { number ->
                        viewModel.signCodeNumber(number)
                    },
                    inputType = KeyboardType.Number,
                    hint = stringResource(R.string.hint_sms_num),
                    timeCount = smsTimer
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.txt_sms_not_received),
                            fontSize = 12.sp,
                            lineHeight = 18.sp,
                            fontFamily = FontFamily(Font(R.font.medium)),
                            fontWeight = FontWeight(400),
                            color = colorResource(R.color.dark_gray)
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = stringResource(R.string.txt_resend_sms),
                            fontSize = 12.sp,
                            lineHeight = 18.sp,
                            fontFamily = FontFamily(Font(R.font.medium)),
                            fontWeight = FontWeight(600),
                            color = Color.Red,
                            modifier = Modifier.clickable {
                                viewModel.smsRequest(phoneNumber, activity)
                            }
                        )
                    }

                    Button(
                        onClick = { viewModel.sendCode(smsCode) },
                        enabled = smsCode.isNotEmpty(),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .width(90.dp)
                            .height(36.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.btn_check_sms_num),
                            fontSize = 14.sp,
                            lineHeight = 20.sp,
                            fontFamily = FontFamily(Font(R.font.medium)),
                            fontWeight = FontWeight(500),
                            color = colorResource(R.color.purple),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }


        Button(
            onClick = { buttonClick() },
            enabled = buttonIsEnable,
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(if(buttonIsEnable){
                    R.color.purple
                }else{
                    R.color.bar_light_gray
                }),
            ),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(
                text = stringResource(R.string.btn_next),
                fontSize = 20.sp,
                lineHeight = 28.sp,
                fontFamily = FontFamily(Font(R.font.medium)),
                fontWeight = FontWeight(600),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                color = colorResource(R.color.white)
            )
        }

        // 다이얼로그 처리
        if (errorDialog) {
            CustomDialog(
                onDismiss = { errorDialog = false },
                buttonClick = {
                    errorDialog = false
                    viewModel.smsRequest(phoneNumber, activity)
                },
                titleText = stringResource(R.string.dialog_title_again_input),
                contentText = stringResource(R.string.dialog_content_again_input),
                buttonText = stringResource(R.string.dialog_btn_again_input)
            )
        }

        if (alreadySignUpDialog) {
            CustomDialog(
                onDismiss = { alreadySignUpDialog = false },
                buttonClick = { alreadySignUpDialog = false },
                titleText = stringResource(R.string.dialog_title_already_signup),
                contentText = stringResource(R.string.dialog_content_already_signup),
                buttonText = stringResource(R.string.dialog_btn_already_signup)
            )
        }
    }
}
