package com.sungil.main.ui.myapge

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign

@Composable
fun MyPageScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red)
    ) {
        Text(
            text = "개발중 MyPage",
            style = MaterialTheme.typography.h1,
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}