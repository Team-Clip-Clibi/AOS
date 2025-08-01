package com.example.core

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.runtime.remember
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
import androidx.compose.material3.OutlinedButton

@Composable
fun ButtonXXLPurple400(
    onClick: () -> Unit,
    buttonText: String,
    modifier: Modifier,
    isEnable: Boolean = true
) {
    Button(
        modifier = modifier.height(60.dp),
        onClick = onClick,
        enabled = isEnable,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isEnable) ColorStyle.PURPLE_400 else ColorStyle.GRAY_200,
            contentColor = if (isEnable) ColorStyle.WHITE_100 else ColorStyle.GRAY_800
        )
    ) {
        Text(
            text = buttonText,
            style = AppTextStyles.TITLE_20_28_SEMI,
            color = when(isEnable){
                true ->{
                    ColorStyle.WHITE_100
                }
                else -> ColorStyle.GRAY_800
            }
        )
    }
}


@Composable
fun ButtonXXLWhite(
    onClick: () -> Unit,
    buttonText: String,
    modifier: Modifier,
) {
    Button(
        modifier = modifier
            .height(60.dp)
            .border(
                width = 1.dp,
                color = ColorStyle.PURPLE_400,
                shape = RoundedCornerShape(size = 12.dp)
            ),
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = ColorStyle.WHITE_100,
            contentColor = ColorStyle.PURPLE_400
        )
    ) {
        Text(
            text = buttonText,
            style = AppTextStyles.TITLE_20_28_SEMI,
            color = ColorStyle.PURPLE_400
        )
    }
}

@Composable
fun ButtonPurple100MEDIUM(
    onClick: () -> Unit,
    buttonText: String,
    modifier: Modifier,
) {
    Button(
        modifier = modifier
            .height(48.dp)
            .background(color = ColorStyle.PURPLE_400, shape = RoundedCornerShape(size = 12.dp)),
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
    ) {
        Text(
            text = buttonText,
            style = AppTextStyles.SUBTITLE_16_24_SEMI,
            color = ColorStyle.WHITE_100
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
fun ButtonCenterLarge(
    text: String,
    checked: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (checked) ColorStyle.PURPLE_100 else ColorStyle.GRAY_100,
            contentColor = Color.Black
        ),
        border = BorderStroke(
            1.dp,
            if (checked) ColorStyle.PURPLE_200 else Color.Transparent
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
    ) {
        Text(
            text = text,
            style = AppTextStyles.BODY_14_20_MEDIUM,
            color = ColorStyle.GRAY_800
        )
    }
}

@Composable
fun ButtonLeftLarge(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val backgroundColor = if (isSelected) ColorStyle.PURPLE_100 else ColorStyle.GRAY_100
    val borderModifier = if (isSelected) {
        Modifier.border(
            width = 1.dp,
            color = ColorStyle.PURPLE_200,
            shape = RoundedCornerShape(12.dp)
        )
    } else Modifier

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(backgroundColor, shape = RoundedCornerShape(8.dp))
            .then(borderModifier)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                onClick()
            },
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(start = 17.dp),
            style = AppTextStyles.BODY_14_20_MEDIUM,
            color = ColorStyle.GRAY_800
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

@Composable
fun ButtonL(
    text: String,
    isEnable: Boolean = true,
    onClick: () -> Unit,
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            when (isEnable) {
                true -> ColorStyle.PURPLE_400
                false -> ColorStyle.GRAY_300
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
            color = when (isEnable) {
                true -> ColorStyle.WHITE_100
                false -> ColorStyle.GRAY_800
            }
        )
    }
}

@Composable
fun ButtonLWhite(
    text: String,
    isEnable: Boolean = true,
    onClick: () -> Unit,
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .border(
                width = 1.dp,
                color = ColorStyle.GRAY_300,
                shape = RoundedCornerShape(size = 8.dp)
            ),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            ColorStyle.WHITE_100
        ),
        contentPadding = PaddingValues(start = 17.dp, top = 10.dp, end = 16.dp, bottom = 10.dp),
        onClick = onClick,
        enabled = isEnable
    ) {
        Text(
            text = text,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = AppTextStyles.BODY_14_20_MEDIUM,
            color =  ColorStyle.GRAY_800
        )
    }
}

@Composable
fun ButtonLargeCustom(
    text: String,
    isEnable: Boolean = true,
    modifier: Modifier = Modifier,
    buttonColor: Color,
    onClick: () -> Unit,
    textColor: Color,
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            buttonColor
        ),
        contentPadding = PaddingValues(start = 17.dp, top = 10.dp, end = 16.dp, bottom = 10.dp),
        onClick = onClick,
        enabled = isEnable
    ) {
        Text(
            text = text,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = AppTextStyles.BODY_14_20_MEDIUM,
            color = textColor
        )
    }
}