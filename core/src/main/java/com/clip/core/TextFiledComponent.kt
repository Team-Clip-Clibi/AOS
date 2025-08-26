package com.clip.core

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay


@Composable
fun TextFieldComponent(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String = "",
    maxLength: Int,
    maxLine: Int,
    inputType: KeyboardType = KeyboardType.Text
) {
    TextField(
        value = value,
        onValueChange = {
            if (it.length <= maxLength) {
                onValueChange(it)
            }
        },
        placeholder = { Text(hint, style = AppTextStyles.BODY_14_20_MEDIUM , color = ColorStyle.GRAY_500) },
        singleLine = maxLine == 1,
        maxLines = maxLine,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        textStyle = AppTextStyles.BODY_14_20_MEDIUM,
        keyboardOptions = KeyboardOptions(keyboardType = inputType),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.White,
            focusedIndicatorColor = ColorStyle.GRAY_500,
            unfocusedIndicatorColor = ColorStyle.GRAY_500,
            disabledIndicatorColor = ColorStyle.GRAY_500,
            cursorColor = Color.Black,
            focusedPlaceholderColor = ColorStyle.GRAY_500,
            unfocusedPlaceholderColor = ColorStyle.GRAY_500,
            focusedTextColor = ColorStyle.GRAY_800,
            unfocusedTextColor = ColorStyle.GRAY_800
        ),
        shape = RoundedCornerShape(4.dp)
    )
}

@Composable
fun SlidingTextBox(
    textList: List<String>,
) {
    val index = remember { mutableIntStateOf(0) }
    val currentText = textList.getOrNull(index.intValue) ?: ""

    // 자동 순회 (2초마다 1개씩)
    LaunchedEffect(Unit) {
        while (true) {
            delay(2000L)
            index.intValue = (index.intValue + 1) % textList.size
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(32.dp)
            .background(color = ColorStyle.PURPLE_100, shape = RoundedCornerShape(size = 4.dp))
            .padding(start = 12.dp, top = 6.dp, end = 12.dp, bottom = 6.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        AnimatedContent(
            targetState = currentText,
            transitionSpec = {
                slideInVertically { height -> height } + fadeIn() togetherWith
                        slideOutVertically { height -> -height } + fadeOut()
            },
            label = "subject"
        ) { text ->
            Text(
                text = text,
                style = AppTextStyles.BODY_14_20_MEDIUM,
                color = ColorStyle.GRAY_800
            )
        }
    }
}

