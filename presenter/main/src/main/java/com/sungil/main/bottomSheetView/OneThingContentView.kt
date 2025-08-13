package com.sungil.main.bottomSheetView

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.util.lerp
import com.example.core.AppTextStyles
import com.sungil.domain.model.OneThingMap
import kotlin.math.absoluteValue

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
                    bottom = paddingValues.calculateBottomPadding()
                )
        ) {
            Text(
                text = stringResource(R.string.match_start_ing),
                style = AppTextStyles.CAPTION_12_18_SEMI,
                color = ColorStyle.PURPLE_400,
                modifier = Modifier.fillMaxWidth().padding(
                    start = 24.dp,
                    end = 24.dp,
                )
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = stringResource(R.string.match_start_oneThing_content),
                style = AppTextStyles.HEAD_24_34_BOLD,
                color = ColorStyle.GRAY_800,
                modifier = Modifier.fillMaxWidth().padding(
                    start = 24.dp,
                    end = 24.dp,
                )
            )
            Spacer(modifier = Modifier.height(20.dp))
            OneThingContentPagerView(
                data = oneThingContent,
                currentPage = currentPage
            )
            Text(
                text = "${currentPage.intValue + 1}/${oneThingContent.size}",
                style = AppTextStyles.CAPTION_12_18_SEMI,
                modifier = Modifier.fillMaxWidth().padding(
                    start = 24.dp,
                    end = 24.dp,
                ),
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
    val colors = listOf(
        ColorStyle.ORANGE_100, ColorStyle.GREEN_100, ColorStyle.BLUE_100, ColorStyle.YELLOW_100,
        ColorStyle.PURPLE_200, ColorStyle.CORAL_100, ColorStyle.MINT_100, ColorStyle.PINK_100
    )

    val pagerState = rememberPagerState(pageCount = { data.size })

    // composition 밖에서 업데이트
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { currentPage.value = it }
    }

    val density = LocalDensity.current
    val basePad = 24.dp
    val isScrolling = pagerState.isScrollInProgress
    val frac = pagerState.currentPageOffsetFraction
    // 방향: 오른쪽(다음 페이지)로 스와이프 중 -> +1, 왼쪽(이전 페이지) -> -1, 정지/미세 -> 0
    val dir = when {
        !isScrolling        -> 0
        frac < 0f           -> +1   // 오른쪽으로 스크롤 중
        frac > 0f           -> -1   // 왼쪽으로 스크롤 중
        else                -> 0
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 20.dp),
            // 정지 기준으로 양쪽 24dp 고정 → 폭/측정값 변동 없음
            contentPadding = PaddingValues(horizontal = basePad),
            pageSpacing = 12.dp // 카드 간 간격 12dp 유지
        ) { page ->
            val item = data[page]
            val bg = colors[page % colors.size]

            // 현재 페이지만 스크롤 방향으로 24dp 만큼 시각적 이동(패딩 채우기)
            val isCurrent = page == pagerState.currentPage
            val offsetX = if (isCurrent && dir != 0) {
                with(density) { (basePad * dir).roundToPx() }
            } else 0

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .offset { IntOffset(offsetX, 0) } // ← 레이아웃 크기 불변, 화면에서만 이동
                    .graphicsLayer {
                        // (선택) 페이드 효과 유지하고 싶다면 남겨두기, 불필요하면 지워도 됨
                        val pageOffset =
                            ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction)
                                .absoluteValue
                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    },
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = bg)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(28.dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                ) {
                    Box(
                        modifier = Modifier
                            .width(44.dp)
                            .height(44.dp)
                            .background(
                                color = ColorStyle.WHITE_100,
                                shape = RoundedCornerShape(10.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = (page + 1).toString(),
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
                        text = stringResource(R.string.match_start_oneThing_nickName, item.nickName),
                        style = AppTextStyles.SUBTITLE_16_24_SEMI,
                        color = ColorStyle.GRAY_800,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
