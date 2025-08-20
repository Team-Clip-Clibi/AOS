package com.oneThing.random.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.core.AppTextStyles
import com.example.core.ButtonXXL
import com.example.core.ColorStyle
import com.oneThing.random.R


@Composable
fun BottomBar(
    isEnable: Boolean,
    buttonText: String,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(bottom = 8.dp)
    ) {
        HorizontalDivider(
            thickness = 1.dp,
            color = ColorStyle.GRAY_200
        )
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(start = 16.dp, end = 17.dp)
        ) {
            ButtonXXL(
                onClick = onClick,
                text = buttonText,
                isEnable = isEnable
            )
        }
    }
}

@Composable
fun DuplicateBottomBar(
    goMeeting: () -> Unit,
    goHome: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(bottom = 8.dp)
    ) {
        HorizontalDivider(
            thickness = 1.dp,
            color = ColorStyle.GRAY_200
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(start = 17.dp, end = 16.dp, bottom = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(modifier = Modifier.weight(1f)) {
                ButtonXXL(
                    onClick = goMeeting,
                    text = stringResource(R.string.random_duplicate_button_meet),
                    useBorder = true,
                    enableButtonColor = ColorStyle.WHITE_100,
                    enableContentColor = ColorStyle.PURPLE_400
                )
            }
            Box(modifier = Modifier.weight(1f)) {
                ButtonXXL(
                    onClick = goHome,
                    text = stringResource(R.string.random_duplicate_button_home),
                )
            }
        }
    }
}

@Composable
fun EventView(
    title: String,
    content: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(68.dp)
            .background(color = ColorStyle.WHITE_100, shape = RoundedCornerShape(size = 12.dp))
            .padding(start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_discount),
            contentDescription = "discount",
            tint = Color.Unspecified,
            modifier = Modifier.size(36.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = title,
                style = AppTextStyles.CAPTION_12_18_SEMI,
                color = ColorStyle.PURPLE_400
            )
            Text(
                text = content,
                style = AppTextStyles.SUBTITLE_16_24_SEMI,
                color = ColorStyle.GRAY_800
            )
        }
    }
}

@Composable
fun RandomMatchDataView(
    nickName: String,
    time: String,
    location: String,
    address: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = ColorStyle.WHITE_100, shape = RoundedCornerShape(size = 16.dp))
            .padding(top = 18.dp, start = 16.dp, end = 16.dp, bottom = 18.dp)
    ) {
        Text(
            text = stringResource(R.string.random_before_pay_content_title, nickName),
            style = AppTextStyles.SUBTITLE_16_24_SEMI,
            color = ColorStyle.GRAY_800,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_time),
                    contentDescription = "time icon",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.random_before_pay_time),
                    style = AppTextStyles.SUBTITLE_16_24_SEMI,
                    color = ColorStyle.GRAY_800
                )
            }
            Text(
                text = time,
                style = AppTextStyles.SUBTITLE_16_24_SEMI,
                color = ColorStyle.GRAY_800
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        HorizontalDivider(
            thickness = 1.dp,
            color = ColorStyle.GRAY_200
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_location),
                    contentDescription = "location icon",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.random_before_pay_location),
                    style = AppTextStyles.SUBTITLE_16_24_SEMI,
                    color = ColorStyle.GRAY_800
                )
            }
            Column {
                Text(
                    text = location,
                    style = AppTextStyles.SUBTITLE_16_24_SEMI,
                    color = ColorStyle.GRAY_800
                )
                Text(
                    text = address,
                    style = AppTextStyles.SUBTITLE_16_24_SEMI,
                    color = ColorStyle.GRAY_800,
                )
            }
        }
    }
}

