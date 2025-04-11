package com.sungil.report.ui.category

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sungil.report.R
import com.sungil.report.REPORT
import com.sungil.report.ReportViewModel
import com.sungil.report.ui.CustomReportItem

@Composable
internal fun CategoryMainView(
    viewModel: ReportViewModel,
    paddingValues: PaddingValues,
    content : () -> Unit
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
            .padding(top = paddingValues.calculateTopPadding() + 20.dp, start = 17.dp, end = 16.dp)
            .navigationBarsPadding()
            .verticalScroll(scrollState)
    ) {
        CustomReportItem(
            title = stringResource(R.string.txt_category_slang),
            textColor = 0xFF171717,
            subTitle = "",
            subTitleColor = 0xFF171717,
            buttonClick = {
                viewModel.setCategory(REPORT.SLANG)
                content()
            }
        )
        CustomReportItem(
            title = stringResource(R.string.txt_category_crime),
            textColor = 0xFF171717,
            subTitle = "",
            subTitleColor = 0xFF171717,
            buttonClick = {
                viewModel.setCategory(REPORT.CRIME)
                content()
            }
        )
        CustomReportItem(
            title = stringResource(R.string.txt_category_sex),
            textColor = 0xFF171717,
            subTitle = "",
            subTitleColor = 0xFF171717,
            buttonClick = {
                viewModel.setCategory(REPORT.SEX)
                content()
            }
        )
        CustomReportItem(
            title = stringResource(R.string.txt_category_false),
            textColor = 0xFF171717,
            subTitle = "",
            subTitleColor = 0xFF171717,
            buttonClick = {
                viewModel.setCategory(REPORT.FALSE)
                content()
            }
        )
        CustomReportItem(
            title = stringResource(R.string.txt_category_abuse),
            textColor = 0xFF171717,
            subTitle = "",
            subTitleColor = 0xFF171717,
            buttonClick = {
                viewModel.setCategory(REPORT.ABUSING)
                content()
            }
        )
        CustomReportItem(
            title = stringResource(R.string.txt_category_etc),
            textColor = 0xFF171717,
            subTitle = "",
            subTitleColor = 0xFF171717,
            buttonClick = {
                viewModel.setCategory(REPORT.ETC)
                content()
            }
        )
    }
}