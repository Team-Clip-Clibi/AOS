package com.example.signup.ui.nickname

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.signup.NAME_LONG
import com.example.signup.NAME_SHORT
import com.example.signup.NAME_SPECIAL
import com.example.signup.NICKNAME_ALREADY_USE
import com.example.signup.R
import com.example.signup.SignUpViewModel
import com.example.signup.ui.component.CustomButton
import com.example.signup.ui.component.CustomContentText
import com.example.signup.ui.component.CustomSmallButton
import com.example.signup.ui.component.CustomTextField
import com.example.signup.ui.component.CustomTitleText
import com.example.signup.ui.component.CustomUnderTextFieldText
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun InPutNickNameScreenMain(
    paddingValues: PaddingValues,
    viewModel: SignUpViewModel,
    buttonClick: () -> Unit,
) {


    var nicknameValidationMessage by remember { mutableIntStateOf(R.string.txt_nick_length) }
    val nickName by viewModel.userInfoState.collectAsState()
    var isCheck by remember { mutableStateOf(false) }
    var buttonOkay by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        viewModel.userInfoState.collectLatest { userInfo ->
            when (val nickNameState = userInfo.nickCheckStanBy) {
                is SignUpViewModel.CheckState.StanBy -> {
                    Log.d(javaClass.name.toString(), "Stand by check NickName")
                    nicknameValidationMessage = R.string.txt_nick_length
                }

                is SignUpViewModel.CheckState.ValueOkay -> {
                    Log.d(javaClass.name.toString(), "Success to set NickName")
                    buttonOkay = true
                    isCheck = true
                    viewModel.resetNickName()
                }

                is SignUpViewModel.CheckState.ValueNotOkay -> {
                    buttonOkay = false
                    isCheck = false
                    nicknameValidationMessage = when (nickNameState.errorMessage) {
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

                        else -> throw IllegalArgumentException("UNKNOW ERROR")
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
                bottom = 8.dp
            )
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            CustomTitleText(
                stringResource(R.string.txt_nick_title),
                Modifier
                    .fillMaxWidth()
                    .padding(start = 17.dp, end = 16.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            CustomContentText(
                stringResource(R.string.txt_nick_content),
                Modifier
                    .fillMaxWidth()
                    .padding(start = 17.dp, end = 16.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            CustomTextField(
                text = nickName.nickName,
                modifier = Modifier.fillMaxWidth()        .padding(start = 17.dp, end = 16.dp),
                onValueChange = { value -> viewModel.inputNickName(value) },
                inputType = KeyboardType.Password,
                hint = stringResource(R.string.hint_nick)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth()        .padding(start = 17.dp, end = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CustomUnderTextFieldText(
                        text = stringResource(nicknameValidationMessage),
                        color = if (nicknameValidationMessage == R.string.txt_nick_length)
                            Color(0xFF171717)
                        else
                            Color(0xFFFB4F4F)
                    )
                }

                CustomSmallButton(
                    stringResource(R.string.btn_check_nick_name),
                    onclick = { viewModel.checkNickName(nickName.nickName) },
                    enable = nickName.nickName.isNotEmpty() && !isCheck
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
                stringResource(R.string.btn_next),
                onclick = { buttonClick() },
                enable = buttonOkay
            )
        }
    }
}