package com.clip.report.ui.content

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.clip.core.ButtonXXL
import com.clip.core.ColorStyle
import com.clip.core.CustomSnackBar
import com.clip.core.TopAppbarClose
import com.clip.report.R
import com.clip.report.ReportViewModel

@Composable
internal fun ContentView(
    viewModel: ReportViewModel,
    onBackClick: () -> Unit,
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val content by viewModel.reportData.collectAsState()
    BackHandler(enabled = true) {
        onBackClick()
    }
    Scaffold(
        topBar = {
            TopAppbarClose(
                title = stringResource(R.string.top_category),
                onBackClick = { onBackClick() },
                isActionShow = false
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState,
                snackbar = { data ->
                    CustomSnackBar(data)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 17.dp, end = 16.dp, bottom = 91.dp)
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = ColorStyle.WHITE_100)
                    .padding(bottom = 8.dp)
                    .navigationBarsPadding()
            ) {
                HorizontalDivider(thickness = 1.dp, color = ColorStyle.GRAY_200)
                Spacer(modifier = Modifier.height(8.dp))
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 17.dp, end = 16.dp)) {
                    ButtonXXL(
                        onClick = viewModel::sendReport,
                        text = stringResource(R.string.btn_finish),
                        isEnable = content.content != "" && content.content.length >= 10,
                        disEnableButtonColor = ColorStyle.GRAY_200,
                        enableButtonColor = ColorStyle.PURPLE_400,
                        disEnableTextColor = ColorStyle.GRAY_800,
                        enableTextColor = ColorStyle.WHITE_100
                    )
                }
            }
        }
    ) { paddingValues ->
        ContentMainView(
            viewModel = viewModel,
            paddingValues = paddingValues,
            onProfilePage = onBackClick,
            content = content
        )
    }
}