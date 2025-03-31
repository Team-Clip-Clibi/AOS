package com.example.signup.ui.nickname

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.signup.R
import com.example.signup.SignUpViewModel
import com.example.signup.ui.component.TopBar

@Composable
internal fun InputNickNameScreen(
    viewModel: SignUpViewModel,
    actionClick: () -> Unit,
    buttonClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.txt_topBar_title),
                currentPage = 4,
                totalPage = 5,
                onBackClick = {
                    actionClick()
                }
            )
        }
    ) { paddingValues ->
        InPutNickNameScreenMain(
            paddingValues, viewModel, buttonClick
        )
    }
}