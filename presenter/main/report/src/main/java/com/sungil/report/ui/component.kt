package com.sungil.report.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.core.AppTextStyles
import com.sungil.report.R


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
                painter = painterResource(id = R.drawable.ic_left_out),
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
fun CustomSnackBar(data: SnackbarData) {
    Row(
        modifier = Modifier
            .width(360.dp)
            .height(48.dp)
            .background(color = Color(0xFF383838), shape = RoundedCornerShape(size = 8.dp))
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
            color = Color(0xFFFFFFFF)
        )
    }
}

@Composable
fun CustomReportItem(
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
fun CustomGrayButton(
    text: String,
    modifier: Modifier = Modifier,
    color: Color, // 이건 텍스트 색
    clickable: () -> Unit,
) {
    Button(
        onClick = clickable,
        modifier = modifier.height(48.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFEFEFEF), // ✅ 버튼 배경을 회색으로!
            contentColor = color // ✅ 텍스트 색상
        )
    ) {
        Text(
            text = text,
            style = AppTextStyles.BODY_14_20_MEDIUM
        )
    }
}

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    maxLength: Int = 100,
) {
    TextField(
        value = value,
        onValueChange = {
            if (it.length <= maxLength) {
                onValueChange(it)
            }
        },
        placeholder = {
            Text(
                text = placeholder,
                color = Color(0xFF666666),
                style = AppTextStyles.SUBTITLE_16_24_SEMI
            )
        },
        textStyle =AppTextStyles.SUBTITLE_16_24_SEMI ,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Color(0xFFF7F7F7), shape = RoundedCornerShape(12.dp))
            .padding(16.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(12.dp),
        maxLines = 10,
        singleLine = false
    )
}

@Composable
fun CustomTitleText(
    text: String,
) {
    Text(
        text = text,
        style = AppTextStyles.TITLE_20_28_SEMI,
        color = Color(0xFF171717)
    )
}
@Composable
fun CustomButton(
    text: String,
    onclick: () -> Unit,
    enable: Boolean,
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(
                start = 17.dp,
                end = 17.dp
            ),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(

            when (enable) {
                true -> {
                    Color(0xFF6700CE)
                }

                false -> {
                    Color(0xFFEFEFEF)
                }
            }

        ),
        onClick = { onclick() },
        enabled = enable
    ) {
        Text(
            text = text,
            style = AppTextStyles.TITLE_20_28_SEMI,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            color = if (enable) {
                Color(0xFFFFFFFF)
            } else {
                Color(0xFF171717)
            }
        )
    }
}

@Composable
fun CustomDialog(
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
                    color = Color(0xFFF7F7F7),
                    shape = RoundedCornerShape(size = 24.dp)
                )
                .width(324.dp)
                .height(194.dp)
                .background(
                    color = Color(0xFFFFFFFF),
                    shape = RoundedCornerShape(size = 24.dp)
                )
                .padding(24.dp),

            ) {
            Column(
                modifier = Modifier.fillMaxSize()
                    .background(Color(0xFFFFFFFF)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = titleText,
                    style = AppTextStyles.TITLE_20_28_SEMI,
                    color = Color(0xFF171717),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = contentText,
                    style = AppTextStyles.BODY_14_20_MEDIUM,
                    color = Color(0xFF666666),
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = buttonClick,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .background(color = Color(0xFF6700CE), shape = RoundedCornerShape(size = 12.dp))
                ) {
                    Text(
                        text = buttonText,
                        style = AppTextStyles.SUBTITLE_16_24_SEMI,
                        color = Color(0xFFFFFFFF),
                    )
                }
            }
        }
    }
}
