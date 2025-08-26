package com.clip.signup.ui.phone

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.clip.core.AppTextStyles
import com.clip.core.ColorStyle
import com.clip.signup.R
import com.clip.signup.SignUpViewModel
import com.clip.signup.ui.component.CustomTextField

@Composable
internal fun PhoneNumberView(
    viewModel: SignUpViewModel,
    ) {
    val phoneNumber by viewModel.userInfoState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = ColorStyle.WHITE_100)
            .padding(top = 32.dp, start = 17.dp, end = 16.dp)
    ) {
        Text(
            text = stringResource(R.string.txt_phone_title),
            style = AppTextStyles.SUBTITLE_16_24_SEMI,
            color = ColorStyle.GRAY_600,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = stringResource(R.string.txt_phone_title_sub),
            style = AppTextStyles.HEAD_28_40_BOLD,
            color = ColorStyle.GRAY_800,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(32.dp))
        CustomTextField(
            text = phoneNumber.phoneNumber,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = {number -> viewModel.inputPhoneNumber(number)},
            inputType = KeyboardType.Phone,
            hint = stringResource(R.string.hint_input_phone_number)
        )
    }
}
