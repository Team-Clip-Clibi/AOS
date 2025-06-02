package com.oneThing.random.nav

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.oneThing.random.R
import com.oneThing.random.component.BottomBar
import com.oneThing.random.component.NAV_RANDOM_MATCH_INTRO
import com.oneThing.random.component.TopAppBarWithProgress

@Composable
internal fun RandomMatchNav(
    onBack: () -> Unit,
) {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    val pageInfo = when (currentRoute) {
        NAV_RANDOM_MATCH_INTRO -> -1
        else -> -1
    }
    Scaffold(
        topBar = {
            TopAppBarWithProgress(
                title = stringResource(R.string.top_app_bar_random_match),
                currentPage = pageInfo,
                totalPage = 4,
                onBackClick = {
                    if (!navController.popBackStack()) onBack()
                }
            )
        },
        bottomBar = {
            BottomBar(
                isEnable = true,
                buttonText = stringResource(R.string.random_btn_next),
                onClick = {

                }
            )
        }
    ) { paddingValues ->

    }
}
