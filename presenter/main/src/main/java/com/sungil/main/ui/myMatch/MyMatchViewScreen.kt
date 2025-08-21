package com.sungil.main.ui.myMatch

import android.app.Activity
import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.sungil.editprofile.ERROR_NETWORK
import com.sungil.editprofile.ERROR_TOKEN_EXPIRE
import com.sungil.main.MainViewModel
import com.sungil.main.R
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.core.ColorStyle
import androidx.compose.ui.unit.dp
import com.example.core.AppTextStyles
import com.sungil.domain.model.UserData
import com.sungil.main.LatestDayInfo
import com.sungil.main.MyMatchDestination
import com.sungil.main.PAGE_MATCH_HISTORY
import com.sungil.main.PAGE_MATCH_NOTICE
import com.sungil.main.ui.myMatch.matchHistory.MatchHistoryView
import com.sungil.main.ui.myMatch.matchNotice.MatchNoticeView
import kotlinx.coroutines.launch

@Composable
internal fun MyMatchViewScreen(
    viewModel: MainViewModel,
    login: () -> Unit,
    guide: () -> Unit,
    matchDetail: () -> Unit,
    review: (List<String> , Int , String) -> Unit,
    snackBarHostState: SnackbarHostState,
) {
    val state by viewModel.userState.collectAsState()
    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }
    val latestDay = state.latestDay
    val userName = state.userDataState
    val context = LocalContext.current
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
    HandleMatchLateError(
        state = state,
        context = context,
        snackBarHostState = snackBarHostState,
        login = login
    )
    HandleLateError(
        state = latestDay,
        context = context,
        login = login,
        snackBarHostState = snackBarHostState
    )
    HandleParticipants(
        context = context,
        state = state,
        login = login,
        review = review,
        snackBarHostState = snackBarHostState
    )
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(color = ColorStyle.GRAY_200)
        ) {
            item {
                TopView(state = latestDay, userName = userName)
            }
            stickyHeader {
                ButtonTabLayout(
                    selectedTabIndex = selectedTabIndex,
                    onTabSelected = { index ->
                        selectedTabIndex = index
                    }
                )
            }

            item {
                when (selectedTabIndex) {
                    PAGE_MATCH_HISTORY -> {
                        MatchHistoryView(
                            viewModel = viewModel,
                            login = login,
                            matchDetail = matchDetail,
                            snackBarHostState = snackBarHostState
                        )
                    }

                    PAGE_MATCH_NOTICE -> MatchNoticeView(viewModel = viewModel)
                }
            }
        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 12.dp, end = 10.dp)
        ) {
            GuideButton(onClick = guide)
        }
    }
}

@Composable
fun ButtonTabLayout(
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
private fun TopView(
    state: MainViewModel.UiState<LatestDayInfo>,
    userName: MainViewModel.UiState<UserData>,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = ColorStyle.WHITE_100)
            .padding(start = 17.dp, end = 16.dp, bottom = 32.dp)
    ) {
        MyMatchTitleView(latestDay = state, userInfo = userName)
    }
}

@Composable
private fun MyMatchTitleView(
    latestDay: MainViewModel.UiState<LatestDayInfo>,
    userInfo: MainViewModel.UiState<UserData>,
) {
    val userName = when (userInfo) {
        is MainViewModel.UiState.Success -> userInfo.data.nickName ?: "error"
        is MainViewModel.UiState.Loading -> ""
        is MainViewModel.UiState.Error -> "error"
    }

    when (latestDay) {
        is MainViewModel.UiState.Error -> {
            Text(
                text = stringResource(R.string.my_match_title_not_approve, userName),
                style = AppTextStyles.TITLE_20_28_SEMI,
                color = ColorStyle.GRAY_800
            )
            Spacer(modifier = Modifier.height(20.dp))
            MyMatchViewInfo(
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
                        text = stringResource(R.string.my_match_title_not_approve, userName),
                        style = AppTextStyles.TITLE_20_28_SEMI,
                        color = ColorStyle.GRAY_800
                    )
                }

                else -> {
                    Text(
                        text = stringResource(R.string.my_match_title, userName),
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
            MyMatchViewInfo(
                applyInfo = apply,
                confirmInfo = confirm
            )
        }

        else -> Unit
    }
}

@Composable
fun GuideButton(
    onClick: () -> Unit,
) {
    Button(
        modifier = Modifier
            .width(137.dp)
            .height(48.dp),
        shape = RoundedCornerShape(size = 40.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = ColorStyle.GRAY_700,
            contentColor = ColorStyle.GRAY_700
        ),
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp),
        onClick = onClick,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center  ,
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
                color = ColorStyle.WHITE_100,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun MyMatchViewInfo(applyInfo: Int, confirmInfo: Int) {
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
private fun HandleMatchLateError(
    state: MainViewModel.MainViewState,
    context: Context,
    snackBarHostState: SnackbarHostState,
    login: () -> Unit,
) {
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
}

@Composable
private fun HandleLateError(
    state: MainViewModel.UiState<LatestDayInfo>,
    context: Context,
    snackBarHostState: SnackbarHostState,
    login: () -> Unit,
) {
    LaunchedEffect(state) {
        when (state) {
            is MainViewModel.UiState.Error -> {
                when (state.message) {
                    ERROR_TOKEN_EXPIRE -> {
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

            else -> Unit
        }
    }
}

@Composable
private fun HandleParticipants(
    state: MainViewModel.MainViewState,
    context: Context,
    snackBarHostState: SnackbarHostState,
    login: () -> Unit,
    review: (List<String> , Int , String) -> Unit,
) {
    LaunchedEffect(state.participants) {
        when (val result = state.participants) {
            is MainViewModel.UiState.Error -> {
                when (result.message) {
                    ERROR_TOKEN_EXPIRE -> {
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
                review(
                    result.data.person.map { data -> data.nickName },
                    result.data.matchId,
                    result.data.matchType
                )
            }

            else -> Unit
        }
    }
}