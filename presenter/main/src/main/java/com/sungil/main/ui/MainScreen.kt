package com.sungil.main.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.sungil.main.MainViewModel
import com.sungil.main.component.BottomNavigation
import com.sungil.main.nav.MainNavigation


@Composable
fun MainScreenView(viewModel: MainViewModel) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigation(navController = navController) },
//        modifier = Modifier.navigationBarsPadding()
    ) {
        Box(Modifier.padding(it)) {
            MainNavigation(navController = navController ,viewModel)
        }
    }
}