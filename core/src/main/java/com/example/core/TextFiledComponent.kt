package com.example.core

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp


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


