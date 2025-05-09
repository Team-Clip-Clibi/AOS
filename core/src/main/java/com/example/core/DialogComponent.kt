package com.example.core

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog


@Composable
fun CustomDialogOneButton(
    onDismiss: () -> Unit,
    buttonClick: () -> Unit,
    titleText: String,
    contentText: String,
    buttonText: String,
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = ColorStyle.GRAY_100,
                    shape = RoundedCornerShape(24.dp)
                )
                .width(324.dp)
                .height(194.dp)
                .background(
                    color = ColorStyle.WHITE_100,
                    shape = RoundedCornerShape(24.dp)
                )
                .padding(24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = ColorStyle.WHITE_100)
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
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(24.dp))
                ButtonPurple400MEDIUM(
                    onClick = buttonClick,
                    buttonText = buttonText,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun CustomSnackBar(
    data: SnackbarData,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(color = ColorStyle.GRAY_700, shape = RoundedCornerShape(size = 8.dp))
            .padding(start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_message),
            contentDescription = "message",
            contentScale = ContentScale.None,
            modifier = Modifier
                .width(24.dp)
                .height(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = data.visuals.message,
            style = AppTextStyles.CAPTION_12_18_SEMI,
            color = ColorStyle.WHITE_100
        )
    }
}