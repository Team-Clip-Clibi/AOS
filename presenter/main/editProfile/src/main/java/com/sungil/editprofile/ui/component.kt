package com.sungil.editprofile.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.core.AppTextStyles
import com.example.core.ColorStyle
import com.sungil.editprofile.JOB
import com.sungil.editprofile.R


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

@Composable
fun JobGridSelector(
    selectedJobs: JOB,
    onJobToggle: (JOB) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier
    ) {
        items(JOB.entries.toList()) { job ->
            val isSelected = selectedJobs == job
            if(job != JOB.NONE){
                OutlinedButton(
                    onClick = {
                        onJobToggle(job)
                    },
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = if (isSelected) Color(0xFFF9F0FF) else Color(0xFFF7F7F7),
                        contentColor = Color.Black
                    ),
                    border = BorderStroke(
                        1.dp,
                        if (isSelected) Color(0xFFD3ADF7) else Color.Transparent
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .width(175.dp)
                        .height(48.dp)

                ) {
                    Text(
                        text = job.displayName,
                        style = AppTextStyles.BODY_14_20_MEDIUM,
                        color = Color(0xFF171717)
                    )
                }
            }
        }
    }
}


@Composable
fun CustomDialog(
    onDismiss: () -> Unit,
    buttonClick: () -> Unit,
    titleText: String,
    contentText: String,
    buttonText: String,
    subButtonText: String,
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = ColorStyle.GRAY_100,
                    shape = RoundedCornerShape(size = 24.dp)
                )
                .width(324.dp)
                .wrapContentHeight()
                .background(
                    color = ColorStyle.WHITE_100,
                    shape = RoundedCornerShape(size = 24.dp)
                )
                .padding(24.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(ColorStyle.WHITE_100),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = titleText,
                    style = AppTextStyles.TITLE_20_28_SEMI,
                    color = ColorStyle.GRAY_800,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = contentText,
                    style = AppTextStyles.BODY_14_20_MEDIUM,
                    color = ColorStyle.GRAY_600,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = buttonClick,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ColorStyle.PURPLE_400
                    )
                ) {
                    Text(
                        text = buttonText,
                        style = AppTextStyles.SUBTITLE_16_24_SEMI,
                        color = ColorStyle.WHITE_100,
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = onDismiss,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        ColorStyle.GRAY_200
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text(
                        text = subButtonText,
                        style = AppTextStyles.SUBTITLE_16_24_SEMI,
                        color = ColorStyle.GRAY_800,
                    )
                }
            }
        }
    }
}
