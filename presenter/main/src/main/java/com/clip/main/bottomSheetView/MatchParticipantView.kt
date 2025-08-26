package com.clip.main.bottomSheetView

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.clip.core.ColorStyle
import com.clip.main.R
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.util.fastForEachIndexed
import com.clip.core.AppTextStyles
import com.clip.core.ButtonXXL

@Composable
internal fun MatchParticipantView(onClick: () -> Unit, participant: List<String>) {
    Scaffold(
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(bottom = 8.dp)
            ) {
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = ColorStyle.GRAY_200
                )
                Spacer(modifier = Modifier.height(10.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(start = 17.dp, end = 16.dp)
                ) {
                    ButtonXXL(
                        text = stringResource(R.string.match_start_next_btn),
                        onClick = onClick,
                    )
                }
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
                text = stringResource(R.string.match_start_participants_content),
                style = AppTextStyles.HEAD_24_34_BOLD,
                color = ColorStyle.GRAY_800
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = stringResource(R.string.match_start_participants_content_sub),
                style = AppTextStyles.SUBTITLE_16_24_SEMI,
                color = ColorStyle.GRAY_700
            )
            Spacer(modifier = Modifier.height(20.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(bottom = 24.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                ParticipantsView(data = participant)
            }
        }
    }
}

@Composable
private fun ParticipantsView(data: List<String>) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        data.fastForEachIndexed { index, person ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(68.dp)
                    .background(color = ColorStyle.GRAY_100, shape = RoundedCornerShape(16.dp))
                    .border(
                        width = 1.dp,
                        color = ColorStyle.GRAY_200,
                        shape = RoundedCornerShape(size = 16.dp)
                    )
                    .padding(start = 16.dp, top = 14.dp, end = 16.dp, bottom = 14.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp, Alignment.Start),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                PersonNumberView(index = (index + 1).toString())
                Text(
                    text = person,
                    style = AppTextStyles.TITLE_20_28_SEMI,
                    color = ColorStyle.GRAY_800
                )
            }
        }
    }
}


@Composable
private fun PersonNumberView(index: String) {
    Column(
        modifier = Modifier
            .width(40.dp)
            .height(40.dp)
            .background(color = ColorStyle.WHITE_100, shape = RoundedCornerShape(16.dp)),
        verticalArrangement = Arrangement.spacedBy(0.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = index,
            style = AppTextStyles.TITLE_20_28_SEMI,
            color = ColorStyle.GRAY_800
        )
    }
}