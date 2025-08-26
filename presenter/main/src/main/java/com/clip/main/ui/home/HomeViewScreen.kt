package com.clip.main.ui.home

import android.app.Activity
import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
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
import com.clip.core.ColorStyle
import com.clip.domain.model.MatchInfo
import com.clip.domain.model.NotificationData
import com.clip.main.BuildConfig
import com.clip.main.ERROR_NETWORK_ERROR
import com.clip.main.ERROR_RE_LOGIN
import com.clip.main.ERROR_SAVE_ERROR
import com.clip.main.MainViewModel
import com.clip.main.R
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import com.clip.core.AppTextStyles
import com.clip.core.CustomDialogTwoButton
import com.clip.domain.model.BannerData
import com.clip.domain.model.HomeBanner
import com.clip.domain.model.MatchData
import com.clip.domain.model.NotWriteReview
import com.clip.domain.model.Participants
import com.clip.domain.model.UserData
import com.clip.editprofile.ERROR_NETWORK
import com.clip.editprofile.ERROR_TOKEN_EXPIRE
import com.clip.main.MatchType
import com.clip.main.Participant
import com.clip.main.component.AutoSlidingBanner
import com.clip.main.component.CustomHomeButton
import com.clip.main.component.HomeBannerUi
import com.clip.main.component.MeetingCardList
import com.clip.main.component.NoticeBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun HomeViewScreen(
    viewModel: MainViewModel,
    snackBarHostState: SnackbarHostState,
    oneThingClick: () -> Unit,
    firstMatchClick: (String) -> Unit,
    notifyClick: (String) -> Unit,
    randomMatchClick: () -> Unit,
    reLogin: () -> Unit,
    goMatchView: () -> Unit,
    review: (Int , String , List<Participants>) -> Unit
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
    val homeBanner = state.homeBanner
    val notWriteReview = state.notWriteReview
    val participant = state.participants
    val reviewLater = state.writeReviewLater
    val dialogInfo by viewModel.dialogIndex.collectAsState()
    val dialogShow by viewModel.actionInFlight.collectAsState()
    val refresh by viewModel.refreshValue.collectAsState()
    val pullState = rememberPullRefreshState(
        refreshing = refresh,
        onRefresh = { viewModel.refreshData() }
    )
    Box(
        modifier = Modifier.fillMaxSize()
            .pullRefresh(pullState)
            .background(color = ColorStyle.GRAY_100)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            NotifyView(notificationState = notificationState, notifyClick = notifyClick)
            HomeBannerView(
                homeBanner = homeBanner,
                viewModel = viewModel,
                snackBarHost = snackBarHostState,
                context = context,
                reLogin = reLogin,
                goMatchView = goMatchView
            )
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
            DialogNotWriteReview(
                notWriteReview = notWriteReview,
                context = context,
                snackBarHostState = snackBarHostState,
                viewModel = viewModel,
                review = review,
                participant = participant,
                reLogin = reLogin,
                index = dialogInfo,
                reviewLater = reviewLater,
                dialogShow = dialogShow
            )
        }
        PullRefreshIndicator(
            refreshing = refresh,
            state = pullState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
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
private fun HomeBannerView(
    homeBanner: MainViewModel.UiState<List<HomeBanner>>,
    viewModel: MainViewModel,
    snackBarHost: SnackbarHostState,
    context: Context,
    reLogin: () -> Unit,
    goMatchView : () -> Unit
) {
    LaunchedEffect(homeBanner) {
        when (homeBanner) {
            is MainViewModel.UiState.Error -> {
                when (homeBanner.message) {
                    ERROR_RE_LOGIN -> {
                        reLogin()
                    }

                    ERROR_NETWORK_ERROR -> {
                        snackBarHost.showSnackbar(
                            message = context.getString(R.string.msg_network_error),
                            duration = SnackbarDuration.Short
                        )
                    }

                    else -> snackBarHost.showSnackbar(
                        message = homeBanner.message,
                        duration = SnackbarDuration.Short
                    )
                }

            }

            else -> Unit
        }
    }
    if (homeBanner is MainViewModel.UiState.Success && homeBanner.data.isNotEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 32.dp, bottom = 8.dp, start = 17.dp, end = 16.dp)
        ) {
            HomeBannerUi(
                data = homeBanner.data,
                closePopUp = { bannerId ->
                    viewModel.removeHomeBannerData(bannerId)
                },
                goMyMatch = {
                    goMatchView()
                }
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
    context: Context,
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

@Composable
private fun DialogNotWriteReview(
    notWriteReview: MainViewModel.UiState<ArrayList<NotWriteReview>>,
    participant: MainViewModel.UiState<Participant>,
    reviewLater: MainViewModel.UiState<String>,
    context: Context,
    snackBarHostState: SnackbarHostState,
    viewModel: MainViewModel,
    reLogin: () -> Unit,
    index: Int,
    review: (Int, String, List<Participants>) -> Unit,
    dialogShow : Boolean
) {
    LaunchedEffect(notWriteReview) {
        when (notWriteReview) {
            is MainViewModel.UiState.Error -> {
                when (notWriteReview.message) {
                    ERROR_RE_LOGIN -> {
                        reLogin()
                    }

                    ERROR_NETWORK_ERROR -> {
                        snackBarHostState.showSnackbar(
                            message = context.getString(R.string.msg_network_error),
                            duration = SnackbarDuration.Short
                        )
                    }

                    else -> snackBarHostState.showSnackbar(
                        message = notWriteReview.message,
                        duration = SnackbarDuration.Short
                    )
                }
            }

            else -> Unit
        }
    }
    LaunchedEffect(participant) {
        when (participant) {
            is MainViewModel.UiState.Error -> {
                when (participant.message) {
                    ERROR_TOKEN_EXPIRE -> {
                        snackBarHostState.showSnackbar(
                            message = context.getString(R.string.msg_re_login),
                            duration = SnackbarDuration.Short
                        )
                        reLogin()
                    }

                    ERROR_NETWORK -> {
                        snackBarHostState.showSnackbar(
                            message = context.getString(R.string.msg_network_error),
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            }

            is MainViewModel.UiState.Success -> {
                viewModel.plusHomeBanner()
                review(
                    participant.data.matchId,
                    participant.data.matchType,
                    participant.data.person
                )
            }

            else -> Unit
        }
    }

    LaunchedEffect(reviewLater) {
        when (reviewLater) {
            is MainViewModel.UiState.Error -> {
                /**
                 * TODO  배포 시 삭제
                 */
                viewModel.plusHomeBanner()
                when (reviewLater.message) {
                    ERROR_TOKEN_EXPIRE -> {
                        snackBarHostState.showSnackbar(
                            message = context.getString(R.string.msg_re_login),
                            duration = SnackbarDuration.Short
                        )
                        reLogin()
                    }

                    ERROR_NETWORK -> {
                        snackBarHostState.showSnackbar(
                            message = context.getString(R.string.msg_network_error),
                            duration = SnackbarDuration.Short
                        )
                    }
                }
                viewModel.initReviewLater()
            }

            is MainViewModel.UiState.Success -> {
                viewModel.plusHomeBanner()
            }

            else -> Unit
        }
    }
    if (notWriteReview is MainViewModel.UiState.Success && notWriteReview.data.isNotEmpty() && dialogShow) {
        CustomDialogTwoButton(
            onDismiss = {
                viewModel.reviewLater(
                    matchId = notWriteReview.data[index].id,
                    matchType = notWriteReview.data[index].type
                )
            },
            buttonClick = {
                viewModel.getParticipants(
                    matchId = notWriteReview.data[index].id,
                    matchType = notWriteReview.data[index].type
                )
            },
            titleText = stringResource(R.string.review_dialog_title),
            contentText = "${notWriteReview.data[index].simpleTime}일 ${
                MatchType.fromRoute(
                    notWriteReview.data[index].type
                ).matchType
            }",
            buttonText = stringResource(R.string.review_dialog_btn_okay),
            dismissButtonText = stringResource(R.string.review_dialog_btn_cancel)
        )
    }
}