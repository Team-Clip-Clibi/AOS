package com.sungil.main.bottomSheetView

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.example.core.AppTextStyles
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource

@Composable
internal fun EndMatchView(onClick: () -> Unit) {
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
                    bottom = paddingValues.calculateBottomPadding(),
                    end = 24.dp
                )
        ) {
            TopView()
            ContentView()
        }
    }
}

@Composable
private fun TopView() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(R.string.match_start_end),
            style = AppTextStyles.CAPTION_12_18_SEMI,
            color = ColorStyle.PURPLE_400
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = stringResource(R.string.match_end_content),
            style = AppTextStyles.HEAD_24_34_BOLD,
            color = ColorStyle.GRAY_800
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = stringResource(R.string.match_end_content_sub),
            style = AppTextStyles.SUBTITLE_16_24_SEMI,
            color = ColorStyle.GRAY_700
        )
    }
}

@Composable
private fun ContentView() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(460.dp)
            .background(color = ColorStyle.PURPLE_100),
        verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_match_end),
            contentDescription = stringResource(R.string.match_end_image_content),
            modifier = Modifier
                .fillMaxWidth()
                .height(223.dp)
                .padding(start = 15.dp, top = 5.dp, end = 15.dp, bottom = 38.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = stringResource(R.string.match_end_image_content),
            style = AppTextStyles.TITLE_20_28_SEMI,
            color = ColorStyle.GRAY_800
        )
    }
}