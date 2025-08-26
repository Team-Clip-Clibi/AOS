package com.clip.main.nav

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.clip.main.MainViewModel
import com.clip.main.BottomView
import com.clip.main.MainView
import com.clip.main.ui.home.HomeViewScreen
import com.clip.main.ui.matchDetail.MeetDetailView
import com.clip.main.ui.myMatch.MyMatchViewScreen
import com.clip.main.ui.myPage.MyPageViewScreen
import com.clip.main.ui.payDetail.PayDetailView
import com.clip.main.ui.review.ReviewView
import androidx.compose.animation.core.tween
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import com.clip.main.ui.alarm.AlarmSettingView

@Composable
fun MainNavigation(
    navController: NavHostController,
    viewModel: MainViewModel,
    profileButtonClick: () -> Unit,
    reportClick: () -> Unit,
    lowClick: () -> Unit,
    oneThingClick: () -> Unit,
    firstMatchClick: (String) -> Unit,
    randomMatchClick: () -> Unit,
    login: () -> Unit,
    guide: () -> Unit,
    paddingValues: PaddingValues,
    snackBarHostState: SnackbarHostState,
) {

    NavHost(
        navController = navController,
        startDestination = BottomView.Home.screenRoute,
        enterTransition = {
            if (targetState.destination.route == MainView.REVIEW.route) {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Up,
                    animationSpec = tween(700)
                )
            } else {
                EnterTransition.None
            }
        },
        exitTransition = {
            if (initialState.destination.route == MainView.REVIEW.route) {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Down,
                    animationSpec = tween(700)
                )
            } else {
                ExitTransition.None
            }
        },
        popEnterTransition = {
            if (targetState.destination.route == MainView.REVIEW.route) {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Up,
                    animationSpec = tween(700)
                )
            } else {
                EnterTransition.None
            }
        },
        popExitTransition = {
            if (initialState.destination.route == MainView.REVIEW.route) {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Down,
                    animationSpec = tween(700)
                )
            } else {
                ExitTransition.None
            }
        }
    )
    {
        composable(BottomView.Home.screenRoute) {
            HomeViewScreen(
                viewModel = viewModel,
                firstMatchClick = firstMatchClick,
                notifyClick = {},
                oneThingClick = oneThingClick,
                randomMatchClick = randomMatchClick,
                reLogin = login,
                snackBarHostState = snackBarHostState,
                goMatchView = {
                    navController.navigate(BottomView.MatchView.screenRoute) {
                        navController.graph.startDestinationRoute?.let {
                            popUpTo(it) { saveState = true }
                        }
                    }
                },
                review = { matchId, matchType, participants ->
                    viewModel.setReviewData(
                        participants = participants.map { it -> it.nickName },
                        matchId = matchId,
                        matchType = matchType
                    )
                    navController.navigate(MainView.REVIEW.route)
                }
            )
        }

        composable(BottomView.MatchView.screenRoute) {
            MyMatchViewScreen(
                viewModel = viewModel,
                guide = guide,
                login = login,
                matchDetail = { navController.navigate(MainView.MATCH_DETAIL.route) },
                review = { participants, matchId, matchType ->
                    viewModel.setReviewData(
                        participants = participants,
                        matchId = matchId,
                        matchType = matchType
                    )
                    navController.navigate(MainView.REVIEW.route)
                },
                snackBarHostState = snackBarHostState
            )
        }

        composable(BottomView.MyPage.screenRoute) {
            MyPageViewScreen(
                viewModel = viewModel,
                profileEdit = profileButtonClick,
                reportClick = reportClick,
                lowGuide = lowClick,
                alarmSetting = {
                    navController.navigate(MainView.ALARM.route)
                },
                snackBarHostState = snackBarHostState
            )
        }

        composable(MainView.MATCH_DETAIL.route) {
            MeetDetailView(
                onBack = { navController.popBackStack() },
                viewModel = viewModel,
                payDetail = { navController.navigate(MainView.PAY_DETAIL.route) }
            )
        }

        composable(MainView.PAY_DETAIL.route) {
            PayDetailView(
                onBack = { navController.popBackStack() },
                viewModel = viewModel
            )
        }

        composable(MainView.REVIEW.route) {
            val data = viewModel.getReviewData()
            ReviewView(
                onClose = { navController.popBackStack() },
                viewModel = viewModel,
                paddingValues = paddingValues,
                participant = data.participants,
                matchId = data.matchId,
                matchType = data.matchType
            )
        }
        composable(MainView.ALARM.route) {
            AlarmSettingView(
                viewModel = viewModel,
                reLogin = login,
                snackbarHostState = snackBarHostState
            )
        }
    }
}

