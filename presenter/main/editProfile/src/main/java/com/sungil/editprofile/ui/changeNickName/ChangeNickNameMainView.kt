package com.sungil.editprofile.ui.changeNickName

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.sungil.editprofile.ProfileEditViewModel
import com.sungil.editprofile.R
import com.sungil.editprofile.ui.CustomButton
import com.sungil.editprofile.ui.CustomChangeDataTextField
import com.sungil.editprofile.ui.CustomUnderTextFieldText

@Composable
internal fun ChangeNickNameMainView(
    paddingValues: PaddingValues,
    viewModel: ProfileEditViewModel,
    finishedView: () -> Unit,
) {
    val state by viewModel.editProfileState.collectAsState()
    val userData = (state as? ProfileEditViewModel.EditProfileState.Success)?.data
    var nicknameValidationMessage by remember { mutableIntStateOf(R.string.txt_nick_length) }
    var nickname by remember { mutableStateOf(userData?.nickName ?: "오류") }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = paddingValues.calculateTopPadding() + 32.dp,
                bottom = 8.dp
            )
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 17.dp, end = 16.dp)
        ) {
            CustomChangeDataTextField(
                beforeText = nickname,
                inputType = KeyboardType.Text,
                onValueChange = { input ->
                    viewModel.changeNickName(input)

                    nicknameValidationMessage = when {
                        input.length < 2 -> R.string.txt_nick_length_low
                        input.length > 8 -> R.string.txt_nick_length_over
                        input.contains(Regex("[^a-zA-Z0-9가-힣]")) -> R.string.txt_nick_no_special
                        else -> R.string.txt_nick_length
                    }
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
            CustomUnderTextFieldText(
                text = stringResource(nicknameValidationMessage),
                color = if (nicknameValidationMessage == R.string.txt_nick_length)
                    Color(0xFF171717)
                else
                    Color(0xFFFB4F4F)
            )
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
                stringResource(R.string.btn_finish),
                onclick = { },
                enable = nicknameValidationMessage == R.string.txt_nick_length
            )
        }

    }
}