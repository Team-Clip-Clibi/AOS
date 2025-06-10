package com.example.signup.ui.nickname

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.core.AppTextStyles
import com.example.core.ButtonSmall
import com.example.core.ColorStyle
import com.example.signup.NAME_LONG
import com.example.signup.NAME_SHORT
import com.example.signup.NAME_SPECIAL
import com.example.signup.NICKNAME_ALREADY_USE
import com.example.signup.R
import com.example.signup.SignUpViewModel
import com.example.signup.ui.component.CustomTextField

@Composable
internal fun NickNameView(
    viewModel: SignUpViewModel,
) {
    var nicknameValidationMessage by remember { mutableIntStateOf(R.string.txt_nick_under_text) }
    val nickName by viewModel.userInfoState.collectAsState()
    var nickNameCheckButton by remember { mutableStateOf(true) }
    LaunchedEffect(nickName.nickCheckStanBy) {
        when(val message = nickName.nickCheckStanBy){
            is SignUpViewModel.CheckState.ValueNotOkay -> {
                nicknameValidationMessage =  when(message.errorMessage){
                    NAME_LONG ->{
                        R.string.txt_nick_length_over
                    }
                    NAME_SHORT -> {
                        R.string.txt_nick_length_low
                    }
                    NAME_SPECIAL -> {
                        R.string.txt_nick_no_special
                    }
                    NICKNAME_ALREADY_USE -> {
                        R.string.txt_nick_name_already_use
                    }
                    else -> throw IllegalArgumentException("UN DEFINE ERROR")
                }
            }
            is SignUpViewModel.CheckState.ValueOkay -> {
                nickNameCheckButton = false
            }
            else -> Unit
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = ColorStyle.WHITE_100)
            .padding(top = 32.dp, start = 17.dp, end = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = stringResource(R.string.txt_nick_title),
            style = AppTextStyles.SUBTITLE_16_24_SEMI,
            color = ColorStyle.GRAY_600,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = stringResource(R.string.txt_nick_sub_title),
            style = AppTextStyles.HEAD_28_40_BOLD,
            color = ColorStyle.GRAY_800,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(32.dp))
        CustomTextField(
            text = nickName.nickName,
            modifier = Modifier.fillMaxWidth().height(60.dp),
            onValueChange = { input -> viewModel.inputNickName(input)},
            inputType = KeyboardType.Password,
            hint = stringResource(R.string.hint_nick)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween){
            Text(
                text = stringResource(nicknameValidationMessage),
                style = AppTextStyles.CAPTION_12_18_SEMI,
                color = when(nicknameValidationMessage){
                    R.string.txt_nick_under_text -> ColorStyle.GRAY_600
                    R.string.txt_nick_name_okay -> ColorStyle.GRAY_800
                    else -> ColorStyle.RED_100
                }
            )
            ButtonSmall(
                text = stringResource(R.string.btn_check_nick_name),
                isEnable = nickName.name.trim().isNotEmpty() && nickNameCheckButton,
                onClick = viewModel::checkNickName
            )
        }
    }
//    var isCheck by remember { mutableStateOf(false) }
//    var buttonOkay by remember { mutableStateOf(false) }
//    val context = LocalContext.current
//    LaunchedEffect(Unit) {
//        viewModel.userInfoState.collectLatest { userInfo ->
//            when (val nickNameState = userInfo.nickCheckStanBy) {
//                is SignUpViewModel.CheckState.StanBy -> {
//                    Log.d(javaClass.name.toString(), "Stand by check NickName")
//                    nicknameValidationMessage = R.string.txt_nick_length
//                }
//
//                is SignUpViewModel.CheckState.ValueOkay -> {
//                    Log.d(javaClass.name.toString(), "Success to set NickName")
//                    buttonOkay = true
//                    isCheck = true
//                    viewModel.resetNickName()
//                }
//
//                is SignUpViewModel.CheckState.ValueNotOkay -> {
//                    buttonOkay = false
//                    isCheck = false
//                    if(nickNameState.errorMessage == RE_LOGIN){
//                        snackBarHostState.showSnackbar(
//                            message = context.getString(R.string.msg_reLogin),
//                            duration = SnackbarDuration.Short
//                        )
//                        goLoginPage()
//                        viewModel.resetNickName()
//                        return@collectLatest
//                    }
//
//                    nicknameValidationMessage = when (nickNameState.errorMessage) {
//                        NAME_LONG -> {
//                            R.string.txt_nick_length_over
//                        }
//
//                        NAME_SHORT -> {
//                            R.string.txt_nick_length_low
//                        }
//
//                        NAME_SPECIAL -> {
//                            R.string.txt_nick_no_special
//                        }
//
//                        NICKNAME_ALREADY_USE -> {
//                            R.string.txt_nick_name_already_use
//                        }
//                        else -> throw IllegalArgumentException("UNKNOW ERROR")
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
//            CustomTitleText(
//                stringResource(R.string.txt_nick_title),
//                Modifier
//                    .fillMaxWidth()
//                    .padding(start = 17.dp, end = 16.dp)
//            )
//            Spacer(modifier = Modifier.height(4.dp))
//            CustomContentText(
//                stringResource(R.string.txt_nick_content),
//                Modifier
//                    .fillMaxWidth()
//                    .padding(start = 17.dp, end = 16.dp)
//            )
//
//            Spacer(modifier = Modifier.height(32.dp))
//
//            CustomTextField(
//                text = nickName.nickName,
//                modifier = Modifier.fillMaxWidth()        .padding(start = 17.dp, end = 16.dp),
//                onValueChange = { value -> viewModel.inputNickName(value) },
//                inputType = KeyboardType.Password,
//                hint = stringResource(R.string.hint_nick)
//            )
//
//            Spacer(modifier = Modifier.height(10.dp))
//
//            Row(
//                modifier = Modifier.fillMaxWidth()        .padding(start = 17.dp, end = 16.dp),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    CustomUnderTextFieldText(
//                        text = stringResource(nicknameValidationMessage),
//                        color = if (nicknameValidationMessage == R.string.txt_nick_length)
//                            Color(0xFF171717)
//                        else
//                            Color(0xFFFB4F4F)
//                    )
//                }
//
//                CustomSmallButton(
//                    stringResource(R.string.btn_check_nick_name),
//                    onclick = { viewModel.checkNickName(nickName.nickName) },
//                    enable = nickName.nickName.isNotEmpty() && !isCheck
//                )
//
//
//            }
//
//        }
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
//                onclick = { buttonClick() },
//                enable = buttonOkay
//            )
//        }
//    }
}