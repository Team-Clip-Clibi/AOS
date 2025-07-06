package com.sungil.main.ui.myMatch

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.core.AppTextStyles
import com.example.core.ColorStyle
import com.example.core.CustomSnackBar
import com.sungil.domain.exception.UnauthorizedException
import com.sungil.domain.model.UserData
import com.sungil.editprofile.ERROR_NETWORK
import com.sungil.editprofile.ERROR_TOKEN_EXPIRE
import com.sungil.main.ERROR_NETWORK_ERROR
import com.sungil.main.LatestDayInfo
import com.sungil.main.MainViewModel
import com.sungil.main.MyMatchDestination
import com.sungil.main.R
import com.sungil.main.component.CustomMainPageTopBar
import com.sungil.main.ui.myMatch.matchHistory.MatchHistoryView
import com.sungil.main.ui.myMatch.matchNotice.MatchNoticeView
import com.sungil.onethingmatch.ERROR_RE_LOGIN

@Composable
fun MyMatchView(
    viewModel: MainViewModel,
    login: () -> Unit,
    guide: () -> Unit,
    matchDetail: () -> Unit,
    review:() -> Unit
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val state by viewModel.userState.collectAsState()
    val latestDay = state.latestDay
    val context = LocalContext.current
    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }
    LaunchedEffect(state.matchLate) {
        when (val result = state.matchLate) {
            is MainViewModel.UiState.Error -> {
                when (result.message) {
                    ERROR_NETWORK -> {
                        snackBarHostState.showSnackbar(
                            message = context.getString(R.string.msg_network_error),
                            duration = SnackbarDuration.Short
                        )
                    }

                    ERROR_TOKEN_EXPIRE -> {
                        snackBarHostState.showSnackbar(
                            message = context.getString(R.string.msg_re_login),
                            duration = SnackbarDuration.Short
                        )
                        login()
                    }
                }
            }

            else -> Unit
        }
    }

    val userName = when (state.userDataState) {
        is MainViewModel.UiState.Success -> (state.userDataState as MainViewModel.UiState.Success<UserData>).data.nickName
        is MainViewModel.UiState.Loading -> ""
        is MainViewModel.UiState.Error -> "오류 발생"
    }

    LaunchedEffect(latestDay) {
        when (latestDay) {
            is MainViewModel.UiState.Error -> {
                when (latestDay.message) {
                    ERROR_RE_LOGIN -> login()
                    ERROR_NETWORK_ERROR -> snackBarHostState.showSnackbar(
                        message = context.getString(R.string.msg_network_error),
                        duration = SnackbarDuration.Short
                    )
                }
            }

            else -> Unit
        }
    }

    val all = viewModel.matchAllData.collectAsLazyPagingItems()
    val applied = viewModel.matchApplied.collectAsLazyPagingItems()
    val confirmed = viewModel.matchConfirmed.collectAsLazyPagingItems()
    val complete = viewModel.matchComplete.collectAsLazyPagingItems()
    val cancelled = viewModel.matchCancelled.collectAsLazyPagingItems()
    val refreshStates = listOf(
        all.loadState.refresh,
        applied.loadState.refresh,
        confirmed.loadState.refresh,
        complete.loadState.refresh,
        cancelled.loadState.refresh
    )
    LaunchedEffect(refreshStates) {
        refreshStates.forEach { state ->
            if (state is LoadState.Error) {
                when (val e = state.error) {
                    is UnauthorizedException -> {
                        when (e.message) {
                            ERROR_NETWORK -> {
                                snackBarHostState.showSnackbar(
                                    message = context.getString(R.string.msg_network_error),
                                    duration = SnackbarDuration.Short
                                )
                            }

                            ERROR_TOKEN_EXPIRE -> {
                                snackBarHostState.showSnackbar(
                                    message = context.getString(R.string.msg_re_login),
                                    duration = SnackbarDuration.Short
                                )
                                login()
                            }

                            else -> {
                                snackBarHostState.showSnackbar(
                                    message = context.getString(R.string.msg_network_error),
                                    duration = SnackbarDuration.Short
                                )
                            }
                        }
                    }

                    else -> {
                        snackBarHostState.showSnackbar(
                            message = e.message ?: "ERROR",
                            duration = SnackbarDuration.Short
                        )
                    }
                }
                return@LaunchedEffect
            }
        }
    }

    LaunchedEffect(state.participants) {
        when (val result = state.participants) {
            is MainViewModel.UiState.Error -> {
                when (result.message) {
                    ERROR_RE_LOGIN -> {
                        snackBarHostState.showSnackbar(
                            message = context.getString(R.string.msg_re_login),
                            duration = SnackbarDuration.Short
                        )
                        login()
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
                review()
            }

            else -> Unit
        }
    }

    Scaffold(
        topBar = {
            CustomMainPageTopBar(text = stringResource(R.string.my_match_top_bar))
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
                        bottom = WindowInsets.navigationBars.asPaddingValues().calculateTopPadding()
                    )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding()
                )
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = ColorStyle.GRAY_200)
            ) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = ColorStyle.WHITE_100)
                            .padding(start = 17.dp, end = 16.dp)
                    ) {
                        MyMatchTitleView(latestDay = latestDay, userName = userName)
                        Spacer(modifier = Modifier.height(32.dp))
                    }
                }

                stickyHeader {
                    MyMatchTabLayout(
                        selectedTabIndex = selectedTabIndex,
                        onTabSelected = { selectedTabIndex = it }
                    )
                }

                item {
                    when (selectedTabIndex) {
                        0 -> {
                            MatchHistoryView(
                                viewModel = viewModel,
                                login = login,
                                matchDetail = matchDetail,
                                snackBarHostState = snackBarHostState
                            )
                        }

                        1 -> MatchNoticeView(viewModel = viewModel)
                    }
                }
            }

            Column(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 12.dp, end = 10.dp)
            ) {
                OneThingGuide(onClick = guide)
            }
        }
    }
}

@Composable
fun MyMatchTitleView(
    latestDay: MainViewModel.UiState<LatestDayInfo>,
    userName: String?,
) {
    val name = userName ?: "error"

    when (latestDay) {
        is MainViewModel.UiState.Error -> {
            Text(
                text = stringResource(R.string.my_match_title_not_approve, name),
                style = AppTextStyles.TITLE_20_28_SEMI,
                color = ColorStyle.GRAY_800
            )
            Spacer(modifier = Modifier.height(20.dp))
            MyMatchViewMatchInfo(
                applyInfo = 0,
                confirmInfo = 0
            )
        }

        is MainViewModel.UiState.Success -> {
            val apply = latestDay.data.applyTime
            val confirm = latestDay.data.confirmTime

            when {
                apply == 0 && confirm == 0 -> {
                    Text(
                        text = stringResource(R.string.my_match_title_not_apply),
                        style = AppTextStyles.TITLE_20_28_SEMI,
                        color = ColorStyle.GRAY_800
                    )
                }

                confirm == 0 -> {
                    Text(
                        text = stringResource(R.string.my_match_title_not_approve, name),
                        style = AppTextStyles.TITLE_20_28_SEMI,
                        color = ColorStyle.GRAY_800
                    )
                }

                else -> {
                    Text(
                        text = stringResource(R.string.my_match_title, name),
                        style = AppTextStyles.HEAD_28_40_BOLD,
                        color = ColorStyle.GRAY_800,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = stringResource(R.string.my_match_content, latestDay.data),
                        style = AppTextStyles.HEAD_28_40_BOLD,
                        color = ColorStyle.PURPLE_400,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            MyMatchViewMatchInfo(
                applyInfo = apply,
                confirmInfo = confirm
            )
        }

        else -> Unit
    }
}

@Composable
fun MyMatchViewMatchInfo(applyInfo: Int, confirmInfo: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(68.dp)
            .background(color = ColorStyle.GRAY_100, shape = RoundedCornerShape(size = 8.dp))
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.my_match_content_apply),
                style = AppTextStyles.CAPTION_12_18_SEMI,
                color = ColorStyle.GRAY_600,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Text(
                text = applyInfo.toString(),
                style = AppTextStyles.SUBTITLE_18_26_SEMI,
                color = ColorStyle.GRAY_800,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
        VerticalDivider(
            modifier = Modifier
                .width(1.dp)
                .fillMaxHeight()
                .background(color = ColorStyle.GRAY_300)
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.my_match_content_confirm),
                style = AppTextStyles.CAPTION_12_18_SEMI,
                color = ColorStyle.GRAY_600,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Text(
                text = confirmInfo.toString(),
                style = AppTextStyles.SUBTITLE_18_26_SEMI,
                color = ColorStyle.GRAY_800,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun MyMatchTabLayout(
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
) {
    val tabs = MyMatchDestination.entries

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = ColorStyle.WHITE_100)
    ) {
        ScrollableTabRow(
            selectedTabIndex = selectedTabIndex,
            edgePadding = 0.dp,
            containerColor = ColorStyle.WHITE_100,
            divider = {},
            indicator = { tabPositions ->
                SecondaryIndicator(
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    height = 3.dp,
                    color = ColorStyle.GRAY_800
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 17.dp)
        ) {
            tabs.forEachIndexed { index, tab ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { onTabSelected(index) },
                    text = {
                        Text(
                            text = tab.label,
                            style = AppTextStyles.SUBTITLE_16_24_SEMI,
                            color = if (selectedTabIndex == index)
                                ColorStyle.GRAY_800
                            else
                                ColorStyle.GRAY_600
                        )
                    }
                )
            }
        }

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = ColorStyle.GRAY_300
        )
    }
}

@Composable
fun OneThingGuide(
    onClick: () -> Unit,
) {
    Button(
        modifier = Modifier
            .width(134.dp)
            .height(48.dp),
        shape = RoundedCornerShape(size = 40.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = ColorStyle.GRAY_700,
            contentColor = ColorStyle.GRAY_700
        ),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 12.dp),
        onClick = onClick,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_pin),
                contentDescription = "oneThing guide",
                modifier = Modifier
                    .width(18.dp)
                    .height(18.dp),
                tint = ColorStyle.WHITE_100
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = stringResource(R.string.btn_meet_guide),
                style = AppTextStyles.BODY_14_20_MEDIUM,
                color = ColorStyle.WHITE_100
            )
        }
    }
}


