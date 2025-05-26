package com.sungil.editprofile.ui.changeNickName

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core.AppTextStyles
import com.example.core.ButtonSmall
import com.example.core.ColorStyle
import com.example.core.TextFieldComponent
import com.sungil.editprofile.ERROR_ALREADY_USE
import com.sungil.editprofile.ERROR_NETWORK
import com.sungil.editprofile.ERROR_SPECIAL
import com.sungil.editprofile.ERROR_TOKEN_NULL
import com.sungil.editprofile.ERROR_TO_LONG
import com.sungil.editprofile.ERROR_TO_SHORT
import com.sungil.editprofile.ERROR_USER_TOKEN_NLL
import com.sungil.editprofile.ProfileEditViewModel
import com.sungil.editprofile.R
import com.sungil.editprofile.UiError
import com.sungil.editprofile.UiSuccess

@Composable
internal fun ChangeNickNameMainView(
    paddingValues: PaddingValues,
    viewModel: ProfileEditViewModel,
    finishedView: () -> Unit,
    snackBarHost: SnackbarHostState,
) {
    val uiState by viewModel.uiState.collectAsState()
    var nicknameValidationMessage by remember { mutableIntStateOf(R.string.txt_nick_length) }

    val context = LocalContext.current

    LaunchedEffect(uiState.error, uiState.success) {
        when (val error = uiState.error) {
            is UiError.Error -> {
                when (error.message) {
                    ERROR_TO_LONG -> {
                        nicknameValidationMessage = R.string.txt_nick_length_over
                    }

                    ERROR_TO_SHORT -> {
                        nicknameValidationMessage = R.string.txt_nick_length_low
                    }

                    ERROR_SPECIAL -> {
                        nicknameValidationMessage = R.string.txt_nick_no_special
                    }

                    ERROR_NETWORK, ERROR_TOKEN_NULL, ERROR_USER_TOKEN_NLL -> {
                        snackBarHost.showSnackbar(
                            message = context.getString(R.string.txt_network_error),
                            duration = SnackbarDuration.Short
                        )
                    }

                    ERROR_ALREADY_USE -> {
                        snackBarHost.showSnackbar(
                            message = context.getString(R.string.txt_already_use),
                            duration = SnackbarDuration.Short
                        )
                    }

                    else -> throw IllegalArgumentException("Unsupported error: ${error.message}")
                }
            }

            UiError.None -> Unit
        }

        when (uiState.success) {
            is UiSuccess.Success -> {
                snackBarHost.showSnackbar(
                    message = context.getString(R.string.txt_message_okay),
                    duration = SnackbarDuration.Short
                )
                viewModel.initSuccessError()
            }

            else -> Unit
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = paddingValues.calculateTopPadding() + 32.dp, start = 17.dp, end = 16.dp)
    ) {
        TextFieldComponent(
            value = uiState.nickName,
            onValueChange = { text ->
                viewModel.setNickName(text)
                validateNickname(text)
            },
            maxLine = 1,
            maxLength = 9,
        )
        Spacer(modifier = Modifier.height(19.dp))
        Row(
            modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(nicknameValidationMessage),
                style = AppTextStyles.CAPTION_12_18_SEMI,
                color = ColorStyle.GRAY_600
            )
            ButtonSmall(
                text = stringResource(R.string.btn_nickname_check),
                isEnable = if (nicknameValidationMessage == R.string.txt_nick_length) true else false,
                onClick = {
                    /**
                     * 닉네임 중복 검사 로직 구현
                     */
                }
            )
        }
    }
}

private fun validateNickname(input: String): Int {
    return when {
        input.length < 2 -> R.string.txt_nick_length_low
        input.length > 8 -> R.string.txt_nick_length_over
        input.contains(Regex("[^a-zA-Z0-9가-힣]")) -> R.string.txt_nick_no_special
        else -> R.string.txt_nick_length
    }
}