package com.sungil.main.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sungil.main.MainViewModel
import com.sungil.main.BottomView
import com.sungil.main.MainView
import com.sungil.main.ui.myMatch.MyMatchView
import com.sungil.main.ui.home.HomeScreen
import com.sungil.main.ui.matchDetail.MeetDetailView
import com.sungil.main.ui.myPage.MyPageScreen
import com.sungil.main.ui.payDetail.PayDetailView
import com.sungil.main.ui.review.ReviewView

@Composable
fun MainNavigation(
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
    NavHost(navController = navController, startDestination = BottomView.Home.screenRoute) {
        composable(BottomView.Home.screenRoute) {
            HomeScreen(
                viewModel = viewModel,
                alarmClick = alarmClick,
                notifyClick = {},
                oneThingMatchClick = oneThingClick,
                randomMatchClick = randomMatchClick,
                firstMatchClick = firstMatchClick,
                login = login
            )
        }
        composable(BottomView.Calendar.screenRoute) {
            MyMatchView(
                viewModel = viewModel,
                login = login,
                guide = guide,
                matchDetail = { navController.navigate(MainView.MATCH_DETAIL.route) },
                review = {
                    navController.navigate(MainView.REVIEW.route)
                })
        }
        composable(BottomView.MyPage.screenRoute) {
            MyPageScreen(viewModel, profileButtonClick, reportClick, lowClick)
        }
        composable(MainView.MATCH_DETAIL.route) {
            MeetDetailView(
                onBack = { navController.popBackStack() },
                viewModel = viewModel,
                payDetail = {
                    navController.navigate(MainView.PAY_DETAIL.route)
                }
            )
        }
        composable(MainView.PAY_DETAIL.route) {
            PayDetailView(
                onBack = { navController.popBackStack() },
                viewModel = viewModel
            )
        }
        composable(MainView.REVIEW.route){
            ReviewView(
                onClose = {navController.popBackStack()},
                viewModel = viewModel
            )
        }
    }
}