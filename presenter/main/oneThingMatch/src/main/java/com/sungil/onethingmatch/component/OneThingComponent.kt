package com.sungil.onethingmatch.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.core.AppTextStyles
import com.example.core.ColorStyle
import com.example.core.TopAppBarNumber
import com.sungil.onethingmatch.CATEGORY
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

@Composable
fun CategoryItemView(
    category: CATEGORY,
    isSelect: Boolean,
    onClick: () -> Unit,
) {
    val modifier = when (isSelect) {
        true -> {
            Modifier
                .width(114.dp)
                .height(88.dp)
                .border(
                    width = 1.dp,
                    color = ColorStyle.PURPLE_200,
                    shape = RoundedCornerShape(8.dp)
                )
                .background(color = ColorStyle.PURPLE_100, shape = RoundedCornerShape(8.dp))
                .padding(10.dp)
        }

        false -> {
            Modifier
                .width(114.dp)
                .height(88.dp)
                .background(ColorStyle.GRAY_100, shape = RoundedCornerShape(8.dp))
                .padding(10.dp)
        }
    }
    Box(
        modifier = modifier
            .clickable { onClick() }
    ){
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = category.displayName,
                style = AppTextStyles.BODY_14_20_MEDIUM,
                color = ColorStyle.GRAY_800
            )
        }
    }
}

@Composable
fun TopAppBarWithProgress(
    title: String,
    currentPage: Int,
    totalPage: Int,
    onBackClick: () -> Unit
) {
    val animatedProgress by animateFloatAsState(
        targetValue = if (currentPage >= 0) currentPage / totalPage.toFloat() else 0f,
        animationSpec = tween(durationMillis = 500),
        label = "progress"
    )

    Column {
        TopAppBarNumber(
            title = title,
            currentPage = if (currentPage >= 0) currentPage else 0,
            totalPage = totalPage,
            onBackClick = onBackClick
        )
        if (currentPage >= 0) {
            LinearProgressIndicator(
                progress = { animatedProgress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp),
                color = ColorStyle.PURPLE_400,
                trackColor = ColorStyle.GRAY_200
            )
        } else {
            Spacer(modifier = Modifier.height(0.dp))
        }
    }
}