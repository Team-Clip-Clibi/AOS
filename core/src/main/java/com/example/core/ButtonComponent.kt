package com.example.core

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.ui.text.style.TextAlign

@Composable
fun ButtonXXL(
    onClick: () -> Unit,
    text: String,
    isEnable: Boolean = true,
    useBorder: Boolean = false,
    borderColor: Color = ColorStyle.PURPLE_400,
    enableTextColor: Color = ColorStyle.WHITE_100,
    disEnableTextColor: Color = ColorStyle.GRAY_800,
    enableButtonColor: Color = ColorStyle.PURPLE_400,
    disEnableButtonColor: Color = ColorStyle.GRAY_200,
    enableContentColor: Color = ColorStyle.WHITE_100,
    disEnableContentColor: Color = ColorStyle.GRAY_200,
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .then(
                if (useBorder) {
                    Modifier.border(
                        width = 1.dp,
                        color = borderColor,
                        shape = RoundedCornerShape(12.dp)
                    )
                } else {
                    Modifier
                }
            ),
        onClick = onClick,
        enabled = isEnable,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isEnable) enableButtonColor else disEnableButtonColor,
            contentColor = if (isEnable) enableContentColor else disEnableContentColor
        )
    ) {
        Text(
            text = text,
            color = if (isEnable) enableTextColor else disEnableTextColor,
            style = AppTextStyles.TITLE_20_28_SEMI,
        )
    }
}

@Composable
fun ButtonM(
    onClick: () -> Unit,
    text: String,
    containerColor: Color = ColorStyle.PURPLE_400,
    contentColor: Color = ColorStyle.PURPLE_400,
    textColor: Color = ColorStyle.WHITE_100,
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        onClick = onClick,
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            text = text,
            style = AppTextStyles.SUBTITLE_16_24_SEMI,
            color = textColor
        )
    }
}

@Composable
fun ButtonL(
    onClick: () -> Unit,
    text: String,
    textColor: Color = ColorStyle.GRAY_800,
    textCenter: Boolean = true,
    isEnable: Boolean = true,
    isSelected: Boolean = true,
    borderUse: Boolean = false,
    borderColor: Color = ColorStyle.PURPLE_200,
    buttonColor: Color = ColorStyle.PURPLE_400,
    disEnableButtonColor: Color = ColorStyle.GRAY_300,
    contentColor: Color = Color.Black,
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .then(
                if (borderUse) {
                    Modifier.border(
                        width = 1.dp,
                        color = borderColor,
                        shape = RoundedCornerShape(8.dp)
                    )
                } else {
                    Modifier
                }
            ),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isEnable || isSelected) buttonColor else disEnableButtonColor,
            contentColor = contentColor
        ),
        onClick = onClick,
        enabled = isEnable,
        contentPadding = PaddingValues(
            start = 17.dp,
            end = 16.dp
        )
    ) {
        Text(
            text = text,
            color = textColor,
            style = AppTextStyles.BODY_14_20_MEDIUM,
            modifier = Modifier.fillMaxWidth(),
            textAlign = if (textCenter) TextAlign.Center else TextAlign.Start
        )
    }
}

@Composable
fun ButtonCheckBoxLeftL(
    content: String,
    isChecked: Boolean,
    onCheckChange: (Boolean) -> Unit,
    checkImageShow : Boolean = true
) {
    val shape = RoundedCornerShape(8.dp)
    val borderColor = if (isChecked) ColorStyle.PURPLE_200 else Color.Transparent
    val backgroundColor = if (isChecked) ColorStyle.PURPLE_100 else ColorStyle.GRAY_100

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = shape
            )
            .background(
                color = backgroundColor,
                shape = shape
            )
            .padding(start = 17.dp, end = 16.dp)
            .clickable { onCheckChange(!isChecked) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        if(checkImageShow){
            CircularCheckBoxLarge(
                checked = isChecked,
                onCheckChange = onCheckChange
            )
            Spacer(modifier = Modifier.width(14.dp))
        }
        Text(
            text = content,
            style = AppTextStyles.BODY_14_20_MEDIUM,
            color = ColorStyle.GRAY_800,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(8.dp))
    }
}

@Composable
fun CircularCheckBoxLarge(
    checked: Boolean,
    onCheckChange: (Boolean) -> Unit,
) {
    Box(
        modifier = Modifier
            .size(24.dp)
            .background(
                color = if (checked) ColorStyle.PURPLE_400 else ColorStyle.GRAY_400,
                shape = CircleShape
            )
            .clickable { onCheckChange(!checked) },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_check),
            contentDescription = "check",
            tint = ColorStyle.WHITE_100,
            modifier = Modifier.size(16.dp)
        )
    }
}



@Composable
fun ButtonSmall(
    text: String,
    isEnable: Boolean = true,
    onClick: () -> Unit,
) {
    Button(
        modifier = Modifier
            .width(104.dp)
            .height(36.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            when (isEnable) {
                true -> ColorStyle.PURPLE_100
                false -> ColorStyle.GRAY_200
            }
        ),
        onClick = onClick,
        enabled = isEnable
    ) {
        Text(
            text = text,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = AppTextStyles.BODY_14_20_MEDIUM,
            modifier = Modifier
                .width(52.dp)
                .height(20.dp),
            color = when (isEnable) {
                true -> ColorStyle.PURPLE_400
                false -> ColorStyle.GRAY_800
            }
        )
    }
}

