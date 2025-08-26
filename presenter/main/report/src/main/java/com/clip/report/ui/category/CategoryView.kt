package com.clip.report.ui.category

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.clip.report.R
import com.clip.report.ReportViewModel
import com.clip.report.ui.CustomSnackBar
import com.clip.report.ui.CustomTopBar

@Composable
internal fun CategoryView(
    viewModel: ReportViewModel,
    onBackClick: () -> Unit,
    content: () -> Unit,
) {
    val snackBarHostState = remember { SnackbarHostState() }
    BackHandler(enabled = true) {
        onBackClick()
    }

    Scaffold(
        topBar = {
            CustomTopBar(
                title = stringResource(R.string.top_category),
                onBackClick = { onBackClick() }
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
        }
    ) { paddingValues ->
        CategoryMainView(
            viewModel = viewModel,
            paddingValues = paddingValues,
            content = content
        )
    }
}