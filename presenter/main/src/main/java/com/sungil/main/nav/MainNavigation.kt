package com.sungil.main.nav

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sungil.main.MainViewModel
import com.sungil.main.BottomView
import com.sungil.main.MainView
import com.sungil.main.ui.home.HomeViewScreen
import com.sungil.main.ui.matchDetail.MeetDetailView
import com.sungil.main.ui.myMatch.MyMatchViewScreen
import com.sungil.main.ui.myPage.MyPageViewScreen
import com.sungil.main.ui.payDetail.PayDetailView
import com.sungil.main.ui.review.ReviewView
import androidx.compose.animation.core.tween
import androidx.navigation.NavBackStackEntry
import androidx.compose.animation.AnimatedContentTransitionScope

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
    val bottomRoutes = setOf(
        BottomView.Home.screenRoute,
        BottomView.MatchView.screenRoute,
        BottomView.MyPage.screenRoute
    )

    NavHost(
        navController = navController,
        startDestination = BottomView.Home.screenRoute,
        enterTransition = {
            slideIntoContainer(
                getSlideDirection(initialState, targetState, bottomRoutes, forward = true),
                animationSpec = tween(700)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                getSlideDirection(initialState, targetState, bottomRoutes, forward = true),
                animationSpec = tween(700)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                getSlideDirection(initialState, targetState, bottomRoutes, forward = false),
                animationSpec = tween(700)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                getSlideDirection(initialState, targetState, bottomRoutes, forward = false),
                animationSpec = tween(700)
            )
        }
    ) {
        composable(BottomView.Home.screenRoute) {
            HomeViewScreen(
                viewModel = viewModel,
                firstMatchClick = firstMatchClick,
                notifyClick = {},
                oneThingClick = oneThingClick,
                randomMatchClick = randomMatchClick,
                reLogin = login,
                snackBarHostState = snackBarHostState
            )
        }

        composable(BottomView.MatchView.screenRoute) {
            MyMatchViewScreen(
                viewModel = viewModel,
                guide = guide,
                login = login,
                matchDetail = { navController.navigate(MainView.MATCH_DETAIL.route) },
                review = { navController.navigate(MainView.REVIEW.route) },
                snackBarHostState = snackBarHostState
            )
        }

        composable(BottomView.MyPage.screenRoute) {
            MyPageViewScreen(
                viewModel = viewModel,
                profileEdit = profileButtonClick,
                reportClick = reportClick,
                lowGuide = lowClick
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
            ReviewView(
                onClose = { navController.popBackStack() },
                viewModel = viewModel,
                paddingValues = paddingValues
            )
        }
    }
}

private fun getSlideDirection(
    from: NavBackStackEntry,
    to: NavBackStackEntry,
    bottomRoutes: Set<String>,
    forward: Boolean
): AnimatedContentTransitionScope.SlideDirection {
    val fromRoute = from.destination.route
    val toRoute = to.destination.route

    val isBottom = fromRoute in bottomRoutes && toRoute in bottomRoutes
    return when {
        isBottom && forward -> AnimatedContentTransitionScope.SlideDirection.Left
        isBottom && !forward -> AnimatedContentTransitionScope.SlideDirection.Right
        !isBottom && forward -> AnimatedContentTransitionScope.SlideDirection.Up
        else -> AnimatedContentTransitionScope.SlideDirection.Down
    }
}
