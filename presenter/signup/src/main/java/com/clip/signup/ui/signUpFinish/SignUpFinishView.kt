package com.clip.signup.ui.signUpFinish

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.clip.core.AppTextStyles
import com.clip.core.ColorStyle
import com.clip.signup.R

@Composable
internal fun SignUpFinishView() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = ColorStyle.WHITE_100)
            .padding(top = 10.dp, start = 16.dp, end = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_sign_up_finish),
                contentDescription = "sign up complete",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .width(200.dp)
                    .height(201.dp)
            )
            Spacer(modifier = Modifier.height(60.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.txt_sign_up_finish),
                style = AppTextStyles.SUBTITLE_16_24_SEMI,
                color = ColorStyle.PURPLE_400,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.txt_sign_up_welcome),
                style = AppTextStyles.HEAD_30_42_BOLD,
                color = ColorStyle.GRAY_800,
                textAlign = TextAlign.Center
            )
        }
    }
}