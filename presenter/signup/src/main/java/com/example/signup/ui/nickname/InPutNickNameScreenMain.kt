package com.example.signup.ui.nickname

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.signup.NAME_LONG
import com.example.signup.NAME_SHORT
import com.example.signup.NAME_SPECIAL
import com.example.signup.R
import com.example.signup.SignUpViewModel
import com.example.signup.ui.component.CustomButton
import com.example.signup.ui.component.CustomContentText
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
    val nickName by viewModel.nickName.collectAsState()

    var nicknameValidationMessage = -1

    LaunchedEffect(Unit) {
        viewModel.nameCheck.collectLatest { state ->
            when (state) {
                is SignUpViewModel.NameCheck.NameStandby -> {
                    nicknameValidationMessage = R.string.txt_nick_length
                }

                is SignUpViewModel.NameCheck.NameIsNotOkay -> {
                    nicknameValidationMessage = when (state.errorMessage) {
                        NAME_LONG -> {
                            R.string.txt_nick_length_over
                        }

                        NAME_SHORT -> {
                            R.string.txt_nick_length_low
                        }

                        NAME_SPECIAL -> {
                            R.string.txt_nick_no_special
                        }

                        else -> throw IllegalArgumentException("UNKNOW ERROR")
                    }
                }

                is SignUpViewModel.NameCheck.NameIsOkay -> {
                    buttonClick()
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            CustomTitleText(stringResource(R.string.txt_nick_title))
            CustomContentText(stringResource(R.string.txt_nick_content))

            Spacer(modifier = Modifier.height(10.dp))

            CustomTextField(
                text = nickName,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { nickName ->
                    viewModel.inputNickName(nickName)
                },
                inputType = KeyboardType.Password,
                hint = stringResource(R.string.hint_nick)
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
                    CustomUnderTextFieldText(
                        text = stringResource(
                            nicknameValidationMessage
                        ),

                        color = colorResource(
                            if (nicknameValidationMessage == R.string.txt_nick_length) {
                                R.color.dark_gray
                            } else {
                                R.color.red
                            }
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            CustomButton(
                stringResource(R.string.btn_next),
                onclick = { viewModel.checkNickName(nickName) },
                enable = nickName.isNotEmpty()
            )
        }

    }
}