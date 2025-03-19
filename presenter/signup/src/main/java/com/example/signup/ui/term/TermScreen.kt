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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TermScreen(viewModel : SignUpViewModel) {
    var pageNum by remember { mutableStateOf("1/5") }
    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    modifier = Modifier.padding(start = 12.dp, end = 16.dp),
                    title = {
                        Text(
                            text = stringResource(R.string.txt_topBar_title),
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp,
                            fontFamily = FontFamily(Font(R.font.bold))
                        )
                    },
                    navigationIcon = {
                        Image(
                            painter = painterResource(id = R.drawable.ic_arrow_back),
                            contentDescription = "뒤로가기",
                            modifier = Modifier
                                .padding(1.dp)
                                .width(24.dp)
                                .height(24.dp)
                                .clickable {

                                }
                        )
                    },
                    actions = {
                        Text(
                            text = pageNum,
                            style = TextStyle(
                                fontSize = 12.sp,
                                lineHeight = 18.sp,
                                fontFamily = FontFamily(Font(R.font.light)),
                                fontWeight = FontWeight(600),
                                color = Color(0xFF9254DE),
                                textAlign = TextAlign.End
                            )
                        )
                    }
                )
                HorizontalDivider(thickness = 1.dp, color = Color(0xFFEFEFEF))
            }
        }
    ) { paddingValues ->
        TermScreenMain(paddingValues, viewModel)
    }
}


