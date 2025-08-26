package com.clip.report.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.clip.core.ColorStyle
import com.clip.report.R
import com.clip.report.ui.CustomGrayButton
import com.clip.report.ui.CustomReportItem
import com.clip.report.ui.CustomTitleText

@Composable
internal fun ReportMainView(
    paddingValues: PaddingValues,
    onCategory: () -> Unit,
) {
    val scrollState = rememberScrollState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorStyle.WHITE_100)
            .verticalScroll(scrollState)
            .navigationBarsPadding()
            .padding(
                top = paddingValues.calculateTopPadding() + 32.dp,
                start = 17.dp,
                end = 16.dp,
                bottom = 8.dp
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
        ) {
            CustomTitleText(stringResource(R.string.txt_main_title))
            Spacer(Modifier.height(24.dp))
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
            Spacer(Modifier.weight(1f))
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            CustomTitleText(stringResource(R.string.txt_main_title_before))
            Spacer(Modifier.height(12.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomGrayButton(
                    text = stringResource(R.string.btn_main_guide),
                    modifier = Modifier.weight(1f),
                    color = ColorStyle.GRAY_800,
                    clickable = {}
                )
                Spacer(Modifier.width(12.dp))
                CustomGrayButton(
                    text = stringResource(R.string.btn_main_one),
                    modifier = Modifier.weight(1f),
                    color = ColorStyle.GRAY_800,
                    clickable = {}
                )
            }
        }
    }
}