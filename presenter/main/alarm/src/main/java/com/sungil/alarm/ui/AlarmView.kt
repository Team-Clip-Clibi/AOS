package com.sungil.alarm.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core.CustomSnackBar
import com.sungil.alarm.AlarmViewModel
import com.sungil.alarm.R
import com.sungil.alarm.component.CustomTopBar


@Composable
internal fun AlarmView(
    viewModel: AlarmViewModel,
    backClick: () -> Unit,
    reLogin : () -> Unit
) {
    val snackBarHostState = remember { SnackbarHostState() }
    Scaffold(
        topBar = {
            CustomTopBar(
                title = stringResource(R.string.top_bar_alarm),
                onBackClick = backClick
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
                    .padding(
                        start = 17.dp,
                        end = 16.dp,
                        bottom = WindowInsets.navigationBars
                            .asPaddingValues()
                            .calculateTopPadding() + 16.dp
                    )
            )
        }
    ) { paddingValues ->
        AlarmMainView(
            viewModel = viewModel,
            paddingValue = paddingValues,
            snackBarHost = snackBarHostState,
            reLogin = reLogin
        )
    }
}