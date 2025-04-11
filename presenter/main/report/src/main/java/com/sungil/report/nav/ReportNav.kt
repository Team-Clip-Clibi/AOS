package com.sungil.report.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sungil.report.NAV_REPORT_MAIN_VIEW
import com.sungil.report.NAV_REPORT_MATCH
import com.sungil.report.NAV_REPORT_REASON
import com.sungil.report.ReportViewModel
import com.sungil.report.ui.category.CategoryView
import com.sungil.report.ui.content.ContentView
import com.sungil.report.ui.main.ReportView

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
                onBackClick = onProfilePage,
                content = {
                    navController.navigate(NAV_REPORT_REASON)
                }
            )
        }
        composable(NAV_REPORT_REASON) {
            ContentView(
                viewModel = viewModel,
                onBackClick = onProfilePage
            )
        }
    }
}