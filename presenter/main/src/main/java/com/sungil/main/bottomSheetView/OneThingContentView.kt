package com.sungil.main.bottomSheetView

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core.ButtonXXLPurple400
import com.example.core.ColorStyle
import com.sungil.domain.model.OneThingContent
import com.sungil.main.R
import androidx.compose.material3.Text
import com.example.core.AppTextStyles

@Composable
internal fun OneThingContentView(onClick: () -> Unit, oneThingContent: List<OneThingContent>) {
    val backgroundColors = listOf(
        ColorStyle
    )
    Scaffold(
        bottomBar = {
            Column(modifier = Modifier.fillMaxWidth()) {
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = ColorStyle.GRAY_200
                )
                Spacer(modifier = Modifier.height(10.dp))
                ButtonXXLPurple400(
                    buttonText = stringResource(R.string.match_start_next_btn),
                    onClick = onClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 17.dp, end = 16.dp),
                )
            }
        }
    ) { paddingValues ->
        OneThingContentView(data = oneThingContent)
    }
}

@Composable
private fun OneThingContentView(data: List<OneThingContent>) {
    val color = listOf(
        ColorStyle.ORANGE_100,
        ColorStyle.GREEN_100,
        ColorStyle.BLUE_100,
        ColorStyle.YELLOW_100,
        ColorStyle.PURPLE_200,
        ColorStyle.CORAL_100,
        ColorStyle.MINT_100,
        ColorStyle.PINK_100
    )
    val pagerState = rememberPagerState(pageCount = { data.size })
    Column(modifier = Modifier
        .width(200.dp)
        .height(400.dp)) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 20.dp)
        ) { page ->
            val item = data[page]
            val backgroundColor = color[page % color.size]
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = backgroundColor, shape = RoundedCornerShape(20.dp))
                    .padding(start = 28.dp, top = 28.dp, end = 28.dp, bottom = 20.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = page.toString(),
                    style = AppTextStyles.HEAD_24_34_BOLD,
                    color = ColorStyle.GRAY_800
                )
                Spacer(modifier = Modifier.height(28.dp))
                Text(
                    text = item.contentCategory,
                    style = AppTextStyles.TITLE_20_28_SEMI,
                    color = ColorStyle.GRAY_800
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = item.content,
                    style = AppTextStyles.HEAD_24_34_BOLD,
                    color = ColorStyle.GRAY_800,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                Text(
                    text = item.nickName,
                    style = AppTextStyles.SUBTITLE_16_24_SEMI,
                    color = ColorStyle.GRAY_800
                )
            }
        }
    }

}