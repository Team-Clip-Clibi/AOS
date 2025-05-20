package com.oneThing.first_matrch.ui.job

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core.AppTextStyles
import com.example.core.ButtonXXLPurple400
import com.example.core.ColorStyle
import com.oneThing.first_matrch.Error
import com.oneThing.first_matrch.FirstMatchViewModel
import com.oneThing.first_matrch.JOB
import com.oneThing.first_matrch.R
import com.oneThing.first_matrch.UiError
import com.oneThing.first_matrch.ui.JobGridSelector


@Composable
internal fun JobView(
    viewModel: FirstMatchViewModel,
    goNextPage: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val subTitleColor = when (val error = uiState.error) {
        is UiError.Error -> {
            when (Error.fromCode(error.message)) {
                Error.ERROR_TO_MANY_SELECT -> ColorStyle.RED_100
                else -> ColorStyle.GRAY_600
            }
        }

        else -> ColorStyle.GRAY_600
    }

    Scaffold(
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(bottom = 8.dp)
            ) {
                HorizontalDivider(
                    thickness = 1.dp,
                    color = ColorStyle.GRAY_200
                )
                Spacer(modifier = Modifier.height(8.dp))
                ButtonXXLPurple400(
                    onClick = goNextPage,
                    buttonText = stringResource(R.string.btn_next),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 17.dp),
                    isEnable = uiState.job.first() != JOB.NONE
                )
            }
        },
        contentColor = ColorStyle.WHITE_100
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding() + 32.dp,
                    bottom = paddingValues.calculateBottomPadding() + 10.dp,
                    start = 17.dp,
                    end = 16.dp
                )
        ) {
            Text(
                text = stringResource(R.string.txt_job_title),
                style = AppTextStyles.HEAD_28_40_BOLD,
                color = ColorStyle.GRAY_800,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = stringResource(R.string.txt_job_sub_title),
                style = AppTextStyles.SUBTITLE_16_24_SEMI,
                color = subTitleColor,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))
            JobGridSelector(
                selectJobs = uiState.job,
                onJobToggle = { job -> viewModel.selectJob(job) }
            )
        }
    }
}