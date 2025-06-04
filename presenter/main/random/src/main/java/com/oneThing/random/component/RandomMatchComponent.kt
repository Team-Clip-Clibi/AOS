package com.oneThing.random.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core.ButtonXXLPurple400
import com.example.core.ButtonXXLWhite
import com.example.core.ColorStyle
import com.example.core.TopAppBarNumber
import com.oneThing.random.R

@Composable
fun TopAppBarWithProgress(
    title: String,
    currentPage: Int,
    totalPage: Int,
    onBackClick: () -> Unit,
) {
    val animatedProgress by animateFloatAsState(
        targetValue = if (currentPage >= 0) currentPage / totalPage.toFloat() else 0f,
        animationSpec = tween(durationMillis = 500),
        label = "progress"
    )

    Column {
        TopAppBarNumber(
            title = title,
            currentPage = if (currentPage >= 0) currentPage else 0,
            totalPage = totalPage,
            onBackClick = onBackClick
        )
        if (currentPage >= 0) {
            LinearProgressIndicator(
                progress = { animatedProgress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp),
                color = ColorStyle.PURPLE_400,
                trackColor = ColorStyle.GRAY_200
            )
        } else {
            Spacer(modifier = Modifier.height(0.dp))
        }
    }
}

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
        ButtonXXLPurple400(
            onClick = onClick,
            buttonText = buttonText,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 17.dp),
            isEnable = isEnable
        )
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
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ButtonXXLWhite(
                onClick = goMeeting,
                buttonText = stringResource(R.string.random_duplicate_button_meet),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 17.dp)
            )

            ButtonXXLPurple400(
                onClick = goHome,
                buttonText = stringResource(R.string.random_duplicate_button_home),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp)
            )
        }
    }
}