package com.example.signup.ui.nickname

import android.widget.Space
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.signup.R
import com.example.signup.SignUpViewModel
import com.example.signup.ui.component.CustomButton
import com.example.signup.ui.component.CustomContentText
import com.example.signup.ui.component.CustomTextField
import com.example.signup.ui.component.CustomTitleText
import com.example.signup.ui.component.CustomUnderTextFieldText

@Composable
internal fun InPutNickNameScreenMain(
    paddingValues: PaddingValues,
    viewModel: SignUpViewModel,
    buttonClick: () -> Unit,
) {
    val nickName by viewModel.nickName.collectAsState()
    val nicknameValidationMessage = when {
        nickName.contains(Regex("[^a-zA-Z가-힣0-9]")) -> {
            R.string.txt_nick_no_special
        }

        nickName.length <= 8 -> {
            R.string.txt_nick_length
        }

        nickName.length > 8 -> {
            R.string.txt_nick_length_over
        }

        else -> {
            R.string.txt_nick_length_low
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
        Column {
            CustomTitleText(stringResource(R.string.txt_nick_title))
            CustomContentText(stringResource(R.string.txt_nick_content))

            Spacer(modifier = Modifier.height(10.dp))

            CustomTextField(
                text = nickName,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { nickName ->
                    viewModel.inputName(nickName)
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
                            when {
                                nickName.contains(Regex("[^a-zA-Z가-힣0-9]")) -> {
                                    R.color.red
                                }

                                nickName.length <= 8 -> {
                                    R.color.red
                                }

                                nickName.length > 8 -> {
                                    R.color.red
                                }

                                else -> {
                                    R.color.dark_gray
                                }
                            }
                        )
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    CustomButton(
                        stringResource(R.string.btn_next),
                        onclick = { buttonClick() },
                        enable = true
                    )
                }
            }
        }
    }
}