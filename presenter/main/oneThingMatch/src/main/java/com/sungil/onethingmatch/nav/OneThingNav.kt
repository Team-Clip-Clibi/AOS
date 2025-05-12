package com.sungil.onethingmatch.nav

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sungil.onethingmatch.NAV_CATEGORY
import com.sungil.onethingmatch.NAV_INTRO
import com.sungil.onethingmatch.NAV_SUBJECT
import com.sungil.onethingmatch.OneThingViewModel
import com.sungil.onethingmatch.component.TopAppBarWithProgress
import com.sungil.onethingmatch.ui.categort.CategoryView
import com.sungil.onethingmatch.ui.intro.IntroView
import com.sungil.onethingmatch.ui.subject.InputSubjectView

@Composable
fun OneThingNav(
    viewModel: OneThingViewModel,
    home: () -> Unit,
) {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    val currentPage = when (currentRoute) {
        NAV_SUBJECT -> 1
        NAV_CATEGORY -> 2
        else -> 0
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBarWithProgress(
            currentPage = currentPage,
            totalPage = 5,
            onBackClick = {
                if (!navController.popBackStack()) home()
            }
        )
        NavHost(
            navController = navController,
            startDestination = NAV_INTRO,
            modifier = Modifier.weight(1f)
        ) {
            composable(NAV_INTRO) {
                IntroView(
                    goSubjectPage = { navController.navigate(NAV_SUBJECT)
                                    },
                    viewModel = viewModel
                )
            }

            composable(NAV_SUBJECT) {
                InputSubjectView(
                    viewModel = viewModel,
                    goNextPage = { navController.navigate(NAV_CATEGORY) }
                )
            }

            composable(NAV_CATEGORY) {
                CategoryView(
                    viewModel = viewModel,
                    goNextPage = { navController.navigate(NAV_SUBJECT) }
                )
            }
        }
    }
}