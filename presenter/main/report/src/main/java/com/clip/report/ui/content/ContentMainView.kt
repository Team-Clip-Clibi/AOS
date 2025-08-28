package com.clip.report.ui.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.clip.core.AppTextStyles
import com.clip.core.ColorStyle
import com.clip.core.CustomDialogOneButton
import com.clip.report.R
import com.clip.report.ReportViewModel
import com.clip.report.ui.CustomTextField

@Composable
internal fun ContentMainView(
    viewModel: ReportViewModel,
    paddingValues: PaddingValues,
    onProfilePage: () -> Unit,
    content: ReportViewModel.Report,
) {
    val dialog by viewModel.showDialog.collectAsState()
    val networkResult by viewModel.networkResult.collectAsState()
    LaunchedEffect(networkResult) {
        when (networkResult) {
            is ReportViewModel.Result.Success -> {
                viewModel.showDialog()
            }

            else -> Unit
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = ColorStyle.WHITE_100)
            .verticalScroll(
                rememberScrollState()
            )
            .padding(
                top = paddingValues.calculateTopPadding() + 32.dp,
                bottom = 8.dp,
                start = 17.dp,
                end = 16.dp
            )
    ) {
        Text(
            text = stringResource(R.string.txt_content_title),
            style = AppTextStyles.TITLE_20_28_SEMI,
            color = ColorStyle.GRAY_800
        )
        Spacer(modifier = Modifier.height(24.dp))
        CustomTextField(
            value = content.content,
            onValueChange = { input ->
                viewModel.setContent(input)
            },
            placeholder = stringResource(R.string.txt_content_hint)
        )
        if (dialog) {
            CustomDialogOneButton(
                onDismiss = onProfilePage,
                buttonClick = onProfilePage,
                titleText = stringResource(R.string.dialog_title),
                contentText = stringResource(R.string.dialog_content),
                buttonText = stringResource(R.string.btn_dialog)
            )
        }
    }
}