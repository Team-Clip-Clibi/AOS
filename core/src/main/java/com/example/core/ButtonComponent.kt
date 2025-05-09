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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

//TEXT STYLE TITLE BUTTON
@Composable
fun ButtonGray200TITLE(
    onClick: () -> Unit,
    buttonText: String,
    modifier: Modifier,
) {
    Button(
        modifier = modifier
            .height(60.dp)
            .background(color = ColorStyle.GRAY_100),
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
    ) {
        Text(
            text = buttonText,
            style = AppTextStyles.TITLE_20_28_SEMI,
            color = ColorStyle.GRAY_800
        )
    }
}

@Composable
fun ButtonPurple400TITLE(
    onClick: () -> Unit,
    buttonText: String,
    modifier: Modifier,
    isEnable : Boolean = true
) {
    Button(
        modifier = modifier
            .height(60.dp)
            .background(color = when(isEnable){
                true ->{
                    ColorStyle.PURPLE_400
                }
                else ->{
                    ColorStyle.GRAY_200
                }
            }),
        onClick = onClick,
        enabled = isEnable,
        shape = RoundedCornerShape(12.dp),
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
fun ButtonWhite100TITLE(
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
            )
            .background(color = ColorStyle.WHITE_100),
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
    ) {
        Text(
            text = buttonText,
            style = AppTextStyles.TITLE_20_28_SEMI,
            color = ColorStyle.PURPLE_400
        )
    }
}

//TEXT STYLE START BUTTON
@Composable
fun ButtonGray100TextStart(
    onClick: () -> Unit,
    buttonText: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
) {
    val backgroundColor = if (isSelected) ColorStyle.PURPLE_100 else ColorStyle.GRAY_100
    val borderColor = if (isSelected) ColorStyle.PURPLE_200 else Color.Transparent

    Button(
        onClick = onClick,
        modifier = modifier
            .height(60.dp)
            .border(1.dp, borderColor, shape = RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = ColorStyle.GRAY_800
        ),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(
            text = buttonText,
            style = AppTextStyles.BODY_14_20_MEDIUM,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

// TEXT STYLE MEDIUM BUTTON
@Composable
fun ButtonGray100MEDIUM(
    onClick: () -> Unit,
    buttonText: String,
    modifier: Modifier,
) {
    Button(
        modifier = modifier
            .height(48.dp)
            .background(color = ColorStyle.GRAY_100),
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
    ) {
        Text(
            text = buttonText,
            style = AppTextStyles.BODY_14_20_MEDIUM,
            color = ColorStyle.GRAY_800
        )
    }
}


@Composable
fun ButtonGray200MEDIUM(
    onClick: () -> Unit,
    buttonText: String,
    modifier: Modifier,
) {
    Button(
        modifier = modifier
            .height(48.dp)
            .background(color = ColorStyle.GRAY_200),
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
    ) {
        Text(
            text = buttonText,
            style = AppTextStyles.BODY_14_20_MEDIUM,
            color = ColorStyle.GRAY_800
        )
    }
}

@Composable
fun ButtonGray300MEDIUM(
    onClick: () -> Unit,
    buttonText: String,
    modifier: Modifier,
) {
    Button(
        modifier = modifier
            .height(48.dp)
            .background(color = ColorStyle.GRAY_300),
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
    ) {
        Text(
            text = buttonText,
            style = AppTextStyles.BODY_14_20_MEDIUM,
            color = ColorStyle.GRAY_800
        )
    }
}

@Composable
fun ButtonPurple400MEDIUM(
    onClick: () -> Unit,
    buttonText: String,
    modifier: Modifier,
) {
    Button(
        modifier = modifier
            .height(48.dp)
            .background(color = ColorStyle.PURPLE_400),
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
    ) {
        Text(
            text = buttonText,
            style = AppTextStyles.BODY_14_20_MEDIUM,
            color = ColorStyle.WHITE_100
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
            .border(width = 1.dp, color = ColorStyle.PURPLE_200, shape = RoundedCornerShape(8.dp))
            .background(color = ColorStyle.PURPLE_100),
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
    ) {
        Text(
            text = buttonText,
            style = AppTextStyles.BODY_14_20_MEDIUM,
            color = ColorStyle.GRAY_800
        )
    }
}

@Composable
fun ButtonWHITE100MEDIUM(
    onClick: () -> Unit,
    buttonText: String,
    modifier: Modifier,
) {
    Button(
        modifier = modifier
            .height(48.dp)
            .border(width = 1.dp, color = ColorStyle.GRAY_300, shape = RoundedCornerShape(8.dp))
            .background(color = ColorStyle.WHITE_100),
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
    ) {
        Text(
            text = buttonText,
            style = AppTextStyles.BODY_14_20_MEDIUM,
            color = ColorStyle.GRAY_800
        )
    }
}

//BUTTON SEMI
@Composable
fun ButtonPurple400SEMI(
    onClick: () -> Unit,
    buttonText: String,
    modifier: Modifier,
) {
    Button(
        modifier = modifier
            .height(48.dp)
            .background(color = ColorStyle.PURPLE_400),
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
    ) {
        Text(
            text = buttonText,
            style = AppTextStyles.SUBTITLE_16_24_SEMI,
            color = ColorStyle.WHITE_100
        )
    }
}

@Composable
fun ButtonGray200SEMI(
    onClick: () -> Unit,
    buttonText: String,
    modifier: Modifier,
) {
    Button(
        modifier = modifier
            .height(48.dp)
            .background(color = ColorStyle.GRAY_200),
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
    ) {
        Text(
            text = buttonText,
            style = AppTextStyles.SUBTITLE_16_24_SEMI,
            color = ColorStyle.GRAY_800
        )
    }
}

// SMALL BUTTON
@Composable
fun ButtonPurple100SMALL(
    onClick: () -> Unit,
    buttonText: String,
) {
    Button(
        modifier = Modifier
            .width(96.dp)
            .height(48.dp)
            .background(color = ColorStyle.PURPLE_100),
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
    ) {
        Text(
            text = buttonText,
            style = AppTextStyles.SUBTITLE_16_24_SEMI,
            color = ColorStyle.PURPLE_400
        )
    }
}

@Composable
fun ButtonGray200SMALL(
    onClick: () -> Unit,
    buttonText: String,
) {
    Button(
        modifier = Modifier
            .width(96.dp)
            .height(48.dp)
            .background(color = ColorStyle.GRAY_200),
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
    ) {
        Text(
            text = buttonText,
            style = AppTextStyles.SUBTITLE_16_24_SEMI,
            color = ColorStyle.GRAY_600
        )
    }
}

@Composable
fun CheckBoxRowLarge(
    content: String,
    isChecked: Boolean,
    onCheckChange: (Boolean) -> Unit,
) {
    val baseModifier = Modifier
        .fillMaxWidth()
        .height(48.dp)
        .padding(start = 17.dp, end = 16.dp)
        .clickable { onCheckChange(!isChecked) }

    val backgroundColor = if (isChecked) ColorStyle.PURPLE_100 else ColorStyle.WHITE_100
    val borderModifier = if (isChecked) {
        Modifier.border(
            width = 1.dp,
            color = ColorStyle.PURPLE_200,
            shape = RoundedCornerShape(8.dp)
        )
    } else Modifier

    Row(
        modifier = baseModifier
            .then(borderModifier)
            .background(color = backgroundColor, shape = RoundedCornerShape(8.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircularCheckBoxLarge(
            checked = isChecked,
            onCheckChange = onCheckChange
        )
        Spacer(modifier = Modifier.width(10.dp))
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