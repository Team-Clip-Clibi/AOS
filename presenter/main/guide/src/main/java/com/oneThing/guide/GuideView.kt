package com.oneThing.guide

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.AppTextStyles
import com.example.core.ColorStyle
import com.example.core.TopAppBarWithCloseButton

@Composable
internal fun GuideView(onClose: () -> Unit) {
    Scaffold(topBar = {
        TopAppBarWithCloseButton(
            title = stringResource(R.string.top_meet_guide),
            onBackClick = { onClose() },
            isNavigationShow = false,
            tint = ColorStyle.GRAY_500
        )
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = ColorStyle.WHITE_100)
                .padding(
                    top = paddingValues.calculateTopPadding() + 24.dp,
                    start = 17.dp,
                    end = 16.dp,
                    bottom = paddingValues.calculateBottomPadding() + 32.dp
                )
                .verticalScroll(rememberScrollState())
        ) {
            DynamicTimelineView(onBackClick = onClose)
        }
    }
}

@Composable
fun DynamicTimelineView(onBackClick : () -> Unit) {
    val context = LocalContext.current
    BackHandler(enabled = true) {
        onBackClick()
    }
    val timelineList = listOf(
        GuideInfo(
            title = context.getString(R.string.guide_ready_title),
            content = listOf(
                ContentInfo(
                    title = context.getString(R.string.guide_ready_title_first),
                    content = context.getString(R.string.guide_ready_content_first)
                ),
                ContentInfo(
                    title = context.getString(R.string.guide_ready_title_sec),
                    content = context.getString(R.string.guide_ready_content_sec)
                ),
                ContentInfo(
                    title = context.getString(R.string.guide_ready_title_third),
                    content = context.getString(R.string.guide_ready_content_third)
                )
            )
        ),
        GuideInfo(
            title = context.getString(R.string.guide_rule_title),
            content = listOf(
                ContentInfo(
                    title = context.getString(R.string.guide_rule_title_first),
                    content = context.getString(R.string.guide_rule_content_first)
                ),
                ContentInfo(
                    title = context.getString(R.string.guide_rule_title_sec),
                    content = context.getString(R.string.guide_rule_content_sec)
                ),
                ContentInfo(
                    title = context.getString(R.string.guide_rule_title_third),
                    content = context.getString(R.string.guide_rule_content_third)
                ),
                ContentInfo(
                    title = context.getString(R.string.guide_rule_title_last),
                    content = context.getString(R.string.guide_rule_content_last)
                )
            )
        ),
        GuideInfo(
            title = context.getString(R.string.guide_note_title),
            content = listOf(
                ContentInfo(
                    title = context.getString(R.string.guide_note_title_first),
                    content = context.getString(R.string.guide_note_content_first)
                ),
                ContentInfo(
                    title = context.getString(R.string.guide_note_title_sec),
                    content = context.getString(R.string.guide_note_content_sec)
                )
            )
        ),
        GuideInfo(
            title = context.getString(R.string.guide_additional_title),
            content = listOf(
                ContentInfo(
                    title = context.getString(R.string.guide_additional_title_first),
                    content = context.getString(R.string.guide_additional_content_first)
                ),
                ContentInfo(
                    title = context.getString(R.string.guide_additional_title_sec),
                    content = context.getString(R.string.guide_additional_content_sec)
                )
            )
        )
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 17.dp)
    ) {
        timelineList.forEachIndexed { index, guideInfo ->
            TimeLineNodeManual(
                number = "${index + 1}",
                contents = guideInfo,
                isLast = index == timelineList.lastIndex
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun TimeLineNodeManual(
    number: String,
    contents: GuideInfo,
    isLast: Boolean,
) {
    var contentHeightPx by remember { mutableIntStateOf(0) }
    Row(
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .alignBy(FirstBaseline)
                .drawBehind {
                    val circleRadius = size.width / 2f
                    val center = Offset(circleRadius, circleRadius)
                    drawCircle(
                        color = ColorStyle.GRAY_200,
                        radius = circleRadius,
                        center = center
                    )
                    if (!isLast) {
                        val lineStartY = center.y + 24.dp.toPx()
                        val lineEndY = contentHeightPx.toFloat()

                        drawLine(
                            color = ColorStyle.GRAY_200,
                            start = Offset(center.x, lineStartY),
                            end = Offset(center.x, lineEndY),
                            strokeWidth = 3.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = number,
                style = AppTextStyles.SUBTITLE_16_24_SEMI,
                color = ColorStyle.GRAY_600
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(
            modifier = Modifier
                .weight(1f)
                .alignBy(FirstBaseline)
                .onGloballyPositioned { coordinates ->
                    contentHeightPx = coordinates.size.height
                }
        ) {
            Text(
                text = contents.title,
                style = AppTextStyles.SUBTITLE_18_26_SEMI,
                color = ColorStyle.GRAY_800
            )
            Spacer(modifier = Modifier.height(15.dp))
            contents.content.forEach { data ->
                Row {
                    Text("• ", fontSize = 14.sp)
                    Text(
                        text = data.title,
                        style = AppTextStyles.BODY_14_20_MEDIUM,
                        color = ColorStyle.GRAY_800
                    )
                }
                Text(
                    text = data.content,
                    style = AppTextStyles.BODY_14_20_REGULAR,
                    color = ColorStyle.GRAY_800,
                    modifier = Modifier.padding(start = 10.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}


data class GuideInfo(
    val title: String,
    val content: List<ContentInfo>,
)

data class ContentInfo(
    val title: String,
    val content: String,
)