package com.sungil.main.ui.home

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sungil.main.MainViewModel
import com.sungil.main.R
import com.sungil.main.component.CustomHomeTopBar
import com.sungil.main.component.CustomSnackBar

@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    alarmClick: () -> Unit,
    notifyClick: (String) -> Unit,
    oneThingMatchClick: () -> Unit,
    randomMatchClick: () -> Unit,
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val alarmState by viewModel.userState.collectAsState()
    val icons = when (val state = alarmState.oneThingState) {
        is MainViewModel.UiState.Success ->
            state.data.takeIf { it.isNotEmpty() }
                ?.let { R.drawable.ic_bell_signal }
                ?: R.drawable.ic_bell

        else -> R.drawable.ic_bell
    }
    Scaffold(
        topBar = {
            CustomHomeTopBar(
                text = "",
                bellImage = icons,
                click = alarmClick
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
                    .padding(start = 17.dp, end = 16.dp, bottom = WindowInsets.navigationBars.asPaddingValues().calculateTopPadding() + 16.dp)
            )
        }
    ) { paddingValues ->
        HomMainScreen(
            paddingValues = paddingValues,
            viewModel = viewModel,
            snackBarHost = snackBarHostState,
            onThingClick = oneThingMatchClick,
            notifyClick = notifyClick,
            randomMatchClick = randomMatchClick,
            reLogin = {}
        )
    }
}