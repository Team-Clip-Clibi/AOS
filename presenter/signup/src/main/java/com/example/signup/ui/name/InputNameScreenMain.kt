package com.example.signup.ui.name

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
internal fun InputNameScreenMain(
    paddingValues: PaddingValues,
    viewModel: SignUpViewModel,
    buttonClick: () -> Unit,
) {
    val name by viewModel.name.collectAsState()

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
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            CustomTitleText(stringResource(R.string.txt_sign_up_title))

            CustomContentText(stringResource(R.string.txt_name_content))

            Spacer(modifier = Modifier.height(32.dp))

            CustomTextField(
                text = name,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { name ->
                    viewModel.inputName(name)
                },
                inputType = KeyboardType.Password,
                hint = stringResource(R.string.hint_name)
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
                    val isValidName = name.matches(Regex("^[a-zA-Z가-힣]*$"))
                    CustomUnderTextFieldText(
                        text = stringResource(
                            if (isValidName) R.string.txt_name_korean_english
                            else R.string.txt_name_safe
                        ),
                        color = colorResource(
                            if (isValidName) R.color.red
                            else R.color.dark_gray
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))

            CustomButton(
                stringResource(R.string.btn_next),
                onclick = {buttonClick()},
                enable = name.isNotEmpty()
            )
        }
    }
}