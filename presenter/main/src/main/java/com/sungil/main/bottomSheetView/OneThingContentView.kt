package com.sungil.main.bottomSheetView

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
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
import com.sungil.main.R
import androidx.compose.material3.Text
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.style.TextAlign
import com.example.core.AppTextStyles
import com.sungil.domain.model.OneThingMap

@Composable
internal fun OneThingContentView(
    onClick: () -> Unit,
    oneThingContent: List<OneThingMap>
) {
    val currentPage = remember { mutableIntStateOf(0) }
    val isLastPage = currentPage.intValue + 1 == oneThingContent.size

    Scaffold(
        bottomBar = {
            Column(modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(bottom = 8.dp)) {
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = ColorStyle.GRAY_200
                )
                Spacer(modifier = Modifier.height(10.dp))
                ButtonXXLPurple400(
                    buttonText = stringResource(R.string.match_start_next_btn),
                    onClick = onClick,
                    isEnable = isLastPage,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 17.dp, end = 16.dp),
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = ColorStyle.WHITE_100)
                .padding(
                    start = 24.dp,
                    end = 24.dp,
                    bottom = paddingValues.calculateBottomPadding()
                )
        ) {
            Text(
                text = stringResource(R.string.match_start_ing),
                style = AppTextStyles.CAPTION_12_18_SEMI,
                color = ColorStyle.PURPLE_400
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = stringResource(R.string.match_start_oneThing_content),
                style = AppTextStyles.HEAD_24_34_BOLD,
                color = ColorStyle.GRAY_800
            )
            Spacer(modifier = Modifier.height(20.dp))
            OneThingContentPagerView(
                data = oneThingContent,
                currentPage = currentPage
            )
            Text(
                text = "${currentPage.intValue + 1}/${oneThingContent.size}",
                style = AppTextStyles.CAPTION_12_18_SEMI,
                modifier = Modifier.fillMaxWidth(),
                color = ColorStyle.GRAY_500,
                textAlign = TextAlign.Center
            )

        }
    }
}


@Composable
private fun OneThingContentPagerView(
    data: List<OneThingMap>,
    currentPage: MutableState<Int>,
) {
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
    currentPage.value = pagerState.currentPage

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
    ) {
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
                    .fillMaxWidth()
                    .height(400.dp)
                    .background(color = backgroundColor, shape = RoundedCornerShape(20.dp))
                    .padding(28.dp),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Box(
                    modifier = Modifier
                        .width(44.dp)
                        .height(44.dp)
                        .background(color = ColorStyle.WHITE_100, shape = RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = (page+1).toString(),
                        style = AppTextStyles.HEAD_24_34_BOLD,
                        color = ColorStyle.GRAY_800
                    )
                }
                Spacer(modifier = Modifier.height(28.dp))
                Text(
                    text = item.nickName,
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
                    text = stringResource(R.string.match_start_oneThing_nickName , item.nickName),
                    style = AppTextStyles.SUBTITLE_16_24_SEMI,
                    color = ColorStyle.GRAY_800,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
