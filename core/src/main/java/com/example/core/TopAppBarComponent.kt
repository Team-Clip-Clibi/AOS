package com.example.core

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarNumber(
    title: String,
    currentPage: Int,
    totalPage: Int,
    onBackClick: () -> Unit,
    isPageTextShow : Boolean = false
) {
    CenterAlignedTopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = ColorStyle.GRAY_200),
        title = {
            Text(
                text = title,
                style = AppTextStyles.TITLE_20_28_SEMI,
                textAlign = TextAlign.Center,
                color = ColorStyle.GRAY_800
            )
        },
        navigationIcon = {
            Image(
                painter = painterResource(id = R.drawable.ic_back_black),
                contentDescription = "go back",
                modifier = Modifier
                    .padding(12.dp)
                    .size(24.dp)
                    .clickable { onBackClick() }
            )
        },
        actions = {
            Text(
                text = if (!isPageTextShow) "" else "$currentPage/$totalPage",
                style = AppTextStyles.CAPTION_12_18_SEMI,
                color = ColorStyle.PURPLE_400,
                modifier = Modifier.padding(end = 16.dp)
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = ColorStyle.WHITE_100
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarWithCloseButton(
    title: String,
    onBackClick: () -> Unit,
    isNavigationShow: Boolean = true,
    isActionShow: Boolean = true,
    tint : Color = ColorStyle.GRAY_300
) {
    Column {
        CenterAlignedTopAppBar(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .height(48.dp),
            title = {
                Text(
                    text = title,
                    style = AppTextStyles.TITLE_20_28_SEMI,
                    textAlign = TextAlign.Center,
                    color = ColorStyle.GRAY_800,
                    modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
                )
            },
            navigationIcon = {
                if (isNavigationShow) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clickable { onBackClick() }
                            .padding(12.dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_back_gray),
                            contentDescription = "go back",
                            modifier = Modifier
                                .padding(12.dp)
                                .size(24.dp)
                        )
                    }
                }
            },
            actions = {
                if (isActionShow) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_close),
                        contentDescription = "close",
                        modifier = Modifier
                            .padding(12.dp)
                            .size(24.dp)
                            .clickable { onBackClick() },
                        colorFilter = ColorFilter.tint(tint)
                    )
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = ColorStyle.WHITE_100
            )
        )

        HorizontalDivider(
            thickness = 1.dp,
            color = ColorStyle.GRAY_200
        )
    }
}
