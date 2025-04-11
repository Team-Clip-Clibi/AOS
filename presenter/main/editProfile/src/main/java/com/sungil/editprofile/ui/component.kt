package com.sungil.editprofile.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.core.AppTextStyles
import com.sungil.editprofile.JOB
import com.sungil.editprofile.R

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomChangeDataAppBar(
    text : String,
    onBackClick: () -> Unit
){
    CenterAlignedTopAppBar(
        modifier = Modifier
            .border(width = 1.dp, color = Color(0xFFEFEFEF))
            .fillMaxWidth()
            .padding(start = 5.dp, end = 5.dp),
        title = {
            Text(
                text = text,
                style = AppTextStyles.TITLE_20_28_SEMI,
                color = Color(0xFF000000),
                textAlign = TextAlign.Center
            )
        },
        navigationIcon = {},
        actions = {
            Image(
                painter = painterResource(id=  R.drawable.ic_close_x),
                contentDescription = "화면닫기",
                modifier = Modifier
                    .padding(12.dp)
                    .size(24.dp)
                    .clickable { onBackClick() }
            )
        },
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

@Composable
fun CustomChangeDataTextField(
    beforeText: String,
    onValueChange: (String) -> Unit,
    inputType: KeyboardType,
) {
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            ,
        value = beforeText,
        onValueChange = onValueChange,
        textStyle = AppTextStyles.BODY_14_20_MEDIUM,
        keyboardOptions = KeyboardOptions(keyboardType = inputType),
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedIndicatorColor = Color(0xFF989898),   // 아래 줄 색상
            unfocusedIndicatorColor = Color(0xFF989898),
            disabledIndicatorColor = Color(0xFF989898)
        )
    )
}


@Composable
fun CustomUnderTextFieldText(text: String, color: Color) {
    Text(
        text = text,
        style = AppTextStyles.CAPTION_12_18_SEMI,
        color = color
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
fun CustomTitle1624Semi(
    text: String,
    color: Long,
) {
    Text(
        text = text,
        style = AppTextStyles.SUBTITLE_16_24_SEMI,
        color = Color(color)
    )
}

@Composable
fun JobGridSelector(
    selectedJobs: List<JOB>,
    onJobToggle: (JOB) -> Unit,

) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(JOB.entries.toList()) { job ->
            val isSelected = selectedJobs.contains(job)

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

@Composable
fun CustomItemPick(
    text: String,
    modifier: Modifier = Modifier,
    clickable: () -> Unit,
) {
    Box(
        modifier = modifier.clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() }
        ) {
            clickable()
        },
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(start = 17.dp),
            style = AppTextStyles.BODY_14_20_MEDIUM,
            color = Color(0xFF171717)
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
    subButtonText : String
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
                .height(240.dp)
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
                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = onDismiss,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        Color(0xFFEFEFEF)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text(
                        text = subButtonText,
                        style = AppTextStyles.SUBTITLE_16_24_SEMI,
                        color = Color(0xFF171717),
                    )
                }
            }
        }
    }
}

@Composable
fun CustomTitle(
    text : String,
    color : Color
){
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = text,
        color = color,
        style = AppTextStyles.TITLE_20_28_SEMI
    )
}

