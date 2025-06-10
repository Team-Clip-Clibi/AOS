package com.sungil.pay_finish.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core.AppTextStyles
import com.example.core.ButtonXXLPurple400
import com.example.core.ButtonXXLWhite
import com.example.core.ColorStyle
import com.example.core.TopAppBarWithCloseButton
import com.sungil.pay_finish.BuildConfig
import com.sungil.pay_finish.R

@Composable
internal fun PayFinishView(
    orderType: String,
    onBackClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBarWithCloseButton(
                title = when (orderType) {
                    BuildConfig.ONE_THING -> {
                        stringResource(R.string.top_bar_oneThing)
                    }

                    BuildConfig.RANDOM -> {
                        stringResource(R.string.top_bar_random)
                    }

                    else -> stringResource(R.string.top_bar_oneThing)
                },
                onBackClick = onBackClick,
                isNavigationShow = false
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(8.dp)
            ) {
                HorizontalDivider(
                    thickness = 1.dp,
                    color = ColorStyle.GRAY_200
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = ColorStyle.WHITE_100)
                        .padding(start = 17.dp, end = 16.dp)
                ) {
                    ButtonXXLWhite(
                        onClick = {},
                        buttonText = stringResource(R.string.btn_match),
                        modifier = Modifier
                            .weight(1f)
                            .height(60.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    ButtonXXLPurple400(
                        onClick = onBackClick,
                        buttonText = stringResource(R.string.btn_home),
                        modifier = Modifier
                            .weight(1f)
                            .height(60.dp)
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = ColorStyle.WHITE_100)
                .padding(
                    top = paddingValues.calculateTopPadding() + 32.dp,
                    start = 17.dp,
                    end = 16.dp,
                    bottom = paddingValues.calculateBottomPadding()
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
            ) {
                Text(
                    text = stringResource(R.string.txt_title),
                    style = AppTextStyles.HEAD_28_40_BOLD,
                    color = ColorStyle.GRAY_800
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(R.string.txt_sub_title),
                    style = AppTextStyles.SUBTITLE_16_24_SEMI,
                    color = ColorStyle.GRAY_600
                )
            }
            Image(
                painter = painterResource(R.drawable.ic_pay_success),
                contentDescription = "pay success image",
                modifier = Modifier
                    .width(148.dp)
                    .height(148.dp)
                    .align(Alignment.Center)
            )
        }
    }
}