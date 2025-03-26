package com.example.signup.ui.detail

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.signup.R
import com.example.signup.SignUpViewModel
import com.example.signup.ui.component.TopBar
import com.example.signup.ui.name.InputNameScreen

@Composable
internal fun InputDetailInfoScreen(
    viewModel: SignUpViewModel,
    actionClick: () -> Unit,
    buttonClick: () -> Unit,
) {
    Scaffold(topBar = {
        TopBar(
            title = stringResource(R.string.txt_topBar_title),
            currentPage = 4,
            totalPage = 4,
            onBackClick = {
                actionClick()
            }
        )
    }
    ) { paddingValues ->
        InPutDetailInfoScreenMain(
            paddingValues = paddingValues,
            viewModel = viewModel,
            buttonClick = buttonClick
        )
    }
}