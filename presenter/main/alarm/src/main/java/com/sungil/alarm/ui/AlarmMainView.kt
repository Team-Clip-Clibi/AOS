package com.sungil.alarm.ui

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.core.AppTextStyles
import com.sungil.alarm.AlarmViewModel
import com.sungil.alarm.R
import com.sungil.alarm.component.CustomNoticeList
import com.sungil.alarm.component.ERROR_RE_LOGIN
import com.sungil.alarm.component.ERROR_SAVE
import com.sungil.alarm.component.ERROR_SERVER
import com.sungil.domain.model.Notification
import androidx.compose.material3.Text
@Composable
internal fun AlarmMainView(
    viewModel: AlarmViewModel,
    paddingValue: PaddingValues,
    snackBarHost: SnackbarHostState,
    reLogin: () -> Unit,
) {
    val context = LocalContext.current
    val unReadNotify = viewModel.unReadNotificationPagingFlow.collectAsLazyPagingItems()
    val readNotify = viewModel.readNotificationPagingFlow.collectAsLazyPagingItems()
    val isEmptyAlarm = viewModel.isAlarmEmpty.collectAsState()

    LaunchedEffect(unReadNotify.loadState.refresh) {
        handleLoadError(
            loadState = unReadNotify.loadState.refresh,
            context = context,
            snackBarHost = snackBarHost,
            reLogin = reLogin
        )
    }

    LaunchedEffect(readNotify.loadState.refresh) {
        handleLoadError(
            loadState = readNotify.loadState.refresh,
            context = context,
            snackBarHost = snackBarHost,
            reLogin = reLogin
        )
    }

    viewModel.setAlarmIsEmpty(
        unReadNotify.itemCount == 0 && readNotify.itemCount == 0
    )

    if (isEmptyAlarm.value) {
        EmptyAlarmScreen()
    } else {
        AlarmContent(
            paddingValue = paddingValue,
            unReadNotify = unReadNotify,
            readNotify = readNotify
        )
    }
}

@Composable
private fun EmptyAlarmScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .background(Color(0xFFF7F7F7), shape = CircleShape)
                .border(1.dp, Color(0xFFDCDCDC), shape = CircleShape)
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_notify_gray),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(Modifier.height(12.dp))

        Text(
            text = stringResource(R.string.txt_empty_alarm),
            style = AppTextStyles.BODY_14_20_MEDIUM,
            color = Color(0xFF989898),
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun AlarmContent(
    paddingValue: PaddingValues,
    unReadNotify: LazyPagingItems<Notification>,
    readNotify: LazyPagingItems<Notification>
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValue.calculateTopPadding() + 24.dp)
        ) {
            if (unReadNotify.itemCount > 0) {
                SectionTitle(R.string.txt_title_new_alarm)

                CustomNoticeList(
                    pagingItems = unReadNotify,
                    modifier = if (readNotify.itemCount == 0) {
                        Modifier.weight(1f)
                    } else {
                        Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    }
                )

                Spacer(Modifier.height(24.dp))
            }

            if (readNotify.itemCount > 0) {
                SectionTitle(R.string.txt_title_read_alarm)

                CustomNoticeList(
                    pagingItems = readNotify,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun SectionTitle(@StringRes titleRes: Int) {
    Text(
        text = stringResource(id = titleRes),
        style = AppTextStyles.TITLE_20_28_SEMI,
        color = Color(0xFF171717),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    )
}

private suspend fun handleLoadError(
    loadState: LoadState,
    context: Context,
    snackBarHost: SnackbarHostState,
    reLogin: () -> Unit
) {
    if (loadState is LoadState.Error) {
        when (loadState.error.message) {
            ERROR_SERVER -> snackBarHost.showSnackbar(
                message = context.getString(R.string.msg_server_error),
                duration = SnackbarDuration.Short
            )
            ERROR_SAVE -> snackBarHost.showSnackbar(
                message = context.getString(R.string.msg_app_error),
                duration = SnackbarDuration.Short
            )
            ERROR_RE_LOGIN -> reLogin()
        }
    }
}
