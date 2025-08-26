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
import com.clip.core.AppTextStyles
import com.clip.core.ColorStyle
import com.clip.core.SlidingTextBox
import com.clip.core.TextFieldComponent
import com.oneThing.random.R
import com.oneThing.random.RandomMatchViewModel

@Composable
internal fun RandomTopic(
    viewModel: RandomMatchViewModel,
    onBackClick: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val tmiData = listOf(
        "ex. 유럽 여행기 대화 나눠요",
        "ex. 다들 면접 준비 어떻게 하고 있는지 궁금해요",
        "ex. 상해 여행기 대화 나눠요"
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
            text = stringResource(R.string.random_topic_title),
            style = AppTextStyles.HEAD_28_40_BOLD,
            color = ColorStyle.GRAY_800
        )
        Spacer(modifier = Modifier.height(24.dp))
        SlidingTextBox(
            textList = tmiData
        )
        Spacer(modifier = Modifier.height(10.dp))
        TextFieldComponent(
            value = uiState.topic,
            onValueChange = viewModel::topic,
            maxLine = 1,
            maxLength = 50,
            hint = stringResource(R.string.random_hint)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "${uiState.topic.length}/50",
            style = AppTextStyles.CAPTION_10_14_MEDIUM,
            color = ColorStyle.GRAY_700,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.End
        )
    }
}