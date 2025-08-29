package com.clip.low

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
import com.clip.low.ui.CustomSnackBar
import com.clip.low.ui.CustomTopBar
import com.clip.low.ui.low.LowMainView

@Composable
fun LowView(
    onBackClick: () -> Unit,
) {
    val snackBarHostState = remember { SnackbarHostState() }
    BackHandler(enabled = true) {
        onBackClick()
    }
    Scaffold(
        topBar = {
            CustomTopBar(
                title = stringResource(R.string.top_low),
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
        LowMainView(paddingValues)
    }
}