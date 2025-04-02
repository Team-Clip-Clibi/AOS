package com.example.signup.ui.alreadySignUp

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.signup.R
import com.example.signup.SignUpViewModel
import com.example.signup.ui.component.TopBar

@Composable
internal fun AlreadySignUpScreen(
    viewModel: SignUpViewModel,
    actionClick: () -> Unit,
    buttonClick: () -> Unit,
) {
    Scaffold(topBar = {
        TopBar(
            title = stringResource(R.string.txt_topBar_title),
            currentPage = 0,
            totalPage = 0,
            onBackClick = {
                actionClick()
            }
        )
    }) { paddingValues ->
        AlreadySignUpScreenMain(
            paddingValues = paddingValues,
            viewModel = viewModel,
            buttonClick = buttonClick
        )
    }
}