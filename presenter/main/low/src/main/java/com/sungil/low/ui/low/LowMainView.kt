package com.sungil.low.ui.low

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sungil.low.R
import com.sungil.low.ui.CustomLowItem

@Composable
internal fun LowMainView(
    paddingValues: PaddingValues,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFFFFFFF))
            .padding(top = paddingValues.calculateTopPadding() + 32.dp, start = 17.dp, end = 16.dp)
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopCenter)
        ) {
            CustomLowItem(
                title = stringResource(R.string.txt_low_person),
                textColor = 0xFF171717,
                subTitle = "",
                subTitleColor = 0xFF171717,
                buttonClick = {}
            )
            CustomLowItem(
                title = stringResource(R.string.txt_low_guide),
                textColor = 0xFF171717,
                subTitle = "",
                subTitleColor = 0xFF171717,
                buttonClick = {}
            )
            CustomLowItem(
                title = stringResource(R.string.txt_low_com),
                textColor = 0xFF171717,
                subTitle = "",
                subTitleColor = 0xFF171717,
                buttonClick = {}
            )
        }
    }
}