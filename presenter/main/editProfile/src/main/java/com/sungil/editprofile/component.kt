package com.sungil.editprofile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.core.AppTextStyles

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar(
    title: String,
    onBackClick: () -> Unit,
) {
    CenterAlignedTopAppBar(
        modifier = Modifier
            .border(width = 1.dp, color = Color(0xFFEFEFEF))
            .fillMaxWidth()
            .padding(start = 5.dp, end = 16.dp),
        title = {
            Text(
                text = title,
                style = AppTextStyles.TITLE_20_28_SEMI,
                color = Color(0xFF000000),
                textAlign = TextAlign.Center
            )
        },
        navigationIcon = {
            Image(
                painter = painterResource(id = R.drawable.ic_back_gray),
                contentDescription = "뒤로가기",
                modifier = Modifier
                    .padding(12.dp)
                    .size(24.dp)
                    .clickable { onBackClick() }
            )
        },
        actions = {},
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.White
        )
    )
}

@Composable
fun CustomLittleTitleText(
    text : String,
    color : Long
){
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = text,
        style = AppTextStyles.CAPTION_12_18_SEMI,
        color = Color(color)
    )
}

@Composable
fun CustomProfileItemWithImage(
    title: String,
    textColor: Long,
    subTitle: String,
    subTitleColor: Long,
    imageResId: Int? = null,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = AppTextStyles.BODY_14_20_MEDIUM,
            color = Color(textColor)
        )
        Spacer(modifier = Modifier.weight(1f))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (imageResId != null) {
                Image(
                    painter = painterResource(id = imageResId),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
            }
            Text(
                text = subTitle,
                style = AppTextStyles.BODY_14_20_MEDIUM,
                color = Color(subTitleColor)
            )
        }
    }
}


@Composable
fun CustomProfileItemWithMore(
    title: String,
    textColor: Long,
    subTitle: String,
    subTitleColor: Long,
    buttonClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = AppTextStyles.BODY_14_20_MEDIUM,
            color = Color(textColor)
        )

        Spacer(modifier = Modifier.weight(1f))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = subTitle,
                style = AppTextStyles.BODY_14_20_MEDIUM,
                color = Color(subTitleColor)
            )

            Spacer(modifier = Modifier.width(4.dp))

            Image(
                painter = painterResource(id = R.drawable.ic_right_out),
                contentDescription = "상세정보",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { buttonClick() }
            )
        }
    }
}

@Composable
fun CustomTwoText(
    firstText : String,
    firstTextColor : Long,
    subText : String,
    subTextColor : Long
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = firstText,
            style = AppTextStyles.BODY_14_20_MEDIUM,
            color = Color(firstTextColor)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.wrapContentWidth(Alignment.End)
        ) {
            Text(
                text = subText,
                style = AppTextStyles.BODY_14_20_MEDIUM,
                color = Color(subTextColor)
            )

        }
    }
}
@Composable
fun GraySpacer() {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(8.dp)
            .background(Color(0xFFEFEFEF))
    )
}