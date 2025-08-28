package com.clip.report.ui.main

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.clip.core.ColorStyle
import com.clip.core.CustomSnackBar
import com.clip.core.TopAppbarClose
import com.clip.report.R
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import com.clip.core.AppTextStyles
import com.clip.core.ButtonL

@Composable
internal fun ReportView(
    onBackClick: () -> Unit,
    category: () -> Unit,
) {
    val snackBarHostState = remember { SnackbarHostState() }
    BackHandler(enabled = true) {
        onBackClick()
    }
    Scaffold(
        topBar = {
            TopAppbarClose(
                title = stringResource(R.string.top_main),
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
                    .padding(start = 17.dp, end = 16.dp, bottom = 8.dp)
                    .navigationBarsPadding()
            ) {
                Text(
                    text = stringResource(R.string.txt_main_title_before),
                    style = AppTextStyles.TITLE_20_28_SEMI,
                    color = ColorStyle.GRAY_800
                )
                Spacer(Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(Modifier.weight(1f)) {
                        ButtonL(
                            text = stringResource(R.string.btn_main_guide),
                            buttonColor = ColorStyle.GRAY_200,
                            textColor = ColorStyle.GRAY_800,
                            isSelected = true,
                            onClick = {}
                        )
                    }
                    Box(Modifier.weight(1f)) {
                        ButtonL(
                            text = stringResource(R.string.btn_main_one),
                            buttonColor = ColorStyle.GRAY_200,
                            textColor = ColorStyle.GRAY_800,
                            isSelected = true,
                            onClick = {}
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        ReportMainView(
            paddingValues = paddingValues,
            onCategory = category
        )
    }
}