package com.sungil.main.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sungil.main.MainViewModel
import com.sungil.main.Screen
import com.sungil.main.ui.guide.MeetGuideView
import com.sungil.main.ui.myMatch.MyMatchView
import com.sungil.main.ui.home.HomeScreen
import com.sungil.main.ui.myPage.MyPageScreen

@Composable
fun MainNavigation(
    navController: NavHostController,
    viewModel: MainViewModel,
    profileButtonClick: () -> Unit,
    reportClick: () -> Unit,
    lowClick: () -> Unit,
    alarmClick: () -> Unit,
    oneThingClick : () -> Unit,
    firstMatchClick : (String) -> Unit,
    randomMatchClick : () -> Unit,
    login : () -> Unit
) {
    NavHost(navController = navController, startDestination = Screen.Home.screenRoute) {
        composable(Screen.Home.screenRoute) {
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
        composable(Screen.Calendar.screenRoute) {
            MyMatchView(viewModel = viewModel , login = login , guide = {navController.navigate(Screen.Guide.screenRoute)})
        }
        composable(Screen.MyPage.screenRoute) {
            MyPageScreen(viewModel, profileButtonClick, reportClick, lowClick)
        }
        composable(Screen.Guide.screenRoute) {
            MeetGuideView(
                onClose = { navController.navigate(Screen.MyPage.screenRoute) },
            )
        }
    }
}