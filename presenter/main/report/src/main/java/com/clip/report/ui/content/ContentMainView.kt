package com.clip.report.ui.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.clip.core.ColorStyle
import com.clip.report.R
import com.clip.report.ReportViewModel
import com.clip.report.ui.CustomButton
import com.clip.report.ui.CustomDialog
import com.clip.report.ui.CustomTextField
import com.clip.report.ui.CustomTitleText

@Composable
internal fun ContentMainView(
    viewModel: ReportViewModel,
    paddingValues: PaddingValues,
    onProfilePage: () -> Unit,
) {
    val content by viewModel.reportData.collectAsState()
    val dialog by viewModel.showDialog.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.networkResult.collect { state ->
            when (state) {
                is ReportViewModel.Result.Error -> {

                }

                is ReportViewModel.Result.Success -> {
                    viewModel.showDialog()
                }

                else -> Unit
            }
        }
    }
    val scrollState = rememberScrollState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorStyle.WHITE_100)
            .verticalScroll(scrollState)
            .padding(top = paddingValues.calculateTopPadding() + 32.dp, bottom = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(start = 17.dp, end = 16.dp)
                .align(Alignment.TopCenter)
        ) {
            CustomTitleText(stringResource(R.string.txt_content_title))
            Spacer(Modifier.height(24.dp))
            CustomTextField(
                value = content.content,
                onValueChange = { data ->
                    viewModel.setContent(data)
                },
                placeholder = stringResource(R.string.txt_content_hint)
            )
            Spacer(Modifier.weight(1f))
        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            HorizontalDivider(thickness = 1.dp, color = Color(0xFFEFEFEF))
            Spacer(modifier = Modifier.height(8.dp))
            CustomButton(
                stringResource(R.string.btn_finish),
                onclick = {
                    viewModel.sendReport()
                },
                enable = content.content != "" && content.content.length >= 10
            )
        }
        if (dialog) {
            CustomDialog(
                onDismiss = {
                    onProfilePage()
                },
                buttonClick = {
                    onProfilePage()
                },
                titleText = stringResource(R.string.dialog_title),
                contentText = stringResource(R.string.dialog_content),
                buttonText = stringResource(R.string.btn_dialog)
            )
        }
    }
}