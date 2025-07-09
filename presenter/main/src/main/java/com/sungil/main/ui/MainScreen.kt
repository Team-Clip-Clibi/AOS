package com.sungil.main.ui

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.core.CustomSnackBar
import com.example.core.TopAppBarWithCloseButton
import com.sungil.main.MainViewModel
import com.sungil.main.BottomView
import com.sungil.main.MainView
import com.sungil.main.R
import com.sungil.main.TRIGGER_OVERDUE
import com.sungil.main.TRIGGER_TIME_UP
import com.sungil.main.component.BottomNavigation
import com.sungil.main.component.CustomMainPageTopBar
import com.sungil.main.component.HomeViewTopBar
import com.sungil.main.component.MatchIngFlowView
import com.sungil.main.nav.MainNavigation
import androidx.compose.ui.Alignment

@Composable
fun MainScreenView(
    navController: NavHostController,
    viewModel: MainViewModel,
    profileButtonClick: () -> Unit,
    reportClick: () -> Unit,
    lowClick: () -> Unit,
    alarmClick: () -> Unit,
    oneThingClick: () -> Unit,
    firstMatchClick: (String) -> Unit,
    randomMatchClick: () -> Unit,
    login: () -> Unit,
    guide: () -> Unit,
) {
    val bottomNavBottomViews = listOf(BottomView.Home, BottomView.MatchView, BottomView.MyPage)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val shouldShowBottomBar = bottomNavBottomViews.any { it.screenRoute == currentRoute }
    val snackBarHostState = remember { SnackbarHostState() }
    val lifecycleOwner = LocalLifecycleOwner.current

    val matchTriggerState by viewModel.meetingTrigger.collectAsState()

    val showMatchFlowView = remember(matchTriggerState) {
        matchTriggerState is MainViewModel.MatchTriggerUiState.Triggered &&
                (matchTriggerState as MainViewModel.MatchTriggerUiState.Triggered).dto.trigger == TRIGGER_TIME_UP
    }

    Scaffold(
        bottomBar = {
            if (shouldShowBottomBar) {
                BottomNavigation(navController = navController)
            }
        },
        topBar = {
            when (currentRoute) {
                MainView.REVIEW.route -> {
                    TopAppBarWithCloseButton(
                        title = stringResource(R.string.review_app_bar),
                        onBackClick = {
                            viewModel.initParticipants()
                        },
                        isNavigationShow = false,
                    )
                }

                BottomView.Home.screenRoute -> {
                    val alarmState by viewModel.userState.collectAsState()
                    val icons = when (val state = alarmState.oneThingState) {
                        is MainViewModel.UiState.Success ->
                            state.data.takeIf { it.isNotEmpty() }
                                ?.let { R.drawable.ic_bell_signal }
                                ?: R.drawable.ic_bell

                        else -> R.drawable.ic_bell
                    }
                    HomeViewTopBar(
                        image = icons,
                        click = {
                            alarmClick()
                        }
                    )
                }

                BottomView.MyPage.screenRoute -> {
                    CustomMainPageTopBar(text = stringResource(R.string.nav_my))
                }

                BottomView.MatchView.screenRoute -> {
                    CustomMainPageTopBar(text = stringResource(R.string.my_match_top_bar))
                }
            }
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
                            .calculateBottomPadding() + 8.dp
                    )
            )
        },
        modifier = Modifier.navigationBarsPadding()
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding() , bottom = paddingValues.calculateBottomPadding())
        ) {
            MainNavigation(
                navController = navController,
                viewModel = viewModel,
                profileButtonClick = profileButtonClick,
                reportClick = reportClick,
                lowClick = lowClick,
                oneThingClick = oneThingClick,
                firstMatchClick = firstMatchClick,
                randomMatchClick = randomMatchClick,
                login = login,
                guide = guide,
                paddingValues = paddingValues,
                snackBarHostState = snackBarHostState
            )

            if (showMatchFlowView && shouldShowBottomBar) {
                MatchIngFlowView(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 66.dp, start = 17.dp, end = 16.dp),
                    onClick = { /* TODO */ }
                )
            }
        }
    }
}