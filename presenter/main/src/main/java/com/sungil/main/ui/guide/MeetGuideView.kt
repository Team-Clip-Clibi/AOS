package com.sungil.main.ui.guide

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.AppTextStyles
import com.example.core.ColorStyle
import com.example.core.TopAppBarWithCloseButton
import com.sungil.main.R

@Composable
internal fun MeetGuideView(onClose: () -> Unit = {}) {
    Scaffold(topBar = {
        TopAppBarWithCloseButton(
            title = stringResource(R.string.top_meet_guide),
            onBackClick = { onClose() },
            isNavigationShow = false
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
            HardCodedTimelineView()

        }
    }
}

@Composable
fun HardCodedTimelineView() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        TimeLineNodeManual(
            number = "1",
            contents = GuideInfo(
                title = stringResource(R.string.guide_ready_title),
                content = listOf(
                    ContentInfo(
                        title = stringResource(R.string.guide_ready_title_first),
                        content = stringResource(R.string.guide_ready_content_first)
                    ),
                    ContentInfo(
                        title = stringResource(R.string.guide_ready_title_sec),
                        content = stringResource(R.string.guide_ready_content_sec)
                    ),
                    ContentInfo(
                        title = stringResource(R.string.guide_ready_title_third),
                        content = stringResource(R.string.guide_ready_content_third)
                    )
                )
            ),
            isLast = false
        )
        Spacer(modifier = Modifier.height(12.dp))
        TimeLineNodeManual(
            number = "2",
            contents = GuideInfo(
                title = stringResource(R.string.guide_rule_title),
                content = listOf(
                    ContentInfo(
                        title = stringResource(R.string.guide_rule_title_first),
                        content = stringResource(R.string.guide_rule_content_first)
                    ),
                    ContentInfo(
                        title = stringResource(R.string.guide_rule_title_sec),
                        content = stringResource(R.string.guide_rule_content_sec)
                    ),
                    ContentInfo(
                        title = stringResource(R.string.guide_rule_title_third),
                        content = stringResource(R.string.guide_rule_content_third)
                    ),
                    ContentInfo(
                        title = stringResource(R.string.guide_rule_title_last),
                        content = stringResource(R.string.guide_rule_content_last)
                    )
                )
            ),
            isLast = false
        )
        Spacer(modifier = Modifier.height(12.dp))
        TimeLineNodeManual(
            number = "3",
            contents = GuideInfo(
                title = stringResource(R.string.guide_note_title),
                content = listOf(
                    ContentInfo(
                        title = stringResource(R.string.guide_note_title_first),
                        content = stringResource(R.string.guide_note_content_first)
                    ),
                    ContentInfo(
                        title = stringResource(R.string.guide_note_title_sec),
                        content = stringResource(R.string.guide_note_content_sec)
                    )
                )
            ),
            isLast = false
        )
        Spacer(modifier = Modifier.height(12.dp))
        TimeLineNodeManual(
            number = "4",
            contents = GuideInfo(
                title = stringResource(R.string.guide_additional_title),
                content = listOf(
                    ContentInfo(
                        title = stringResource(R.string.guide_additional_title_first),
                        content = stringResource(R.string.guide_additional_content_first)
                    ),
                    ContentInfo(
                        title = stringResource(R.string.guide_additional_title_sec),
                        content = stringResource(R.string.guide_additional_content_sec)
                    )
                )
            ),
            isLast = false
        )
    }
}


@Composable
fun TimeLineNodeManual(
    number: String,
    contents: GuideInfo,
    isLast: Boolean,
) {
    Row(
        verticalAlignment = Alignment.Top
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(
                        color = ColorStyle.GRAY_200,
                        shape = RoundedCornerShape(size = 100.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = number,
                    style = AppTextStyles.SUBTITLE_16_24_SEMI,
                    color = ColorStyle.GRAY_600
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            if (!isLast) {
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .height(80.dp)
                        .background(color = ColorStyle.GRAY_200)
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(title, fontSize = 16.sp, color = Color.Black)
            Spacer(modifier = Modifier.height(8.dp))
            contents.forEach {
                Row(modifier = Modifier.padding(bottom = 4.dp)) {
                    Text("• ", fontSize = 14.sp)
                    Text(it, fontSize = 14.sp)
                }
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