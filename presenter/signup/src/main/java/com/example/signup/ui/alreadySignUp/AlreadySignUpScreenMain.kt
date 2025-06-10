package com.example.signup.ui.alreadySignUp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core.AppTextStyles
import com.example.core.ColorStyle
import com.example.signup.R
import com.example.signup.SignUpViewModel

@Composable
internal fun AlreadySignUpScreenView(
    viewModel: SignUpViewModel,
) {
    val userInfo by viewModel.userInfoState.collectAsState()
    val context = LocalContext.current
    val account = listOf(
        UserData(
            title = context.getString(R.string.txt_signUp_name),
            content = userInfo.name.ifEmpty { "ERROR" }
        ),
        UserData(
            title = context.getString(R.string.txt_signUp_account),
            content = userInfo.platform.ifEmpty { "카카오" }
        ),
        UserData(
            title = context.getString(R.string.txt_signUp_day),
            content = userInfo.createdAt.ifEmpty { "ERROR" }
        )
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = ColorStyle.WHITE_100)
            .padding(top = 32.dp, start = 17.dp, end = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = stringResource(R.string.txt_signUp_title),
            style = AppTextStyles.SUBTITLE_16_24_SEMI,
            color = ColorStyle.GRAY_600,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = stringResource(R.string.txt_signUp_sub_title),
            style = AppTextStyles.HEAD_28_40_BOLD,
            color = ColorStyle.GRAY_800,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = stringResource(R.string.txt_signUp_id),
            style = AppTextStyles.CAPTION_12_18_SEMI,
            color = ColorStyle.GRAY_800,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))
        for(data in account){
            Box(modifier = Modifier.fillMaxWidth()){
                Text(
                    text = data.title,
                    style = AppTextStyles.CAPTION_12_18_SEMI,
                    color = ColorStyle.GRAY_800,
                    modifier = Modifier.align(Alignment.CenterStart)
                )
                Text(
                    text = data.content,
                    style = AppTextStyles.SUBTITLE_16_24_SEMI,
                    color = ColorStyle.GRAY_600,
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
data class UserData(
    val title : String,
    val content : String
)