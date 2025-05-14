package com.sungil.onethingmatch.ui.tmi

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core.AppTextStyles
import com.example.core.ButtonXXLPurple400
import com.example.core.ColorStyle
import com.example.core.TextFieldComponent
import com.sungil.onethingmatch.OneThingViewModel
import com.sungil.onethingmatch.R
import com.sungil.onethingmatch.component.SlidingTextBox

@Composable
internal fun TmiView(
    viewModel: OneThingViewModel,
    goNextPage: () -> Unit,
) {
    val tmiData = listOf(
        "ex. 저는 시드니에 사는 것이 꿈이에요",
        "ex. 저는 매년 생일마다 증명사진을 찍어서 모아요",
        "ex. 저는 피자를 좋아 해요."
    )
    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(bottom = 8.dp)
            ) {
                HorizontalDivider(
                    thickness = 1.dp,
                    color = ColorStyle.GRAY_200
                )
                Spacer(modifier = Modifier.height(8.dp))
                ButtonXXLPurple400(
                    onClick = goNextPage,
                    buttonText = stringResource(R.string.btn_finish),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 17.dp),
                    isEnable = uiState.tmi.trim().isNotEmpty() && uiState.tmi.trim().length <= 50
                )
            }
        },
        contentColor = ColorStyle.WHITE_100
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(ColorStyle.WHITE_100)
                .padding(
                    top = paddingValues.calculateTopPadding() + 32.dp,
                    start = 17.dp,
                    end = 16.dp,
                    bottom = paddingValues.calculateBottomPadding()
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
        }
    }
}