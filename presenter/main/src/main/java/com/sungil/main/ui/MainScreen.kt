package com.sungil.main.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sungil.main.MainViewModel
import com.sungil.main.Screen
import com.sungil.main.component.BottomNavigation
import com.sungil.main.nav.MainNavigation


@Composable
fun MainScreenView(
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
    val navController = rememberNavController()
    val bottomNavScreens = listOf(Screen.Home, Screen.Calendar, Screen.MyPage)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val shouldShowBottomBar = bottomNavScreens.any { it.screenRoute == currentRoute }

    Scaffold(
        bottomBar = {
            if (shouldShowBottomBar) {
                BottomNavigation(navController = navController)
            }
        },
        modifier = Modifier.navigationBarsPadding()
    ) {
        Box(Modifier.padding(it)) {
            MainNavigation(
                navController = navController,
                viewModel = viewModel,
                profileButtonClick = profileButtonClick,
                reportClick = reportClick,
                lowClick = lowClick,
                alarmClick = alarmClick,
                oneThingClick = oneThingClick,
                firstMatchClick = firstMatchClick,
                randomMatchClick = randomMatchClick,
                login = login,
                guide = guide
            )
        }
    }
}