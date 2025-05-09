package com.example.core

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProgressPurple400(
    progress : Float
){
    LinearProgressIndicator(
        progress = {progress},
        modifier = Modifier.fillMaxWidth()
            .height(6.dp),
        color = ColorStyle.PURPLE_400,
        trackColor = ColorStyle.GRAY_200
    )
}