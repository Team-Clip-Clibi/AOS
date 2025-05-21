package com.sungil.main.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sungil.domain.model.MatchInfo
import com.sungil.main.BuildConfig
import com.sungil.main.ERROR_NETWORK_ERROR
import com.sungil.main.ERROR_RE_LOGIN
import com.sungil.main.ERROR_SAVE_ERROR
import com.sungil.main.MainViewModel
import com.sungil.main.R
import com.sungil.main.component.AutoSlidingBanner
import com.sungil.main.component.CustomHomeButton
import com.sungil.main.component.HomeTitleText
import com.sungil.main.component.MeetingCardList
import com.sungil.main.component.NotificationBarListStable
import kotlinx.coroutines.launch

@Composable
internal fun HomMainScreen(
    paddingValues: PaddingValues,
    viewModel: MainViewModel,
    snackBarHost: SnackbarHostState,
    onThingClick: () -> Unit,
    firstMatchClick : (String) -> Unit,
    notifyClick: (String) -> Unit,
    randomMatchClick: () -> Unit,
    reLogin: () -> Unit,
) {

    val visibleCards = remember { mutableStateListOf<MatchInfo>() }
    val coroutineScope = rememberCoroutineScope()
    val state by viewModel.userState.collectAsState()
    val notificationState = state.notificationResponseState
    val firstMatch = state.firstMatch
    val userData = state.userDataState
    val context = LocalContext.current
    val matchState = state.matchState
    val notServiceMsg = stringResource(R.string.msg_not_service)
    val bannerImage = state.banner
    val notifyBarHeight = if (notificationState is MainViewModel.UiState.Success) 34.dp else 0.dp

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

    LaunchedEffect(firstMatch) {
        when(firstMatch){
            is MainViewModel.UiState.Success ->{
                when(firstMatch.data){
                    BuildConfig.ONE_THING -> {
                        onThingClick()
                    }
                    BuildConfig.RANDOM ->{

                    }
                }
            }
            is MainViewModel.UiState.Error ->{
                firstMatchClick(firstMatch.message)
            }

            else -> Unit
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F7F7))
            .padding(top = paddingValues.calculateTopPadding())
            .navigationBarsPadding()
    ) {
        if (notificationState is MainViewModel.UiState.Success) {
            val data = notificationState.data
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopStart)
            ) {
                NotificationBarListStable(
                    notifications = data,
                    notifyClick = { notifyClick(data.first().link) },
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopStart)
                .padding(top = paddingValues.calculateTopPadding() + notifyBarHeight , bottom = 40.dp)
                .verticalScroll(rememberScrollState())
        ) {
            HomeTitleText(
                text = if (userData is MainViewModel.UiState.Success)
                    stringResource(R.string.txt_home_title, userData.data.nickName ?: "error")
                else stringResource(R.string.txt_home_title, "ERROR"),
                size = visibleCards.size.toString()
            )
            Spacer(Modifier.height(12.dp))

            MeetingCardList(
                matchList = visibleCards,
                onAddClick = {},
                canAdd = true
            )

            Spacer(Modifier.height(40.dp))

            HomeTitleText(text = stringResource(R.string.txt_home_sub_title))

            Spacer(Modifier.height(12.dp))

            CustomHomeButton(
                titleText = stringResource(R.string.btn_home_oneThing),
                contentText = stringResource(R.string.btn_home_oneThing_content),
                onClick = {
                    viewModel.checkFirstMatch(BuildConfig.ONE_THING)
                },
                image = R.drawable.ic_one_thing_purple,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 17.dp, end = 16.dp),
                padding = Modifier.width(16.dp)
            )

            Spacer(Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                CustomHomeButton(
                    titleText = stringResource(R.string.btn_home_random),
                    contentText = stringResource(R.string.btn_home_random_content),
                    onClick = randomMatchClick,
                    image = R.drawable.ic_random_green,
                    modifier = Modifier
                        .weight(1f)
                        .height(83.dp)
                        .padding(start = 17.dp),
                    padding = Modifier.width(12.dp)
                )
                CustomHomeButton(
                    titleText = stringResource(R.string.btn_home_light),
                    contentText = stringResource(R.string.btn_home_light_content),
                    onClick = {
                        coroutineScope.launch {
                            snackBarHost.showSnackbar(
                                message = notServiceMsg,
                                duration = SnackbarDuration.Short
                            )
                        }
                    },
                    image = R.drawable.ic_light_yellow,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 16.dp)
                        .height(83.dp),
                    padding = Modifier.width(12.dp)
                )
            }

            Spacer(Modifier.height(32.dp))

            if (bannerImage is MainViewModel.UiState.Success) {
                AutoSlidingBanner(
                    image = bannerImage.data,
                )
            }
        }
    }
}