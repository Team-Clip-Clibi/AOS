package com.sungil.login.component

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.core.AppTextStyles
import com.example.core.ColorStyle
import com.sungil.domain.model.BannerData
import kotlin.math.absoluteValue

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun LoginPager(
    state: PagerState,
    data: List<BannerData>,
) {
    HorizontalPager(
        state = state,
        modifier = Modifier.fillMaxWidth()
            .height(400.dp)
    ) { page ->
        Card(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    val pageOffset = (
                            (state.currentPage - page) + state.currentPageOffsetFraction
                            ).absoluteValue
                    alpha = lerp(
                        start = 0.5f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                },
            shape = RoundedCornerShape(16.dp),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent)
            ) {
                GlideImage(
                    model = data[page].image,
                    contentDescription = data[page].headText,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize()
                )
                Column(
                modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 24.dp, start = 16.dp, end = 16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = data[page].headText ?: "",
                        style = AppTextStyles.HEAD_24_34_BOLD,
                        color = ColorStyle.GRAY_800,
                        textAlign = TextAlign.Center,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = data[page].subText ?: "",
                        style = AppTextStyles.SUBTITLE_16_24_SEMI,
                        color = ColorStyle.GRAY_800,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun PageIndicator(
    numberOfPages: Int,
    selectedPage: Int = 0
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            repeat(numberOfPages) {
                Indicator(
                    isSelected = it == selectedPage,
                    selectedColor = ColorStyle.PURPLE_300,
                    defaultColor = ColorStyle.GRAY_300,
                    defaultRadius = 10.dp,
                    selectedLength = 16.dp,
                )
            }
        }
    }
}

/**
 * pager indicator item
 */
@Composable
fun Indicator(
    isSelected: Boolean,
    selectedColor: Color,
    defaultColor: Color,
    defaultRadius: Dp,
    selectedLength: Dp,
    modifier: Modifier = Modifier.height(defaultRadius)
) {
    val width by animateDpAsState(
        targetValue = if (isSelected) selectedLength else defaultRadius,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
    )
    Box(
        modifier = modifier
            .width(width)
            .clip(CircleShape)
            .background(color = if (isSelected) selectedColor else defaultColor)
    )
}