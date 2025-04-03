package com.sungil.main.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sungil.main.Screen
import com.sungil.main.ui.calendar.CalendarScreen
import com.sungil.main.ui.home.HomeScreen
import com.sungil.main.ui.myapge.MyPageScreen

@Composable
fun MainNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Home.screenRoute) {
        composable(Screen.Home.screenRoute) {
            HomeScreen()
        }
        composable(Screen.Calendar.screenRoute) {
            CalendarScreen()
        }
        composable(Screen.MyPage.screenRoute) {
            MyPageScreen()
        }
    }
}