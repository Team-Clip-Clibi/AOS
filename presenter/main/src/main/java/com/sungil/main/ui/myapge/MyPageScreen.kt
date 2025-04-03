package com.sungil.main.ui.myapge

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.sungil.main.R
import com.sungil.main.component.CustomMyPageAppBar

@Composable
internal fun MyPageScreen() {
    Scaffold(
        topBar = {
            CustomMyPageAppBar(
                text = stringResource(R.string.nav_my)
            )
        }
    ) { innerPadding ->
        MyPageScreenMain(innerPadding)
    }
}
