package com.oneThing.random.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.example.core.AppTextStyles
import com.example.core.ColorStyle
import com.oneThing.random.R
import kotlin.math.absoluteValue

@Composable
internal fun RandomMatchIntro(
    onBackClick: () -> Unit,
) {
    val pageState = rememberPagerState(pageCount = { 1 })
    BackHandler(enabled = true) {
        onBackClick()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorStyle.WHITE_100)
            .padding(top = 16.dp, start = 17.dp, end = 16.dp)
    ) {
        Text(
            text = stringResource(R.string.random_intro_title),
            style = AppTextStyles.HEAD_28_40_BOLD,
            color = ColorStyle.GRAY_800,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = stringResource(R.string.random_intro_subtitle),
            style = AppTextStyles.SUBTITLE_16_24_SEMI,
            color = ColorStyle.GRAY_600,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        HorizontalPager(
            state = pageState,
            modifier = Modifier
                .fillMaxWidth()
                .height(460.dp)
        ) { page ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer {
                        val pageOffset = (
                                (pageState.currentPage - page) + pageState.currentPageOffsetFraction
                                ).absoluteValue
                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    },
                shape = RoundedCornerShape(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = ColorStyle.PURPLE_100),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_random_match_intro),
                            contentDescription = "intro image",
                            modifier = Modifier
                                .width(267.dp)
                                .height(224.dp)
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                        Text(
                            text = stringResource(R.string.random_intro_content),
                            style = AppTextStyles.TITLE_20_28_SEMI,
                            color = ColorStyle.GRAY_800,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}