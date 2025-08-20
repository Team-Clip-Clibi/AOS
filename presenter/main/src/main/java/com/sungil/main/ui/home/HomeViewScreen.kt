package com.sungil.main.ui.home

import android.app.Activity
import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.core.ColorStyle
import com.sungil.domain.model.MatchInfo
import com.sungil.domain.model.NotificationData
import com.sungil.main.BuildConfig
import com.sungil.main.ERROR_NETWORK_ERROR
import com.sungil.main.ERROR_RE_LOGIN
import com.sungil.main.ERROR_SAVE_ERROR
import com.sungil.main.MainViewModel
import com.sungil.main.R
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import com.example.core.AppTextStyles
import com.sungil.domain.model.BannerData
import com.sungil.domain.model.MatchData
import com.sungil.domain.model.UserData
import com.sungil.main.component.AutoSlidingBanner
import com.sungil.main.component.CustomHomeButton
import com.sungil.main.component.MeetingCardList
import com.sungil.main.component.NoticeBar
import kotlinx.coroutines.launch

@Composable
internal fun HomeViewScreen(
    viewModel: MainViewModel,
    snackBarHostState: SnackbarHostState,
    oneThingClick: () -> Unit,
    firstMatchClick: (String) -> Unit,
    notifyClick: (String) -> Unit,
    randomMatchClick: () -> Unit,
    reLogin: () -> Unit,
) {
    val context = LocalContext.current
    val state by viewModel.userState.collectAsState()
    val notificationState = state.notificationResponseState
    var lastBackPressed by remember { mutableLongStateOf(0L) }
    val scope = rememberCoroutineScope()
    BackHandler {
        val now = System.currentTimeMillis()
        if (now - lastBackPressed <= 2000L) {
            (context as? Activity)?.finish()
        } else {
            lastBackPressed = now
            scope.launch {
                snackBarHostState.currentSnackbarData?.dismiss()
                snackBarHostState.showSnackbar(
                    message = context.getString(R.string.app_finish),
                    duration = SnackbarDuration.Short
                )
            }
        }
    }
    HandleNotifyError(
        notificationState = notificationState,
        snackBarHost = snackBarHostState,
        context = context,
        reLogin = reLogin
    )
    val firstMatch = state.firstMatch
    HandleMatchClick(
        firstMatch = firstMatch,
        oneThingClick = oneThingClick,
        randomClick = randomMatchClick,
        firstMatchClick = firstMatchClick
    )
    val userData = state.userDataState
    val matchState = state.matchState
    val banner = state.banner
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = ColorStyle.GRAY_100)
            .verticalScroll(rememberScrollState())
    ) {
        NotifyView(notificationState = notificationState, notifyClick = notifyClick)
        MatchView(
            userData = userData,
            matchState = matchState,
            context = context,
            reLogin = reLogin,
            snackBarHost = snackBarHostState,
        )
        MatchButtonView(
            randomMatchClick = randomMatchClick,
            viewModel = viewModel
        )
        BannerView(banner = banner)
    }
}

@Composable
private fun NotifyView(
    notificationState: MainViewModel.UiState<List<NotificationData>>,
    notifyClick: (String) -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        if (notificationState is MainViewModel.UiState.Success && notificationState.data.isNotEmpty()) {
            NoticeBar(
                notification = notificationState.data,
                onClick = { notifyClick(notificationState.data.first().link) },
            )
        }
    }
}

@Composable
private fun MatchView(
    userData: MainViewModel.UiState<UserData>,
    matchState: MainViewModel.UiState<MatchData>,
    reLogin: () -> Unit,
    snackBarHost: SnackbarHostState,
    context: Context
) {
    val visibleCards = remember { mutableStateListOf<MatchInfo>() }
    LaunchedEffect(matchState) {
        when (matchState) {
            is MainViewModel.UiState.Error -> {
                when (matchState.message) {
                    ERROR_RE_LOGIN -> {
                        reLogin()
                    }

                    ERROR_SAVE_ERROR -> snackBarHost.showSnackbar(
                        message = context.getString(R.string.msg_save_error),
                        duration = SnackbarDuration.Short
                    )

                    ERROR_NETWORK_ERROR -> snackBarHost.showSnackbar(
                        message = context.getString(R.string.msg_network_error),
                        duration = SnackbarDuration.Short
                    )
                }
            }

            is MainViewModel.UiState.Success -> {
                val data = matchState.data
                visibleCards.clear()
                visibleCards.addAll(data.oneThingMatch)
                visibleCards.addAll(data.randomMatch)
            }

            else -> Unit
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.padding(start = 17.dp, end = 16.dp)
        ) {
            Text(
                text = if (userData is MainViewModel.UiState.Success) stringResource(
                    R.string.txt_home_title,
                    userData.data.nickName ?: "error"
                ) else stringResource(R.string.txt_home_title, "ERROR"),
                style = AppTextStyles.TITLE_20_28_SEMI,
                color = ColorStyle.GRAY_700
            )
            if (visibleCards.size != 0) {
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = visibleCards.size.toString(),
                    style = AppTextStyles.TITLE_20_28_SEMI,
                    color = ColorStyle.PURPLE_400
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        MeetingCardList(
            matchList = visibleCards,
            onAddClick = {},
            canAdd = true
        )
    }
}

@Composable
private fun MatchButtonView(
    viewModel: MainViewModel,
    randomMatchClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp, start = 17.dp, end = 16.dp)
    ) {
        Text(
            text = stringResource(R.string.txt_home_sub_title),
            style = AppTextStyles.TITLE_20_28_SEMI,
            color = ColorStyle.GRAY_700
        )
        Spacer(modifier = Modifier.height(12.dp))
        CustomHomeButton(
            titleText = stringResource(R.string.btn_home_oneThing),
            contentText = stringResource(R.string.btn_home_oneThing_content),
            onClick = {
                viewModel.checkFirstMatch(BuildConfig.ONE_THING)
            },
            image = R.drawable.ic_one_thing_match,
            modifier = Modifier
                .fillMaxWidth(),
            padding = Modifier.width(16.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CustomHomeButton(
                titleText = stringResource(R.string.btn_home_random),
                contentText = stringResource(R.string.btn_home_random_content),
                onClick = randomMatchClick,
                image = R.drawable.ic_random_match,
                modifier = Modifier
                    .weight(1f)
                    .height(83.dp),
                padding = Modifier.width(12.dp)
            )
            CustomHomeButton(
                titleText = stringResource(R.string.btn_home_light),
                contentText = stringResource(R.string.btn_home_light_content),
                onClick = {
                    viewModel.showLightDialog()
                },
                image = R.drawable.ic_lighting_match,
                modifier = Modifier
                    .weight(1f)
                    .height(83.dp),
                padding = Modifier.width(12.dp)
            )
        }
    }
}

@Composable
private fun BannerView(banner: MainViewModel.UiState<List<BannerData>>) {
    if (banner is MainViewModel.UiState.Success) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp, start = 17.dp, end = 16.dp, bottom = 32.dp)
        ) {
            AutoSlidingBanner(
                image = banner.data
            )
        }
    }
}

@Composable
private fun HandleNotifyError(
    notificationState: MainViewModel.UiState<List<NotificationData>>,
    snackBarHost: SnackbarHostState,
    context: Context,
    reLogin: () -> Unit,
) {
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
}

@Composable
private fun HandleMatchClick(
    firstMatch: MainViewModel.UiState<String>,
    oneThingClick: () -> Unit,
    randomClick: () -> Unit,
    firstMatchClick: (String) -> Unit,
) {
    LaunchedEffect(firstMatch) {
        when (firstMatch) {
            is MainViewModel.UiState.Error -> {
                firstMatchClick(firstMatch.message)
            }

            is MainViewModel.UiState.Success -> {
                when (firstMatch.data) {
                    BuildConfig.ONE_THING -> oneThingClick()
                    BuildConfig.RANDOM -> randomClick()
                }
            }

            else -> Unit
        }
    }
}