package com.clip.core

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun TopAppbar(
    title: String,
    currentPage: Int = 0,
    totalPage: Int = 0,
    onBackClick: () -> Unit,
    isPageShow: Boolean = false,
    isBackVisible: Boolean = true,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .height(60.dp)
            .background(color = ColorStyle.WHITE_100)
            .drawBehind {
                val strokeWidth = 1.dp.toPx()
                val y = size.height - strokeWidth / 2
                drawLine(
                    color = ColorStyle.GRAY_100,
                    start = Offset(0f, y),
                    end = Offset(size.width, y),
                    strokeWidth = strokeWidth
                )
            }
            .padding(start = 5.dp, end = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isBackVisible) {
                Image(
                    painter = painterResource(id = R.drawable.ic_back_black),
                    contentDescription = "go back",
                    modifier = Modifier
                        .size(48.dp)
                        .padding(12.dp)
                        .clickable(
                            onClick = onBackClick,
                            enabled = true,
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        )
                )
            } else {
                Spacer(modifier = Modifier.width(48.dp))
            }
            Spacer(Modifier.weight(1f))
            if (isPageShow && totalPage != 0) {
                Text(
                    text = "$currentPage/$totalPage",
                    style = AppTextStyles.CAPTION_12_18_SEMI,
                    color = ColorStyle.PURPLE_400
                )
            }
        }

        Text(
            text = title,
            style = AppTextStyles.TITLE_20_28_SEMI,
            color = ColorStyle.GRAY_800,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun TopAppbarClose(
    title: String,
    onBackClick: () -> Unit,
    isNavigationShow: Boolean = true,
    isActionShow: Boolean = true,
    tint: Color = ColorStyle.GRAY_300,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .height(60.dp)
            .background(color = ColorStyle.WHITE_100)
            .drawBehind {
                val strokeWidth = 1.dp.toPx()
                val y = size.height - strokeWidth / 2
                drawLine(
                    color = ColorStyle.GRAY_200,
                    start = Offset(0f, y),
                    end = Offset(size.width, y),
                    strokeWidth = strokeWidth
                )
            }
            .padding(start = 5.dp, end = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isNavigationShow) {
                Image(
                    painter = painterResource(id = R.drawable.ic_back_black),
                    contentDescription = "close",
                    modifier = Modifier
                        .padding(12.dp)
                        .size(48.dp)
                        .clickable(
                            onClick = onBackClick,
                            enabled = true,
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        )
                )
            } else {
                Spacer(modifier = Modifier.width(48.dp))
            }
            Spacer(Modifier.weight(1f))
            if (isActionShow) {
                Image(
                    painter = painterResource(id = R.drawable.ic_close),
                    contentDescription = "close",
                    modifier = Modifier
                        .padding(12.dp)
                        .size(48.dp)
                        .clickable(
                            onClick = onBackClick,
                            enabled = true,
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ),
                    colorFilter = ColorFilter.tint(tint)
                )
            } else {
                Spacer(modifier = Modifier.width(48.dp))
            }
        }
        Text(
            text = title,
            style = AppTextStyles.TITLE_20_28_SEMI,
            textAlign = TextAlign.Center,
            color = ColorStyle.GRAY_800,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun TopAppBarWithProgress(
    title: String,
    currentPage: Int,
    totalPage: Int,
    onBackClick: () -> Unit,
) {
    val animatedProgress by animateFloatAsState(
        targetValue = if (currentPage >= 0) currentPage / totalPage.toFloat() else 0f,
        animationSpec = tween(durationMillis = 500),
        label = "progress"
    )

    Column {
        TopAppbar(
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


