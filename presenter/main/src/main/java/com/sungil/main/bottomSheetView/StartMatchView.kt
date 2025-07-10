package com.sungil.main.bottomSheetView

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core.ButtonXXLPurple400
import com.example.core.ColorStyle
import com.sungil.main.R
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import com.example.core.AppTextStyles

@Composable
internal fun StartMatchView(
    onClick: () -> Unit,
) {
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
                    buttonText = stringResource(R.string.match_start_first_btn),
                    onClick = onClick,
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
                text = stringResource(R.string.match_start_first),
                style = AppTextStyles.CAPTION_12_18_SEMI,
                color = ColorStyle.PURPLE_400
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = stringResource(R.string.match_start_first_content),
                style = AppTextStyles.SUBTITLE_16_24_SEMI,
                color = ColorStyle.GRAY_700
            )
            Spacer(modifier = Modifier.height(20.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(color = ColorStyle.PURPLE_100)
                    .padding(bottom = 56.dp),
                verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_match_start_first),
                    contentDescription = stringResource(R.string.match_start_first_image_content)
                )
                Text(
                    text = stringResource(R.string.match_start_first_image_content),
                    style = AppTextStyles.TITLE_20_28_SEMI,
                    color = ColorStyle.GRAY_800
                )
            }
        }
    }
}