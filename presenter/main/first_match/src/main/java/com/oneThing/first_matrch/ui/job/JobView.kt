package com.oneThing.first_matrch.ui.job

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.core.ColorStyle
import com.oneThing.first_matrch.Error
import com.oneThing.first_matrch.FirstMatchViewModel
import com.oneThing.first_matrch.UiError
import com.oneThing.first_matrch.component.JobGridSelector


@Composable
internal fun JobView(
    viewModel: FirstMatchViewModel,
    onBackClick: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    BackHandler(enabled = true) {
        onBackClick()
    }
    Column(
        modifier = Modifier
            .background(color = ColorStyle.WHITE_100)
            .fillMaxSize()
            .padding(
                top = 32.dp,
                start = 17.dp,
                end = 16.dp
            )
    ) {
        JobGridSelector(
            selectJobs = uiState.job,
            onJobToggle = { job -> viewModel.selectJob(job) }
        )
    }
}