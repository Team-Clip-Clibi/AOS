package com.sungil.onethingmatch.ui.subject

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import com.example.core.AppTextStyles
import com.example.core.ColorStyle
import com.example.core.SlidingTextBox
import com.example.core.TextFieldComponent
import com.sungil.onethingmatch.OneThingViewModel
import com.sungil.onethingmatch.R

@Composable
internal fun InputSubjectView(
    viewModel: OneThingViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    val subjectData = listOf(
        "ex. 유럽 여행기 대화 나눠요",
        "ex. 다들 면접 준비 어떻게 하고 있는지 궁금해요",
        "ex. 상해 여행기 대화 나눠요"
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorStyle.WHITE_100)
            .padding(
                top = 32.dp,
                start = 17.dp,
                end = 16.dp,
            )
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = stringResource(R.string.txt_subject_title),
            style = AppTextStyles.HEAD_28_40_BOLD,
            color = ColorStyle.GRAY_800
        )
        Spacer(Modifier.height(24.dp))
        SlidingTextBox(subjectData)
        TextFieldComponent(
            value = uiState.topic,
            onValueChange = viewModel::onSubjectChanged,
            maxLine = 1,
            maxLength = 50,
            hint = stringResource(R.string.txt_hint)
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
