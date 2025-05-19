package com.example.core

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
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
                color = Color(0xFF000000)
            )
        },
        navigationIcon = {
            Image(
                painter = painterResource(id = R.drawable.ic_back_gray),
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
                color = ColorStyle.PURPLE_400
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
    title : String,
    onBackClick: () -> Unit,
    isNavigationShow : Boolean = true,
    isActionShow : Boolean = true
){
    CenterAlignedTopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = ColorStyle.GRAY_200),
        title = {
            Text(
                text = title,
                style = AppTextStyles.TITLE_20_28_SEMI,
                textAlign = TextAlign.Center,
                color = Color(0xFF000000)
            )
        },
        navigationIcon = {
            if(isNavigationShow){
                Image(
                    painter = painterResource(id = R.drawable.ic_back_gray),
                    contentDescription = "go back",
                    modifier = Modifier
                        .padding(12.dp)
                        .size(24.dp)
                        .clickable { onBackClick() }
                )
            }
        },
        actions = {
            if(isActionShow){
                Image(
                    painter = painterResource(id = R.drawable.ic_close),
                    contentDescription = "go back",
                    modifier = Modifier
                        .padding(12.dp)
                        .size(24.dp)
                        .clickable { onBackClick() }
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = ColorStyle.WHITE_100
        )
    )
}