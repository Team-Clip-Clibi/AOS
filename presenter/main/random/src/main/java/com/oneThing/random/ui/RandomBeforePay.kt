package com.oneThing.random.ui

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
import androidx.compose.ui.unit.dp
import com.example.core.AppTextStyles
import com.example.core.ColorStyle
import com.example.core.CustomDialogOneButton
import com.oneThing.random.R
import com.oneThing.random.RandomMatchViewModel
import com.oneThing.random.UiError
import com.oneThing.random.UiSuccess
import com.oneThing.random.component.EventView
import com.oneThing.random.component.RandomMatchDataView

@Composable
internal fun RandomBeforePay(
    viewModel: RandomMatchViewModel,
) {
    val uiState by viewModel.uiState.collectAsState()
    val success = uiState.success
    val error = uiState.error
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = ColorStyle.GRAY_100)
            .padding(top = 32.dp, start = 17.dp, end = 16.dp)
    ) {
        Text(
            text = stringResource(R.string.random_before_pay_title),
            style = AppTextStyles.HEAD_28_40_BOLD,
            color = ColorStyle.GRAY_800,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = stringResource(R.string.random_before_pay_subtitle),
            style = AppTextStyles.SUBTITLE_16_24_SEMI,
            color = ColorStyle.GRAY_600,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(32.dp))
        EventView(
            title = "원띵 출시 이벤트",
            content = "지금, 매칭비용은 단 2,900원이에요"
        )
        Spacer(modifier = Modifier.height(12.dp))
        if (success is UiSuccess.RandomMatchSuccess) {
            RandomMatchDataView(
                nickName = success.data.nickName,
                time = success.data.meetingTime,
                location = success.data.meetingLocation,
                address = success.data.meetingPlace
            )
        }
        if (error is UiError.TossNotInstalled) {
            CustomDialogOneButton(
                onDismiss = {
                    viewModel.initError()
                },
                buttonClick = {
                    viewModel.initError()
                },
                titleText = stringResource(R.string.txt_toss_dialog_title),
                contentText = stringResource(R.string.txt_toss_dialog_sub_title),
                buttonText = stringResource(R.string.btn_okay)
            )
        }
    }
}