package com.clip.onethingmatch.ui.intro

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
import androidx.compose.ui.util.lerp
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.clip.core.AppTextStyles
import com.clip.core.ColorStyle
import com.clip.onethingmatch.R
import kotlin.math.absoluteValue

@Composable
internal fun IntroView(onBackClick : () -> Unit) {
    BackHandler(enabled = true) {
        onBackClick()
    }
    val pageState = rememberPagerState(pageCount = {
        1
    })
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorStyle.WHITE_100)
            .padding(
                top = 16.dp,
                start = 17.dp,
                end = 16.dp,
            )
    ) {
        Text(
            text = stringResource(R.string.txt_intro_title),
            style = AppTextStyles.HEAD_28_40_BOLD,
            color = ColorStyle.GRAY_800,
            modifier = Modifier.padding(start = 17.dp, end = 16.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = stringResource(R.string.txt_intro_sub_title),
            style = AppTextStyles.SUBTITLE_16_24_SEMI,
            color = ColorStyle.GRAY_600,
            modifier = Modifier.padding(start = 17.dp, end = 16.dp)
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
                            painter = painterResource(R.drawable.ic_onething_intro),
                            contentDescription = "intro image",
                            modifier = Modifier
                                .width(267.dp)
                                .height(224.dp)
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                        Text(
                            text = stringResource(R.string.txt_one_thing_intro_content),
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