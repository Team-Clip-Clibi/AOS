package com.sungil.main.bottomSheetView

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.style.TextAlign
import com.example.core.AppTextStyles

@Composable
internal fun OneThingContentView(
    onClick: () -> Unit,
    oneThingContent: List<OneThingContent>
) {
    val currentPage = remember { mutableIntStateOf(0) }
    val isLastPage = currentPage.intValue + 1 == oneThingContent.size

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
                    top = paddingValues.calculateTopPadding(),
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
        }
    }
}


@Composable
private fun OneThingContentPagerView(
    data: List<OneThingContent>,
    currentPage: MutableState<Int>
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
            .padding(bottom = 68.dp)
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
                    .fillMaxSize()
                    .background(color = backgroundColor, shape = RoundedCornerShape(20.dp))
                    .padding(28.dp),
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
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "${pagerState.currentPage + 1}/${data.size}",
            style = AppTextStyles.CAPTION_12_18_SEMI,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}
