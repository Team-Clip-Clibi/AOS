package com.example.signup.ui.phone

import android.app.Activity
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.signup.R
import com.example.signup.SignUpViewModel
import com.example.signup.ui.component.CustomSnackBar
import com.example.signup.ui.component.TopBar


@Composable
internal fun PhoneNumberScreen(
    viewModel: SignUpViewModel,
    actionClick: () -> Unit,
    buttonClick: () -> Unit,
    signUpPage: () -> Unit,
    goToLoginPage: () -> Unit,
) {
    val snackBarHostState = remember { SnackbarHostState() }
    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.txt_topBar_title),
                currentPage = 2,
                totalPage = 5,
                onBackClick = {
                    actionClick()
                }
            )
        },

        snackbarHost = {
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
                            .calculateTopPadding() + 70.dp
                    )
            )
        }
    ) { paddingValues ->
        PhoneNumberScreenMain(
            paddingValues,
            viewModel,
            buttonClick,
            signUpPage,
            snackBarHostState,
            goToLoginPage
        )
    }
}