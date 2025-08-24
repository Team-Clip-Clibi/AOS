package com.sungil.login.ui


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core.ColorStyle
import com.example.core.CustomDialogOneButton
import com.example.core.CustomSnackBar
import com.sungil.login.ERROR_NETWORK
import com.sungil.login.ERROR_NOTIFY_SAVE
import com.sungil.login.ERROR_RE_LOGIN
import com.sungil.login.LoginViewModel
import com.sungil.login.R
import com.sungil.login.UPDATE_APP

@Composable
internal fun SplashScreen(
    viewModel: LoginViewModel,
    login: () -> Unit,
    notification: () -> Unit,
    home: () -> Unit,
    isDebug: Boolean,
    appVersion: String,
    playStore: () -> Unit,
) {
    val snackBarHostState = remember { SnackbarHostState() }
    var updateDialogShow by remember { mutableStateOf(false) }
    val uiState by viewModel.actionFlow.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(uiState.permissionShow) {
        when (val result = uiState.permissionShow) {
            is LoginViewModel.UiState.Success -> {
                when (result.data) {
                    true -> {
                        viewModel.getToken()
                    }

                    false -> {
                        notification()
                    }
                }
            }

            else -> Unit
        }
    }
    LaunchedEffect(uiState.notification) {
        when (val state = uiState.notification) {
            is LoginViewModel.UiState.Error -> {
                when (state.error) {
                    ERROR_NOTIFY_SAVE -> {
                        snackBarHostState.showSnackbar(
                            message = context.getString(R.string.msg_app_error_notification),
                            duration = SnackbarDuration.Short
                        )
                    }

                    ERROR_RE_LOGIN -> {
                        viewModel.getToken()
                    }

                    ERROR_NETWORK -> {
                        snackBarHostState.showSnackbar(
                            message = context.getString(R.string.msg_app_error_network),
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            }

            is LoginViewModel.UiState.Success -> {
                viewModel.getToken()
            }

            else -> Unit
        }
    }
    LaunchedEffect(uiState.fcmToken) {
        when (val fcmToken = uiState.fcmToken) {
            is LoginViewModel.UiState.Error -> {
                when (fcmToken.error) {
                    ERROR_RE_LOGIN -> {
                        viewModel.banner()
                    }

                    else -> {
                        snackBarHostState.showSnackbar(
                            message = context.getString(R.string.msg_app_error_fcm),
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            }

            is LoginViewModel.UiState.Success -> {
                viewModel.version(
                    isDebug = isDebug,
                    appVersion = appVersion
                )
            }

            else -> Unit
        }
    }
    LaunchedEffect(uiState.appVersionCheck) {
        when (val result = uiState.appVersionCheck) {
            is LoginViewModel.UiState.Error -> {
                if (result.error == UPDATE_APP) {
                    updateDialogShow = true
                    return@LaunchedEffect
                }
                snackBarHostState.showSnackbar(
                    message = result.error,
                    duration = SnackbarDuration.Short
                )
            }

            is LoginViewModel.UiState.Success -> {
                viewModel.getUserId()
            }

            else -> Unit
        }
    }
    LaunchedEffect(uiState.userId) {
        when (val userId = uiState.userId) {
            is LoginViewModel.UiState.Error -> {
                viewModel.banner()
            }

            is LoginViewModel.UiState.Success -> {
                viewModel.checkSignUp(userId.data)
            }

            else -> Unit
        }
    }
    LaunchedEffect(uiState.signUp) {
        when (uiState.signUp) {
            is LoginViewModel.UiState.Error -> {
                viewModel.banner()
            }

            is LoginViewModel.UiState.Success -> {
                home()
            }

            else -> Unit
        }
    }
    LaunchedEffect(uiState.banner) {
        when (uiState.banner) {
            is LoginViewModel.UiState.Error -> {
                snackBarHostState.showSnackbar(
                    message = context.getString(R.string.msg_app_error_network),
                    duration = SnackbarDuration.Short
                )
            }

            is LoginViewModel.UiState.Success -> {
                login()
            }

            else -> Unit
        }
    }

    Scaffold(snackbarHost = {
        SnackbarHost(
            hostState = snackBarHostState,
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
                        .calculateTopPadding()
                )
        )
    }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = ColorStyle.PURPLE_500)
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding()
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_splash_screen),
                    contentDescription = "splash screen"
                )
                if (updateDialogShow) {
                    CustomDialogOneButton(
                        onDismiss = {
                            updateDialogShow = false
                        },
                        buttonClick = {
                            /**
                             * TODO 추후 플레이스토어로 이동하는 코드 작성
                             */
                            viewModel.getUserId()
                        },
                        titleText = stringResource(R.string.dialog_update_title),
                        contentText = stringResource(R.string.dialog_update_content),
                        buttonText = stringResource(R.string.dialog_update_btn)
                    )
                }
            }
        }
    }
}