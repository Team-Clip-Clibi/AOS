package com.sungil.onethingmatch.ui.tmi

import androidx.activity.compose.BackHandler
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.core.AppTextStyles
import com.example.core.ColorStyle
import com.example.core.SlidingTextBox
import com.example.core.TextFieldComponent
import com.sungil.onethingmatch.OneThingViewModel
import com.sungil.onethingmatch.R

@Composable
internal fun TmiView(
    viewModel: OneThingViewModel,
    onBackClick: () -> Unit,
) {
    BackHandler(enabled = true) {
        onBackClick()
    }
    val tmiData = listOf(
        "ex. 저는 시드니에 사는 것이 꿈이에요",
        "ex. 저는 매년 생일마다 증명사진을 찍어서 모아요",
        "ex. 저는 피자를 좋아 해요."
    )
    val uiState by viewModel.uiState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorStyle.WHITE_100)
            .padding(
                top = 32.dp,
                start = 17.dp,
                end = 16.dp,
            )
    ) {
        Text(
            text = stringResource(R.string.txt_tmi_title),
            style = AppTextStyles.HEAD_28_40_BOLD,
            color = ColorStyle.GRAY_800,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))
        SlidingTextBox(tmiData)
        Spacer(modifier = Modifier.height(10.dp))
        TextFieldComponent(
            value = uiState.tmi,
            onValueChange = viewModel::onTmiChanged,
            maxLine = 1,
            maxLength = 50,
            hint = stringResource(R.string.txt_hint)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "${uiState.tmi.length}/50",
            style = AppTextStyles.CAPTION_10_14_MEDIUM,
            color = ColorStyle.GRAY_700,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.End
        )
    }
}