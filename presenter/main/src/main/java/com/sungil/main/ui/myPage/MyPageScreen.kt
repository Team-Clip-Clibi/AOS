package com.sungil.main.ui.myPage

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.sungil.main.MainViewModel
import com.sungil.main.R
import com.sungil.main.component.CustomMainPageTopBar

@Composable
internal fun MyPageScreen(
    viewModel: MainViewModel,
    profileButtonEditClick: () -> Unit,
    reportClick: () -> Unit,
    lowClick : () -> Unit
) {
    Scaffold(
        topBar = {
            CustomMainPageTopBar(
                text = stringResource(R.string.nav_my)
            )
        }
    ) { innerPadding ->
        MyPageScreenMain(innerPadding, viewModel, profileButtonEditClick, reportClick, lowClick)
    }
}
