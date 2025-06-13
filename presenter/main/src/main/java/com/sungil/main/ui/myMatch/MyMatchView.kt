package com.sungil.main.ui.myMatch

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.core.AppTextStyles
import com.example.core.ColorStyle
import com.example.core.CustomSnackBar
import com.sungil.domain.model.UserData
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
fun MyMatchView(viewModel: MainViewModel, login: () -> Unit) {
    val snackBarHostState = remember { SnackbarHostState() }
    val state by viewModel.userState.collectAsState()
    val latestDay = state.latestDay
    val context = LocalContext.current
    val navController = rememberNavController()
    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }
    val userName = when (state.userDataState) {
        is MainViewModel.UiState.Success -> (state.userDataState as MainViewModel.UiState.Success<UserData>).data.nickName
        is MainViewModel.UiState.Loading -> ""
        is MainViewModel.UiState.Error -> "오류 발생"
    }
    LaunchedEffect(latestDay) {
        when (latestDay) {
            is MainViewModel.UiState.Error -> {
                when (latestDay.message) {
                    ERROR_RE_LOGIN -> {
                        login()
                    }

                    ERROR_NETWORK_ERROR -> {
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
                        bottom = WindowInsets.navigationBars
                            .asPaddingValues()
                            .calculateTopPadding()
                    )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding()
                )
        ) {
            //Top View
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = ColorStyle.WHITE_100)
                    .padding(top = 16.dp, start = 17.dp, end = 16.dp)
            ) {
                MyMatchTitleView(latestDay = latestDay, userName = userName)
                Spacer(modifier = Modifier.height(32.dp))
            }
            //tab layout
            MyMatchTabLayout(
                selectedTabIndex = selectedTabIndex,
                onTabSelected = { index ->
                    selectedTabIndex = index
                    val route = MyMatchDestination.entries[index].route
                    navController.navigate(route) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                    }
                }
            )
            NavHost(
                navController = navController,
                startDestination = MyMatchDestination.MATCH_HISTORY.route,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(color = ColorStyle.GRAY_200)
            ) {
                composable(MyMatchDestination.MATCH_HISTORY.route, enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(700)
                    )
                },
                    popEnterTransition = {
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(700)
                        )
                    }) {
                    MatchHistoryView()
                }
                composable(MyMatchDestination.MATCH_NOTICE.route, enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(700)
                    )
                },
                    popEnterTransition = {
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(700)
                        )
                    }) {
                    MatchNoticeView()
                }
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
    onTabSelected: (Int) -> Unit
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



