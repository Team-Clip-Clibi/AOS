package com.clip.report.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.clip.core.ColorStyle
import com.clip.report.R
import com.clip.report.ui.CustomReportItem
import androidx.compose.material3.Text
import com.clip.core.AppTextStyles

@Composable
internal fun ReportMainView(
    paddingValues: PaddingValues,
    onCategory: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = ColorStyle.WHITE_100)
            .padding(
                top = paddingValues.calculateTopPadding() + 32.dp,
                start = 17.dp,
                end = 16.dp,
                bottom = paddingValues.calculateBottomPadding() + 8.dp
            )
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = stringResource(R.string.txt_main_title),
            style = AppTextStyles.TITLE_20_28_SEMI,
            color = ColorStyle.GRAY_800
        )
        Spacer(modifier = Modifier.height(24.dp))
        CustomReportItem(
            title = stringResource(R.string.txt_main_content_match),
            textColor = ColorStyle.GRAY_800,
            subTitle = "",
            subTitleColor = ColorStyle.GRAY_800,
            buttonClick = onCategory
        )
        CustomReportItem(
            title = stringResource(R.string.txt_main_content_service),
            textColor = ColorStyle.GRAY_800,
            subTitle = "",
            subTitleColor = ColorStyle.GRAY_800,
            buttonClick = onCategory
        )
    }
}