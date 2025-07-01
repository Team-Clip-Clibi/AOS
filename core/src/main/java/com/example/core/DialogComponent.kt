package com.example.core

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.material3.Text
import androidx.compose.ui.res.stringResource
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

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
                ButtonPurple100MEDIUM(
                    onClick = buttonClick,
                    buttonText = buttonText,
                    modifier = Modifier
                        .fillMaxWidth()
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

@Composable
fun CustomDialogOneButton(
    time: String,
    meetState: String,
    meetKind: String,
    title: String,
    reviewWrite: Boolean,
    buttonText: String,
    onClick: () -> Unit,
    onClickDetail: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(193.dp)
            .background(color = ColorStyle.WHITE_100, shape = RoundedCornerShape(size = 12.dp))
            .padding(start = 20.dp, end = 20.dp, top = 18.dp, bottom = 18.dp)
    ) {
        //top view
        Row(
            modifier = Modifier.fillMaxWidth().clickable { onClickDetail() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = time,
                style = AppTextStyles.CAPTION_12_18_SEMI,
                color = ColorStyle.GRAY_800
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = stringResource(R.string.txt_detail),
                style = AppTextStyles.CAPTION_12_18_SEMI,
                color = ColorStyle.GRAY_700
            )
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                painter = painterResource(R.drawable.ic_arrow_right),
                contentDescription = stringResource(R.string.txt_detail),
            )
        }
        //content View
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = meetState,
                style = AppTextStyles.CAPTION_12_18_SEMI,
                color = when(meetState){
                    "취소" -> ColorStyle.RED_100
                    else -> ColorStyle.PURPLE_400
                }
            )
            Spacer(modifier = Modifier.width(6.dp))
            Icon(
                painter = painterResource(R.drawable.ic_point),
                contentDescription = meetState,
                modifier = Modifier
                    .padding(1.dp)
                    .width(2.dp)
                    .height(2.dp)
                    .alignByBaseline()
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = meetKind,
                style = AppTextStyles.CAPTION_12_18_SEMI,
                color = ColorStyle.GRAY_800
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(color = ColorStyle.GRAY_400)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = title,
            style = AppTextStyles.SUBTITLE_18_26_SEMI,
            color = ColorStyle.GRAY_800,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(14.dp))
        if(meetState != "취소"){
            ButtonL(
                text = buttonText,
                isEnable = !reviewWrite,
                onClick = onClick,
            )
        }
    }
}

@Composable
fun NoticePage(
    date: String,
    meetState: String,
    meetKind: String,
    title: String,
    dateDetail: String,
    restaurant : String,
    location: String,
    people : String,
    job : String,
    cuisine: String,
    cuisineHighLight: String,
    detail: String,
    pay: String,
    onClick: () -> Unit,
    buttonShow : Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = ColorStyle.WHITE_100, shape = RoundedCornerShape(12.dp))
            .padding(top = 20.dp, bottom = 20.dp, start = 18.dp, end = 18.dp)
    ) {
        Text(
            text = date,
            style = AppTextStyles.CAPTION_12_18_SEMI,
            color = ColorStyle.GRAY_800
        )
        Spacer(modifier = Modifier.height(2.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = meetState,
                style = AppTextStyles.CAPTION_12_18_SEMI,
                color = ColorStyle.PURPLE_400
            )
            Spacer(modifier = Modifier.width(6.dp))
            Icon(
                painter = painterResource(R.drawable.ic_point),
                contentDescription = meetState,
                modifier = Modifier
                    .padding(1.dp)
                    .width(2.dp)
                    .height(2.dp)
                    .align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = meetKind,
                style = AppTextStyles.CAPTION_12_18_SEMI,
                color = ColorStyle.GRAY_800
            )
        }
        Text(
            text = title,
            style = AppTextStyles.HEAD_24_34_BOLD,
            color = ColorStyle.GRAY_800,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))
        NoticeItemView(image = painterResource(R.drawable.ic_calendar), text = dateDetail , highlighted = dateDetail)
        NoticeItemView(image = painterResource(R.drawable.ic_location), text = "$restaurant($location)", highlighted = restaurant)
        NoticeItemView(image = painterResource(R.drawable.ic_people), text = people , highlighted = job)
        NoticeItemView(image = painterResource(R.drawable.ic_cuisine), text = cuisine , highlighted = cuisineHighLight)
        NoticeItemView(image = painterResource(R.drawable.ic_detail), text = detail , highlighted = "")
        NoticeItemView(image = painterResource(R.drawable.ic_pay), text = pay , highlighted = "" , isLinePrint = buttonShow)
        if(buttonShow){
            Spacer(modifier = Modifier.height(10.dp))
            ButtonLWhite(
                text = stringResource(R.string.btn_late),
                onClick = onClick
            )
        }
    }
}

@Composable
fun NoticeItemView(image: Painter, text: String , highlighted : String , isLinePrint : Boolean = true) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Icon(
                painter = image,
                contentDescription = text
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = buildAnnotatedString {
                    val start = text.indexOf(highlighted)
                    if (start != -1) {
                        val end = start + highlighted.length
                        withStyle(style = AppTextStyles.BODY_14_20_REGULAR.toSpanStyle()) {
                            append(text.substring(0, start))
                        }
                        withStyle(style = AppTextStyles.BODY_14_20_EXTRA_BOLD.toSpanStyle()) {
                            append(text.substring(start, end))
                        }
                        withStyle(style = AppTextStyles.BODY_14_20_REGULAR.toSpanStyle()) {
                            append(text.substring(end))
                        }
                    } else {
                        withStyle(style = AppTextStyles.BODY_14_20_REGULAR.toSpanStyle()) {
                            append(text)
                        }
                    }
                },
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        if(isLinePrint){
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(color = ColorStyle.GRAY_200)
            )
        }
    }
}
