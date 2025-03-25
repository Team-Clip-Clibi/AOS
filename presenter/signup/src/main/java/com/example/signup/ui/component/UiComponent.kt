package com.example.signup.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.signup.R
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.window.Dialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    currentPage: Int,
    totalPage: Int,
    onBackClick: () -> Unit,
) {
    Column {
        TopAppBar(
            modifier = Modifier.padding(start = 12.dp, end = 16.dp),
            title = {
                Text(
                    text = title,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.bold))
                )
            },
            navigationIcon = {
                Image(
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = "뒤로가기",
                    modifier = Modifier
                        .padding(1.dp)
                        .width(24.dp)
                        .height(24.dp)
                        .clickable { onBackClick() }
                )
            },
            actions = {
                Text(
                    text = "$currentPage/$totalPage",
                    style = TextStyle(
                        fontSize = 12.sp,
                        lineHeight = 18.sp,
                        fontFamily = FontFamily(Font(R.font.light)),
                        fontWeight = FontWeight(600),
                        color = colorResource(R.color.lavender),
                        textAlign = TextAlign.End
                    )
                )
            }
        )
        HorizontalDivider(thickness = 1.dp, color = Color(0xFFEFEFEF))
    }
}

@Composable
fun CustomCheckBox(
    text: String,
    checked: Boolean,
    modifier: Modifier,
    onCheckChange: (Boolean) -> Unit,
    isIconShow: Boolean = true,
) {
    Row(
        modifier = modifier.clickable { onCheckChange(!checked) },
        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(2.dp))
        CircularCheckBox(
            checked = checked,
            onCheckedChange = onCheckChange
        )
        Spacer(modifier = Modifier.width(10.dp))

        Text(
            modifier = Modifier.weight(1f),
            text = text,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        if (isIconShow) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow),
                contentDescription = "자세히 보기",
                modifier = Modifier.size(24.dp),
                tint = Color.Gray
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
    }
}

@Composable
fun CircularCheckBox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    Box(
        modifier = Modifier
            .size(32.dp)
            .background(
                color = if (checked) colorResource(id = R.color.purple) else colorResource(R.color.bright_gray),
                shape = CircleShape
            )
            .clickable { onCheckedChange(!checked) },
        contentAlignment = Alignment.Center
    ) {
        if (checked) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "동의항목",
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )
        } else {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "비동의항목",
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@Composable
fun CustomTextField(
    text: String,
    modifier: Modifier,
    onValueChange: (String) -> Unit,
    inputType : KeyboardType,
    hint : String,
    timeCount : String =""
) {
    TextField(
        value = text,
        onValueChange = onValueChange,
        modifier = modifier.background(
            colorResource(R.color.light_gray),
            shape = RoundedCornerShape(12.dp)
        ),
        keyboardOptions = KeyboardOptions(keyboardType = inputType),
        singleLine = true,
        placeholder = {
            Text(
                hint,
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    fontFamily = FontFamily(Font(R.font.medium)),
                    fontWeight = FontWeight(600),
                    color = colorResource(R.color.dark_gray)
                )
            )
        },
        textStyle = TextStyle(
            fontSize = 16.sp,
            lineHeight = 24.sp,
            fontFamily = FontFamily(Font(R.font.medium)),
            fontWeight = FontWeight(600),
            color = colorResource(R.color.black_gray),
        ),
        trailingIcon = {
            Text(
                text = timeCount,
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    fontFamily = FontFamily(Font(R.font.medium)),
                    fontWeight = FontWeight(600),
                    color = colorResource(R.color.gray_text),
                    textAlign = TextAlign.Right
                ),
                modifier = Modifier.padding(end = 16.dp)
            )
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = colorResource(R.color.light_gray),
            unfocusedContainerColor = colorResource(R.color.light_gray),
            disabledContainerColor = colorResource(R.color.light_gray),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
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
                    color = colorResource(R.color.light_gray),
                    shape = RoundedCornerShape(size = 24.dp)
                )
                .width(324.dp)
                .height(174.dp)
                .background(
                    color = colorResource(R.color.white),
                    shape = RoundedCornerShape(size = 24.dp)
                )
                .padding(24.dp),

            ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = titleText,
                    style = TextStyle(
                        fontSize = 20.sp,
                        lineHeight = 28.sp,
                        fontFamily = FontFamily(Font(R.font.medium)),
                        fontWeight = FontWeight(600),
                        color = colorResource(R.color.black_gray),
                        textAlign = TextAlign.Center
                    )
                )
                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = contentText,
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        fontFamily = FontFamily(Font(R.font.medium)),
                        fontWeight = FontWeight(500),
                        color = colorResource(R.color.dark_gray),
                        textAlign = TextAlign.Center
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = buttonClick,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text(
                        text = buttonText,
                        style = TextStyle(
                            fontSize = 16.sp,
                            lineHeight = 24.sp,
                            fontFamily = FontFamily(Font(R.font.medium)),
                            fontWeight = FontWeight(600),
                            color = colorResource(R.color.white)
                        )
                    )
                }
            }
        }
    }
}
@Composable
fun CustomTitleText(text : String){
    Text(
        text = text,
        fontSize = 16.sp,
        fontFamily = FontFamily(Font(R.font.medium)),
        fontWeight = FontWeight(600),
        modifier = Modifier.fillMaxWidth(),
        color = colorResource(R.color.dark_gray)
    )
}

@Composable
fun CustomContentText(text : String){
    Text(
        text = text,
        fontSize = 28.sp,
        lineHeight = 40.sp,
        fontFamily = FontFamily(Font(R.font.medium)),
        fontWeight = FontWeight(700),
        modifier = Modifier.fillMaxWidth(),
        color = colorResource(R.color.black_gray)
    )
}

@Composable
fun CustomUnderTextFieldText(text : String , color : Color){
    Text(
        text = text,
        fontSize = 12.sp,
        lineHeight = 18.sp,
        fontFamily =  FontFamily(Font(R.font.medium)),
        fontWeight = FontWeight(400),
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
        onClick = { onclick() },
        enabled = enable
    ) {
        Text(
            text = text,
            fontSize = 20.sp,
            lineHeight = 28.sp,
            fontFamily = FontFamily(Font(R.font.medium)),
            fontWeight = FontWeight(600),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            color = colorResource(R.color.white)
        )
    }
}