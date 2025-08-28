package com.clip.report.ui.category

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.clip.core.ColorStyle
import com.clip.report.R
import com.clip.report.REPORT
import com.clip.report.ReportViewModel
import com.clip.report.ui.CustomReportItem

@Composable
internal fun CategoryMainView(
    viewModel: ReportViewModel,
    paddingValues: PaddingValues,
    content : () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorStyle.WHITE_100)
            .padding(top = paddingValues.calculateTopPadding() + 20.dp, start = 17.dp, end = 16.dp)
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState())
    ) {
        CustomReportItem(
            title = stringResource(R.string.txt_category_slang),
            textColor = ColorStyle.GRAY_800,
            subTitle = "",
            subTitleColor = ColorStyle.GRAY_800,
            buttonClick = {
                viewModel.setCategory(REPORT.SLANG)
                content()
            }
        )
        CustomReportItem(
            title = stringResource(R.string.txt_category_crime),
            textColor = ColorStyle.GRAY_800,
            subTitle = "",
            subTitleColor = ColorStyle.GRAY_800,
            buttonClick = {
                viewModel.setCategory(REPORT.CRIME)
                content()
            }
        )
        CustomReportItem(
            title = stringResource(R.string.txt_category_sex),
            textColor = ColorStyle.GRAY_800,
            subTitle = "",
            subTitleColor = ColorStyle.GRAY_800,
            buttonClick = {
                viewModel.setCategory(REPORT.SEX)
                content()
            }
        )
        CustomReportItem(
            title = stringResource(R.string.txt_category_false),
            textColor = ColorStyle.GRAY_800,
            subTitle = "",
            subTitleColor = ColorStyle.GRAY_800,
            buttonClick = {
                viewModel.setCategory(REPORT.FALSE)
                content()
            }
        )
        CustomReportItem(
            title = stringResource(R.string.txt_category_abuse),
            textColor = ColorStyle.GRAY_800,
            subTitle = "",
            subTitleColor = ColorStyle.GRAY_800,
            buttonClick = {
                viewModel.setCategory(REPORT.ABUSING)
                content()
            }
        )
        CustomReportItem(
            title = stringResource(R.string.txt_category_etc),
            textColor = ColorStyle.GRAY_800,
            subTitle = "",
            subTitleColor = ColorStyle.GRAY_800,
            buttonClick = {
                viewModel.setCategory(REPORT.ETC)
                content()
            }
        )
    }
}