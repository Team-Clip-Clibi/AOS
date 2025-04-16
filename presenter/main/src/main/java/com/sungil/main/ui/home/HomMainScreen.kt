package com.sungil.main.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.sungil.domain.model.Notification
import com.sungil.editprofile.ERROR_USER_DATA_NULL
import com.sungil.main.ERROR_NETWORK_ERROR
import com.sungil.main.ERROR_RE_LOGIN
import com.sungil.main.ERROR_SAVE_ERROR
import com.sungil.main.MainViewModel
import com.sungil.main.R
import com.sungil.main.component.CustomNotifyBar
import com.sungil.main.component.HomeTitleText

@Composable
internal fun HomMainScreen(
    paddingValues: PaddingValues,
    viewModel: MainViewModel,
    snackBarHost: SnackbarHostState,
    onThingClick: () -> Unit,
    notifyClick: (String) -> Unit,
    randomMatchClick: () -> Unit,
    reLogin: () -> Unit,
) {
    val state by viewModel.userState.collectAsState()
    val notificationState = state.notificationState
    val userData = state.userDataState
    val context = LocalContext.current
    val notifyShow by viewModel.notifyShow.collectAsState()
    LaunchedEffect(notificationState) {
        if (notificationState is MainViewModel.UiState.Error) {
            when (notificationState.message) {
                ERROR_SAVE_ERROR -> snackBarHost.showSnackbar(
                    message = context.getString(R.string.msg_save_error),
                    duration = SnackbarDuration.Short
                )

                ERROR_NETWORK_ERROR -> snackBarHost.showSnackbar(
                    message = context.getString(R.string.msg_network_error),
                    duration = SnackbarDuration.Short
                )

                ERROR_RE_LOGIN -> {
                    reLogin()
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = paddingValues.calculateTopPadding(),
                bottom = 40.dp
            )
            .background(Color(0xFFF7F7F7))
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState())
    ) {
        // 상단 알림 영역 (padding 없음)
        if (notificationState is MainViewModel.UiState.Success && notifyShow) {
            val data = notificationState.data
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopStart)
            ) {
                CustomNotifyBar(
                    noticeType = data.noticeType,
                    content = data.content,
                    link = data.link,
                    notifyClick = { notifyClick(data.link) },
                    notifyClose = { viewModel.setNotifyShow(false) }
                )
            }
        }

        // 메인 콘텐츠 영역 (padding 있음)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopStart)
                .padding(top = if (notificationState is MainViewModel.UiState.Success && notifyShow) 32.dp else 0.dp)
                .padding(start = 17.dp, end = 16.dp)
        ) {

            HomeTitleText(
                text = if (userData is MainViewModel.UiState.Success) userData.data.nickName else "error"
            )
            Spacer(Modifier.height(12.dp))

        }
    }
}