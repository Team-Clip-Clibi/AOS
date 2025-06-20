package com.sungil.onethingmatch.ui.pay

import androidx.compose.foundation.background
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core.AppTextStyles
import com.example.core.ColorStyle
import com.example.core.CustomDialogOneButton
import com.sungil.onethingmatch.ERROR_ORDER_TIME_LATE
import com.sungil.onethingmatch.OneThingViewModel
import com.sungil.onethingmatch.R
import com.sungil.onethingmatch.UiError
import com.sungil.onethingmatch.component.EventView

@Composable
internal fun BeforePayView(
    viewModel: OneThingViewModel,
    goDayPage: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = ColorStyle.GRAY_100)
            .padding(
                top = 32.dp,
                start = 17.dp,
                end = 16.dp,
            )
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = stringResource(R.string.txt_before_pay_title),
            style = AppTextStyles.HEAD_28_40_BOLD,
            color = ColorStyle.GRAY_800,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = stringResource(R.string.txt_before_pay_sub_title),
            style = AppTextStyles.SUBTITLE_16_24_SEMI,
            color = ColorStyle.GRAY_600,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(32.dp))
        EventView(
            title = "원띵 출시 이벤트",
            content = "지금, 매칭비용은 단 2,900원이에요."
        )
        if (uiState.error is UiError.TossNotInstall) {
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
        if (uiState.error is UiError.FailOrder && (uiState.error as UiError.FailOrder).message == ERROR_ORDER_TIME_LATE) {
            CustomDialogOneButton(
                onDismiss = {
                    viewModel.initError()
                    viewModel.date()
                    goDayPage()
                },
                buttonClick = {
                    viewModel.initError()
                    viewModel.date()
                    goDayPage()
                },
                titleText = stringResource(R.string.txt_order_time_late_title),
                contentText = stringResource(R.string.txt_order_time_late_sub_title),
                buttonText = stringResource(R.string.btn_order_time_late_yes)
            )
        }
    }
}