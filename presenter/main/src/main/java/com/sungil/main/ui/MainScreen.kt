package com.sungil.main.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.core.CustomSnackBar
import com.example.core.TopAppBarWithCloseButton
import com.sungil.main.MainViewModel
import com.sungil.main.BottomView
import com.sungil.main.MainView
import com.sungil.main.R
import com.sungil.main.TRIGGER_TIME_UP
import com.sungil.main.component.BottomNavigation
import com.sungil.main.component.CustomMainPageTopBar
import com.sungil.main.component.HomeViewTopBar
import com.sungil.main.component.MatchIngFlowView
import com.sungil.main.nav.MainNavigation
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import com.example.core.CustomDialogTwoButton
import com.sungil.editprofile.ERROR_NETWORK
import com.sungil.editprofile.ERROR_TOKEN_EXPIRE
import com.sungil.main.component.MatchingBottomSheet

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
    val context = LocalContext.current
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val bottomNavRoutes = listOf(BottomView.Home, BottomView.MatchView, BottomView.MyPage)
    val shouldShowBottomBar = currentRoute in bottomNavRoutes.map { it.screenRoute }
    val snackBarHostState = remember { SnackbarHostState() }
    val matchTriggerState by viewModel.meetingTrigger.collectAsState()
    val showBottomSheet by viewModel.bottomSheetShow.collectAsState()
    val showDialog by viewModel.dialogShow.collectAsState()
    val userState by viewModel.userState.collectAsState()
    val dto = (matchTriggerState as? MainViewModel.MatchTriggerUiState.Triggered)?.dto
    val showMatchFlowView = remember(dto) { dto?.trigger == TRIGGER_TIME_UP }
    val progressMatchInfo = (userState.progressMatchInfo as? MainViewModel.UiState.Success)?.data

    LaunchedEffect(userState.progressMatchInfo) {
        when (val result = userState.progressMatchInfo) {
            is MainViewModel.UiState.Error -> {
                val message = when (result.message) {
                    ERROR_TOKEN_EXPIRE -> context.getString(R.string.msg_re_login).also { login() }
                    ERROR_NETWORK -> context.getString(R.string.msg_network_error)
                    else -> "error"
                }
                message.let {
                    snackBarHostState.showSnackbar(message = it, duration = SnackbarDuration.Short)
                }
            }

            is MainViewModel.UiState.Success -> {
                viewModel.showBottomSheet()
            }

            else -> Unit
        }
    }
    Scaffold(
        bottomBar = {
            if (shouldShowBottomBar) BottomNavigation(navController)
        },
        topBar = {
            when (currentRoute) {
                MainView.REVIEW.route -> {
                    TopAppBarWithCloseButton(
                        title = stringResource(R.string.review_app_bar),
                        onBackClick = {
                            viewModel.initParticipants()
                            navController.popBackStack()
                        },
                        isNavigationShow = false,
                    )
                }

                BottomView.Home.screenRoute -> {
                    val icons = when (val state = userState.oneThingState) {
                        is MainViewModel.UiState.Success ->
                            if (state.data.isNotEmpty()) R.drawable.ic_bell_signal else R.drawable.ic_bell

                        else -> R.drawable.ic_bell
                    }
                    HomeViewTopBar(image = icons, click = alarmClick)
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
                snackbar = { data -> CustomSnackBar(data) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 17.dp,
                        end = 16.dp,
                        bottom = WindowInsets.navigationBars.asPaddingValues()
                            .calculateBottomPadding() + 8.dp
                    )
            )
        },
        modifier = Modifier.navigationBarsPadding()
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding()
                )
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

            if (showBottomSheet && progressMatchInfo != null) {
                MatchingBottomSheet(
                    viewModel = viewModel,
                    onClick = { viewModel.showDialog() },
                    matchData = progressMatchInfo
                )
            }

            if (showMatchFlowView && shouldShowBottomBar) {
                MatchIngFlowView(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 12.dp, start = 17.dp, end = 16.dp),
                    onClick = {
                        viewModel.progressMatchInfo(
                            matchId = dto!!.id,
                            matchType = dto.type
                        )
                    }
                )
            }
        }

        if (showDialog) {
            CustomDialogTwoButton(
                buttonText = stringResource(R.string.review_dialog_btn_okay),
                dismissButtonText = stringResource(R.string.review_dialog_btn_cancel),
                titleText = stringResource(R.string.review_dialog_title),
                onDismiss = { viewModel.closeDialog() },
                buttonClick = {
                    viewModel.closeDialog()
                    viewModel.setReviewData(
                        participants = progressMatchInfo!!.nickName,
                        matchType = dto!!.matchType,
                        matchId = dto.id
                    )
                    navController.navigate(MainView.REVIEW.route)
                },
                contentText = "${dto?.localTime ?: ""} ${dto?.matchType ?: ""}"
            )
        }
    }
}