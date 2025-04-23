package com.example.signup.ui.name

import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.signup.ERROR_NETWORK_ERROR
import com.example.signup.R
import com.example.signup.RE_LOGIN
import com.example.signup.SignUpViewModel
import com.example.signup.ui.component.CustomButton
import com.example.signup.ui.component.CustomContentText
import com.example.signup.ui.component.CustomTextField
import com.example.signup.ui.component.CustomTitleText
import com.example.signup.ui.component.CustomUnderTextFieldText
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun InputNameScreenMain(
    paddingValues: PaddingValues,
    viewModel: SignUpViewModel,
    buttonClick: () -> Unit,
    reLogin: () -> Unit,
    snackBarHostState: SnackbarHostState,
) {

    var isValidName by remember { mutableIntStateOf(R.string.txt_name_safe) }
    val name by viewModel.userInfoState.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.userInfoState.collectLatest { userInfoState ->
            when (userInfoState.nameCheck) {
                is SignUpViewModel.CheckState.StanBy -> {
                    isValidName = R.string.txt_name_safe
                }

                is SignUpViewModel.CheckState.ValueNotOkay -> {
                    when ((userInfoState.nameCheck as SignUpViewModel.CheckState.ValueNotOkay).errorMessage) {
                        ERROR_NETWORK_ERROR -> {
                            snackBarHostState.showSnackbar(
                                message = context.getString(R.string.msg_network_error),
                                duration = SnackbarDuration.Short
                            )
                        }

                        RE_LOGIN -> {
                            snackBarHostState.showSnackbar(
                                message = context.getString(R.string.msg_reLogin),
                                duration = SnackbarDuration.Short
                            )
                            viewModel.resetName()
                            reLogin()
                        }

                        else -> isValidName = R.string.txt_name_korean_english
                    }

                }

                is SignUpViewModel.CheckState.ValueOkay -> {
                    buttonClick()
                    viewModel.resetName()
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
            .background(color = Color(0xFFFFFFFF))
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            CustomTitleText(
                stringResource(R.string.txt_name_title),
                Modifier
                    .fillMaxWidth()
                    .padding(start = 17.dp, end = 16.dp)
            )

            Spacer(modifier = Modifier.height(4.dp))

            CustomContentText(
                stringResource(R.string.txt_name_content),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 17.dp, end = 16.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            CustomTextField(
                text = name.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(start = 17.dp, end = 16.dp),
                onValueChange = { input ->
                    viewModel.inputName(input)
                },
                inputType = KeyboardType.Password,
                hint = stringResource(R.string.hint_name)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 17.dp, end = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CustomUnderTextFieldText(
                        text = stringResource(isValidName),
                        color = if (isValidName == R.string.txt_name_safe)
                            Color(0xFF666666)
                        else
                            Color(0xFFFB4F4F)
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))
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
                onclick = { viewModel.checkName(name.name) },
                enable = name.name.isNotEmpty()
            )
        }
    }
}