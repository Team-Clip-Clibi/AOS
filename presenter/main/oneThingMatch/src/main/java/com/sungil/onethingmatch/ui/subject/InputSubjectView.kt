package com.sungil.onethingmatch.ui.subject

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material3.Text
import com.example.core.AppTextStyles
import com.example.core.ButtonXXLPurple400
import com.example.core.ColorStyle
import com.example.core.TextFieldComponent
import com.sungil.onethingmatch.OneThingViewModel
import com.sungil.onethingmatch.R
import com.sungil.onethingmatch.component.SlidingTextBox

@Composable
internal fun InputSubjectView(
    viewModel: OneThingViewModel,
    goNextPage: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val testData = listOf(
        "ex. 유럽 여행기 대화 나눠요",
        "ex. 다들 면접 준비 어떻게 하고 있는지 궁금해요",
        "ex. 상해 여행기 대화 나눠요"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorStyle.WHITE_100)
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .align(Alignment.TopCenter)
                .padding(top = 32.dp, start = 17.dp, end = 16.dp)

        ) {
            Text(
                text = stringResource(R.string.txt_subject_title),
                style = AppTextStyles.HEAD_28_40_BOLD,
                color = ColorStyle.GRAY_800
            )
            Spacer(Modifier.height(24.dp))
            SlidingTextBox(testData)
            TextFieldComponent(
                value = uiState.subject,
                onValueChange = viewModel::onSubjectChanged,
                maxLine = 1,
                maxLength = 50,
                hint = stringResource(R.string.txt_subject_hint)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${uiState.subject.length}/50",
                style = AppTextStyles.CAPTION_10_14_MEDIUM,
                color = ColorStyle.GRAY_700,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )
            Spacer(Modifier.weight(1f))
        }

        ButtonXXLPurple400(
            onClick = { goNextPage() },
            buttonText = stringResource(R.string.btn_finish),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            isEnable = uiState.subject.trim().isNotEmpty() &&
                    uiState.subject.length <= 50
        )
    }
}
