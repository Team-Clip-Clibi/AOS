package com.sungil.onethingmatch.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.util.lerp
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.core.AppTextStyles
import com.example.core.ButtonPurple400TITLE
import com.sungil.onethingmatch.R
import com.sungil.onethingmatch.component.CustomButton
import kotlin.math.absoluteValue

@Composable
internal fun IntroView(
    goOneThingPage: () -> Unit,
) {
    val pageState = rememberPagerState(pageCount = {
        3
    })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(start = 17.dp, end = 16.dp, top = 40.dp, bottom = 12.dp)
            .navigationBarsPadding()
    ) {
        Text(
            text = stringResource(R.string.txt_intro_title),
            style = AppTextStyles.HEAD_28_40_BOLD,
            color = Color(0xFF171717)
        )
        Text(
            text = stringResource(R.string.txt_intro_sub_title),
            style = AppTextStyles.SUBTITLE_16_24_SEMI,
            color = Color(0xFF666666)
        )
        Spacer(modifier = Modifier.height(16.dp))
        HorizontalPager(
            state = pageState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
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
                        .background(color = Color(0xFFF7F7F7))
                ) {

                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.txt_intro_below_page),
            style = AppTextStyles.SUBTITLE_18_26_SEMI,
            color = Color(0xFF171717),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(47.dp))

        ButtonPurple400TITLE(
            onClick = goOneThingPage,
            buttonText = stringResource(R.string.btn_next),
            modifier = Modifier.fillMaxWidth()
        )

    }
}