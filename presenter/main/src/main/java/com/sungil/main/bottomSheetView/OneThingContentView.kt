package com.sungil.main.bottomSheetView

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core.ButtonXXLPurple400
import com.example.core.ColorStyle
import com.sungil.domain.model.OneThingContent
import com.sungil.main.R

@Composable
internal fun OneThingContentView(onClick: () -> Unit, oneThingContent: List<OneThingContent>) {
    val backgroundColors = listOf(
        ColorStyle
    )
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
                    buttonText = stringResource(R.string.match_start_next_btn),
                    onClick = onClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 17.dp, end = 16.dp),
                )
            }
        }
    ) { paddingValues ->

    }
}

@Composable
private fun OneThingContentView(data : List<OneThingContent>){
    val color = listOf(
        ColorStyle.ORANGE_100,
        ColorStyle.GREEN_100,
        ColorStyle.BLUE_100,
        ColorStyle.YELLOW_100,
        ColorStyle.PURPLE_200,
        ColorStyle.CORAL_100,
        ColorStyle.MINT_100,
        ColorStyle.PINK_100
    )
    val pagerState = rememberPagerState(pageCount = {data.size})

}