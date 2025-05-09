package com.sungil.onethingmatch.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.AppTextStyles
import com.example.core.ColorStyle
import kotlinx.coroutines.delay

@Composable
fun SlidingTextBox(textList: List<String>) {
    val index = remember { mutableIntStateOf(0) }
    val currentText = textList.getOrNull(index.intValue) ?: ""

    // 자동 순회 (2초마다 1개씩)
    LaunchedEffect(Unit) {
        while (true) {
            delay(2000L)
            index.intValue = (index.intValue + 1) % textList.size
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(32.dp)
            .background(color = ColorStyle.PURPLE_100, shape = RoundedCornerShape(size = 4.dp))
            .padding(start = 12.dp, top = 6.dp, end = 12.dp, bottom = 6.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        AnimatedContent(
            targetState = currentText,
            transitionSpec = {
                slideInVertically { height -> height } + fadeIn() togetherWith
                        slideOutVertically { height -> -height } + fadeOut()
            },
            label = "subject"
        ) { text ->
            Text(
                text = text,
                style = AppTextStyles.BODY_14_20_MEDIUM,
                color = ColorStyle.GRAY_800
            )
        }
    }
}
