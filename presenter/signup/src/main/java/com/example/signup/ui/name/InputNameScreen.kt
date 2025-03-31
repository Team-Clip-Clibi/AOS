package com.example.signup.ui.name

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.signup.R
import com.example.signup.SignUpViewModel
import com.example.signup.ui.component.TopBar

@Composable
internal fun InputNameScreen(
    viewModel: SignUpViewModel,
    actionClick: () -> Unit,
    buttonClick: () -> Unit,
) {
    Scaffold(topBar = {
        TopBar(
            title = stringResource(R.string.txt_topBar_title),
            currentPage = 3,
            totalPage = 5,
            onBackClick = {
                actionClick()
            }
        )
    }
    ) { paddingValues ->
        InputNameScreenMain(paddingValues, viewModel, buttonClick)
    }
}