package com.sungil.alarm.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.wear.compose.material3.Text
import com.example.core.AppTextStyles
import com.sungil.alarm.AlarmViewModel
import com.sungil.alarm.R
import com.sungil.alarm.component.CustomNoticeList
import com.sungil.alarm.component.ERROR_RE_LOGIN
import com.sungil.alarm.component.ERROR_SAVE
import com.sungil.alarm.component.ERROR_SERVER

@Composable
internal fun AlarmMainView(
    viewModel: AlarmViewModel,
    paddingValue: PaddingValues,
    snackBarHost: SnackbarHostState,
    reLogin: () -> Unit,
) {
    val unReadNotify = viewModel.unReadNotify.collectAsLazyPagingItems()
    val readNotify = viewModel.readNotification.collectAsLazyPagingItems()
    val unReadState = unReadNotify.loadState.refresh
    val readState = readNotify.loadState.refresh
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        when (unReadState) {
            is LoadState.Error -> {
                when (unReadState.error.message) {
                    ERROR_SERVER -> {
                        snackBarHost.showSnackbar(
                            message = context.getString(R.string.msg_server_error),
                            duration = SnackbarDuration.Short
                        )
                    }

                    ERROR_SAVE -> {
                        snackBarHost.showSnackbar(
                            message = context.getString(R.string.msg_app_error),
                            duration = SnackbarDuration.Short
                        )
                    }

                    ERROR_RE_LOGIN -> {
                        reLogin()
                    }
                }
            }

            else -> Unit
        }
    }
    LaunchedEffect(Unit) {
        when (readState) {
            is LoadState.Error -> {
                when (readState.error.message) {
                    ERROR_SERVER -> {
                        snackBarHost.showSnackbar(
                            message = context.getString(R.string.msg_server_error),
                            duration = SnackbarDuration.Short
                        )
                    }

                    ERROR_SAVE -> {
                        snackBarHost.showSnackbar(
                            message = context.getString(R.string.msg_app_error),
                            duration = SnackbarDuration.Short
                        )
                    }

                    ERROR_RE_LOGIN -> {
                        reLogin()
                    }
                }
            }

            else -> Unit
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFEFEFEF))
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValue.calculateTopPadding() + 24.dp,
                    start = 24.dp, end = 24.dp
                )
        ) {
            Text(
                text = stringResource(R.string.txt_title_new_alarm),
                style = AppTextStyles.TITLE_20_28_SEMI,
                color = Color(0xFF171717)
            )
            CustomNoticeList(
                pagingItems = unReadNotify,
                height = 200.dp
            )

            Spacer(Modifier.height(24.dp))

            Text(
                text = stringResource(R.string.txt_title_read_alarm),
                style = AppTextStyles.TITLE_20_28_SEMI,
                color = Color(0xFF171717)
            )

            CustomNoticeList(
                pagingItems = readNotify,
                height = 1f.dp
            )
        }
    }
}