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
import com.example.signup.NAME_OKAY
import com.example.signup.NAME_SHORT
import com.example.signup.NAME_SPECIAL
import com.example.signup.NICKNAME_ALREADY_USE
import com.example.signup.R
import com.example.signup.SignUpStep
import com.example.signup.SignUpStepState
import com.example.signup.SignUpViewModel
import com.example.signup.ui.component.CustomTextField

@Composable
internal fun NickNameView(
    viewModel: SignUpViewModel,
) {
    var nicknameValidationMessage by remember { mutableIntStateOf(R.string.txt_nick_under_text) }
    val nickName by viewModel.userInfoState.collectAsState()
    var nickNameCheckButton by remember { mutableStateOf(true) }

    LaunchedEffect(nickName.stepStates[SignUpStep.NICKNAME]) {
        when (val state = nickName.stepStates[SignUpStep.NICKNAME]) {
            is SignUpStepState.Success -> {
                if (state.message == NAME_OKAY) {
                    nicknameValidationMessage = R.string.txt_nick_name_okay
                    nickNameCheckButton = false
                } else {
                    nicknameValidationMessage = R.string.txt_nick_under_text
                    nickNameCheckButton = true
                }
            }

            is SignUpStepState.Error -> {
                nicknameValidationMessage =
                    when ((nickName.stepStates[SignUpStep.NICKNAME] as? SignUpStepState.Error)?.message) {
                        NAME_LONG -> {
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

                        else -> R.string.txt_nick_length_over
                    }
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
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            onValueChange = { input -> viewModel.inputNickName(input) },
            inputType = KeyboardType.Password,
            hint = stringResource(R.string.hint_nick)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(nicknameValidationMessage),
                style = AppTextStyles.CAPTION_12_18_SEMI,
                color = when (nicknameValidationMessage) {
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
}