package com.example.core

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun TextFieldComponent(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String = "",
    maxLength: Int,
    maxLine: Int
) {
    TextField(
        value = value,
        onValueChange = {
            if (it.length <= maxLength) {
                onValueChange(it)
            }
        },
        placeholder = { Text(hint) },
        singleLine = maxLine == 1,
        maxLines = maxLine,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(top = 10.dp, bottom = 10.dp)
            .border(
                width = 1.dp,
                color = ColorStyle.GRAY_500,
                shape = RoundedCornerShape(4.dp)
            ),
        colors = TextFieldDefaults.colors(
            focusedTextColor = ColorStyle.GRAY_800,
            unfocusedTextColor = ColorStyle.GRAY_800,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            cursorColor = ColorStyle.GRAY_800,
            focusedPlaceholderColor = ColorStyle.GRAY_500,
            unfocusedPlaceholderColor = ColorStyle.GRAY_500
        ),
        shape = RoundedCornerShape(4.dp)
    )
}
