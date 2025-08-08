package com.oneThing.random.ui

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
import com.example.core.TextFieldComponent
import com.oneThing.random.R
import com.oneThing.random.RandomMatchViewModel
import com.oneThing.random.component.SlidingTextBox

@Composable
internal fun RandomTmi(
    viewModel: RandomMatchViewModel,
    onBackClick: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val tmiData = listOf(
        "ex. 저는 시드니에 사는 것이 꿈이에요",
        "ex. 저는 매년 생일마다 증명사진을 찍어서 모아요",
        "ex. 저는 피자를 좋아 해요."
    )
    BackHandler(enabled = true) {
        onBackClick()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = ColorStyle.WHITE_100)
            .padding(top = 32.dp, start = 17.dp, end = 16.dp)
    ) {
        Text(
            text = stringResource(R.string.random_tmi_title),
            style = AppTextStyles.HEAD_28_40_BOLD,
            color = ColorStyle.GRAY_800
        )
        Spacer(modifier = Modifier.height(24.dp))
        SlidingTextBox(
            textList = tmiData
        )
        Spacer(modifier = Modifier.height(10.dp))
        TextFieldComponent(
            value = uiState.tmi,
            onValueChange = viewModel::tmi,
            maxLine = 1,
            maxLength = 50,
            hint = stringResource(R.string.random_hint)
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