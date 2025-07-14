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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core.ButtonXXLPurple400
import com.example.core.ColorStyle
import com.sungil.main.R
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import com.example.core.AppTextStyles

@Composable
internal fun ConversationMatchView(conversationData: List<String>, onClick: () -> Unit) {
    Scaffold(bottomBar = {
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
                    .padding(start = 17.dp, end = 16.dp)
            )
        }
    }) { paddingValues ->
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
            TopView()
            Spacer(modifier = Modifier.height(20.dp))
            ConversationView(data = conversationData)
        }
    }
}

@Composable
private fun TopView() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(R.string.match_start_ing),
            style = AppTextStyles.CAPTION_12_18_SEMI,
            color = ColorStyle.PURPLE_400
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = stringResource(R.string.match_conversation_content),
            style = AppTextStyles.HEAD_24_34_BOLD,
            color = ColorStyle.GRAY_800
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = stringResource(R.string.match_conversation_content_sub),
            style = AppTextStyles.SUBTITLE_16_24_SEMI,
            color = ColorStyle.GRAY_700
        )
    }
}

@Composable
private fun ConversationView(
    data: List<String>,
) {
    val pagerState = rememberPagerState(pageCount = { data.size })
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = ColorStyle.PURPLE_100, shape = RoundedCornerShape(20.dp))
                    .padding(20.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = (page + 1).toString(),
                    style = AppTextStyles.HEAD_24_34_BOLD,
                    color = ColorStyle.WHITE_100,
                    modifier = Modifier
                        .width(44.dp)
                        .height(44.dp)
                        .background(
                            color = ColorStyle.PURPLE_400,
                            shape = RoundedCornerShape(10.dp)
                        )
                )
                Spacer(modifier = Modifier.height(28.dp))
                Text(
                    text = data[page],
                    style = AppTextStyles.HEAD_24_34_BOLD,
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