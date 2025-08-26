package com.clip.report.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.clip.report.NAV_REPORT_MAIN_VIEW
import com.clip.report.NAV_REPORT_MATCH
import com.clip.report.NAV_REPORT_REASON
import com.clip.report.ReportViewModel
import com.clip.report.ui.category.CategoryView
import com.clip.report.ui.content.ContentView
import com.clip.report.ui.main.ReportView

@Composable
internal fun ReportNav(
    viewModel: ReportViewModel,
    onProfilePage: () -> Unit,
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NAV_REPORT_MAIN_VIEW
    ) {
        composable(NAV_REPORT_MAIN_VIEW) {
            ReportView(
                onBackClick = onProfilePage,
                category = {
                    navController.navigate(NAV_REPORT_MATCH)
                }
            )
        }
        composable(NAV_REPORT_MATCH){
            CategoryView(
                viewModel = viewModel,
                onBackClick = {
                    navController.popBackStack()
                },
                content = {
                    navController.navigate(NAV_REPORT_REASON)
                }
            )
        }
        composable(NAV_REPORT_REASON) {
            ContentView(
                viewModel = viewModel,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}