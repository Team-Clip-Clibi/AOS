package com.example.signup.ui.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.signup.SignUpViewModel

@Composable
internal fun InPutDetailInfoScreenMain(
    paddingValues: PaddingValues,
    viewModel: SignUpViewModel,
    buttonClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 17.dp,
                end = 17.dp,
                top = paddingValues.calculateTopPadding() + 32.dp,
                bottom = 21.dp
            )
    ){
        Column {

        }
    }
}