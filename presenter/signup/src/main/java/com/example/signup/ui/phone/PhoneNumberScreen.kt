package com.example.signup.ui.phone

import android.app.Activity
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.signup.R
import com.example.signup.SignUpViewModel
import com.example.signup.ui.component.TopBar


@Composable
internal fun PhoneNumberScreen(
    viewModel: SignUpViewModel,
    actionClick: () -> Unit,
    buttonClick: () -> Unit,
    signUpPage: () -> Unit,
) {
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
        }
    ) { paddingValues ->
        PhoneNumberScreenMain(paddingValues, viewModel, buttonClick, signUpPage)
    }
}