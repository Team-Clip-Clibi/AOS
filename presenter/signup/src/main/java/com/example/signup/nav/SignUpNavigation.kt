package com.example.signup.nav

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.core.CustomSnackBar
import com.example.core.TopAppBarNumber
import com.example.signup.ERROR_ALREADY_SIGN_UP
import com.example.signup.ERROR_DATA_NOT_INPUT
import com.example.signup.ERROR_NETWORK_ERROR
import com.example.signup.ERROR_SAVE_DATA_FAIL
import com.example.signup.ERROR_SAVE_SIGNUP_HISTORY_FAIL
import com.example.signup.NAME_OKAY
import com.example.signup.NAV_ALREADY_SIGN_UP
import com.example.signup.NAV_INFO
import com.example.signup.NAV_NAME
import com.example.signup.NAV_NICK
import com.example.signup.NAV_PHONE
import com.example.signup.NAV_SIGN_UP_FINISH
import com.example.signup.NAV_TERM
import com.example.signup.NICKNAME_UPDATE_SUCCESS
import com.example.signup.R
import com.example.signup.RE_LOGIN
import com.example.signup.SignUpStep
import com.example.signup.SignUpStepState
import com.example.signup.SignUpViewModel
import com.example.signup.ui.alreadySignUp.AlreadySignUpScreenView
import com.example.signup.ui.component.AlreadySignUpBottomBar
import com.example.signup.ui.component.BottomBar
import com.example.signup.ui.detail.DetailView
import com.example.signup.ui.name.InputNameView
import com.example.signup.ui.nickname.NickNameView
import com.example.signup.ui.phone.PhoneNumberView
import com.example.signup.ui.signUpFinish.SignUpFinishView
import com.example.signup.ui.term.TermView

@Composable
internal fun SignUpNavigation(viewModel: SignUpViewModel, loginPage: () -> Unit, main: () -> Unit) {
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    val context = LocalContext.current
    val termItems by viewModel.termItem.collectAsState(initial = emptyList())
    val uiState by viewModel.userInfoState.collectAsState()
    LaunchedEffect(uiState.stepStates[SignUpStep.TERM]) {
        when (uiState.stepStates[SignUpStep.TERM]) {
            is SignUpStepState.Success -> {
                navController.navigate(NAV_PHONE) {
                    popUpTo(NAV_PHONE) { inclusive = true }
                }
            }

            is SignUpStepState.Error -> {
                snackbarHostState.showSnackbar(
                    message = context.getString(R.string.msg_reLogin),
                    duration = SnackbarDuration.Short
                )
                loginPage()
            }
            else -> Unit
        }
    }
    LaunchedEffect(uiState.stepStates[SignUpStep.PHONE]) {
        when (uiState.stepStates[SignUpStep.PHONE]) {
            is SignUpStepState.Error -> {
                if ((uiState.stepStates[SignUpStep.PHONE] as? SignUpStepState.Error)?.message == ERROR_ALREADY_SIGN_UP) {
                    navController.navigate(NAV_ALREADY_SIGN_UP)
                } else {
                    when ((uiState.stepStates[SignUpStep.PHONE] as? SignUpStepState.Error)?.message) {
                        RE_LOGIN -> {
                            loginPage()
                        }

                        ERROR_NETWORK_ERROR -> {
                            snackbarHostState.showSnackbar(
                                message = context.getString(R.string.msg_network_error),
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                }
            }
            is SignUpStepState.Success -> {
                navController.navigate(NAV_NAME) {
                    popUpTo(NAV_NAME) { inclusive = true }
                }
            }
            else -> Unit
        }
    }
    LaunchedEffect(uiState.stepStates[SignUpStep.NAME]) {
        when (uiState.stepStates[SignUpStep.NAME]) {
            is SignUpStepState.Success -> {
                navController.navigate(NAV_NICK) {
                    popUpTo(NAV_NICK) { inclusive = true }
                }
            }

            is SignUpStepState.Error -> {
                when ((uiState.stepStates[SignUpStep.NAME] as? SignUpStepState.Error)?.message) {
                    RE_LOGIN -> {
                        loginPage()
                    }

                    ERROR_NETWORK_ERROR -> {
                        snackbarHostState.showSnackbar(
                            message = context.getString(R.string.msg_network_error),
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            }
            else -> Unit
        }
    }
    LaunchedEffect(uiState.stepStates[SignUpStep.NICKNAME]) {
        when (uiState.stepStates[SignUpStep.NICKNAME]) {
            is SignUpStepState.Error -> {
                when ((uiState.stepStates[SignUpStep.NICKNAME] as? SignUpStepState.Error)?.message) {
                    RE_LOGIN -> {
                        loginPage()
                    }

                    ERROR_NETWORK_ERROR -> {
                        snackbarHostState.showSnackbar(
                            message = context.getString(R.string.msg_network_error),
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            }
            is SignUpStepState.Success ->{
                when((uiState.stepStates[SignUpStep.NICKNAME] as? SignUpStepState.Success)?.message){
                    NICKNAME_UPDATE_SUCCESS -> {
                        navController.navigate(NAV_INFO) {
                            popUpTo(NAV_INFO) { inclusive = true }
                        }
                    }
                }
            }
            else -> Unit
        }
    }
    LaunchedEffect(uiState.stepStates[SignUpStep.INFO]) {
        when (uiState.stepStates[SignUpStep.INFO]) {
            is SignUpStepState.Success -> {
                navController.navigate(NAV_SIGN_UP_FINISH) {
                    popUpTo(NAV_SIGN_UP_FINISH) { inclusive = true }
                }
            }

            is SignUpStepState.Error -> {
                when ((uiState.stepStates[SignUpStep.INFO] as? SignUpStepState.Error)?.message) {
                    RE_LOGIN -> {
                        loginPage()
                    }

                    ERROR_NETWORK_ERROR -> {
                        snackbarHostState.showSnackbar(
                            message = context.getString(R.string.msg_network_error),
                            duration = SnackbarDuration.Short
                        )
                    }

                    ERROR_SAVE_DATA_FAIL -> {
                        snackbarHostState.showSnackbar(
                            message = context.getString(R.string.msg_data_error),
                            duration = SnackbarDuration.Short
                        )
                    }

                    ERROR_SAVE_SIGNUP_HISTORY_FAIL -> {
                        snackbarHostState.showSnackbar(
                            message = context.getString(R.string.msg_data_error),
                            duration = SnackbarDuration.Short
                        )
                    }

                    ERROR_DATA_NOT_INPUT -> {
                        snackbarHostState.showSnackbar(
                            message = context.getString(R.string.msg_input_birth),
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            }

            else -> Unit
        }
    }
    Scaffold(
        topBar = {
            if(currentRoute != NAV_SIGN_UP_FINISH){
                TopAppBarNumber(
                    title = stringResource(R.string.txt_topBar_title),
                    currentPage = when (currentRoute) {
                        NAV_PHONE -> 1
                        NAV_NAME -> 2
                        NAV_NICK -> 3
                        NAV_INFO -> 4
                        else -> 0
                    },
                    totalPage = 4,
                    onBackClick = {
                        if (!navController.popBackStack()) loginPage()
                    },
                    isPageTextShow = when (currentRoute) {
                        NAV_TERM -> false
                        NAV_ALREADY_SIGN_UP -> false
                        else -> true
                    }
                )
            }
        },
        bottomBar = {
            when (currentRoute) {
                NAV_ALREADY_SIGN_UP -> {
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
                            NAV_TERM -> termItems.getOrNull(1)?.checked == true && termItems.getOrNull(
                                2
                            )?.checked == true

                            NAV_PHONE -> uiState.phoneNumber.isNotEmpty()
                            NAV_NAME -> uiState.name.isNotEmpty()
                            NAV_NICK -> (uiState.stepStates[SignUpStep.NICKNAME] as? SignUpStepState.Success)?.message == NAME_OKAY && uiState.nickName.isNotEmpty()
                            NAV_INFO -> uiState.birthYear.isNotEmpty() &&
                                    uiState.birthMonth.isNotEmpty() &&
                                    uiState.birtDay.isNotEmpty() &&
                                    uiState.city.isNotEmpty() &&
                                    uiState.area.isNotEmpty() &&
                                    uiState.gender.isNotEmpty()
                            NAV_SIGN_UP_FINISH -> true
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
                                    viewModel.sendNickName()
                                }

                                NAV_INFO -> {
                                    viewModel.sendDetail()
                                }
                                NAV_SIGN_UP_FINISH ->{
                                    main()
                                }
                            }
                        },
                        buttonText = when (currentRoute) {
                            NAV_TERM -> stringResource(R.string.btn_accept)
                            NAV_SIGN_UP_FINISH -> stringResource(R.string.btn_confirm)
                            else -> stringResource(R.string.btn_next)
                        },
                    )
                }
            }
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                snackbar = { data ->
                    CustomSnackBar(data)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 17.dp,
                        end = 16.dp,
                        bottom = WindowInsets.navigationBars
                            .asPaddingValues()
                            .calculateBottomPadding()
                    )
            )
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
                DetailView(viewModel = viewModel)
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

            composable(NAV_SIGN_UP_FINISH, enterTransition = {
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
                SignUpFinishView()
            }
        }
    }
}