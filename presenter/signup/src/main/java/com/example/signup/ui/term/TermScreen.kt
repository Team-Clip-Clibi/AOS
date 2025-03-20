package com.example.signup.ui.term

import androidx.compose.runtime.Composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.signup.R
import com.example.signup.SignUpViewModel
import com.example.signup.ui.component.TopBar



@Composable
internal fun TermScreen(
    viewModel: SignUpViewModel,
    actionBarClick: () -> Unit,
    buttonClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.txt_term_title),
                currentPage = 1,
                totalPage = 5,
                onBackClick = {
                    actionBarClick()
                }
            )
        }
    ) { paddingValues ->
        TermScreenMain(paddingValues, viewModel)
    }
}


