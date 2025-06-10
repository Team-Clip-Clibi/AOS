package com.example.signup.nav

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.core.TopAppBarNumber
import com.example.signup.NAV_ALREADY_SIGN_UP
import com.example.signup.NAV_INFO
import com.example.signup.NAV_NAME
import com.example.signup.NAV_NICK
import com.example.signup.NAV_PHONE
import com.example.signup.NAV_TERM
import com.example.signup.R
import com.example.signup.SignUpViewModel
import com.example.signup.ui.alreadySignUp.AlreadySignUpScreenView
import com.example.signup.ui.component.AlreadySignUpBottomBar
import com.example.signup.ui.component.BottomBar
import com.example.signup.ui.detail.DetailView
import com.example.signup.ui.name.InputNameView
import com.example.signup.ui.nickname.NickNameView
import com.example.signup.ui.phone.PhoneNumberView
import com.example.signup.ui.term.TermView

@Composable
internal fun SignUpNavigation(viewModel: SignUpViewModel, clear: () -> Unit, main: () -> Unit) {
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    val context = LocalContext.current
    val termItems by viewModel.termItem.collectAsState(initial = emptyList())
    val uiState by viewModel.userInfoState.collectAsState()
    Scaffold(
        topBar = {
            TopAppBarNumber(
                title = stringResource(R.string.txt_topBar_title),
                currentPage = when (currentRoute) {
                    NAV_TERM -> 0
                    NAV_PHONE -> 1
                    NAV_NAME -> 2
                    NAV_NICK -> 3
                    NAV_INFO -> 4
                    NAV_ALREADY_SIGN_UP -> 0
                    else -> 0
                },
                totalPage = 5,
                onBackClick = {
                    if (!navController.popBackStack()) clear()
                }
            )
        },
        bottomBar = {
            when(currentRoute){
                NAV_ALREADY_SIGN_UP ->{
                    AlreadySignUpBottomBar(
                        onClick = viewModel::setSignUp,
                        isNotMyAccount = {
                            //TODO 아직 미구현
                        }
                    )
                }
                else -> {
                    BottomBar(
                        isEnable = when (currentRoute) {
                            NAV_TERM -> termItems.getOrNull(1)?.checked == true && termItems.getOrNull(2)?.checked == true
                            NAV_PHONE -> uiState.phoneNumber.isNotEmpty()
                            NAV_NAME -> uiState.name.isNotEmpty()
                            NAV_NICK -> uiState.nickCheckStanBy is SignUpViewModel.CheckState.ValueOkay ||
                                    uiState.nickCheckStanBy is SignUpViewModel.CheckState.StanBy
                            NAV_INFO -> uiState.birthYear.isNotEmpty() &&
                                    uiState.birthMonth.isNotEmpty() &&
                                    uiState.birtDay.isNotEmpty() &&
                                    uiState.city.isNotEmpty() &&
                                    uiState.area.isNotEmpty() &&
                                    uiState.gender.isNotEmpty()
                            else -> false
                        },
                        onClick = {
                            when (currentRoute) {
                                NAV_TERM -> {
                                    viewModel.sendTerm()
                                }

                                NAV_PHONE -> {
                                    viewModel.checkSignUpNumber()
                                }

                                NAV_NAME -> {
                                    viewModel.checkName()
                                }

                                NAV_NICK -> {
                                    viewModel.checkNickName()
                                }

                                NAV_INFO -> {
                                    viewModel.sendDetail()
                                }
                            }
                        },
                        buttonText = when (currentRoute) {
                            NAV_TERM -> stringResource(R.string.btn_accept)
                            else -> stringResource(R.string.btn_next)
                        },
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = NAV_TERM,
            modifier = Modifier.padding(
                top = paddingValues.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding()
            )
        ) {
            composable(NAV_TERM) {
                TermView(viewModel = viewModel)
            }

            composable(NAV_PHONE, enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(700)
                )
            },
                popEnterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(700)
                    )
                }) {
                PhoneNumberView(viewModel = viewModel)
            }
            composable(NAV_NAME, enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(700)
                )
            },
                popEnterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(700)
                    )
                }) {
                InputNameView(viewModel = viewModel)
            }
            composable(NAV_NICK, enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(700)
                )
            },
                popEnterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(700)
                    )
                }) {
                NickNameView(viewModel = viewModel)
            }

            composable(NAV_INFO, enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(700)
                )
            },
                popEnterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(700)
                    )
                }) {
                DetailView(viewModel = viewModel, snackBarHostState = snackbarHostState)
            }
            composable(NAV_ALREADY_SIGN_UP, enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(700)
                )
            },
                popEnterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(700)
                    )
                }) {
                AlreadySignUpScreenView(viewModel = viewModel)
            }
        }
    }
}