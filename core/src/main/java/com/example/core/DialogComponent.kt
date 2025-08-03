package com.example.core

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import kotlinx.coroutines.launch

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
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = contentText,
                    style = AppTextStyles.BODY_14_20_MEDIUM,
                    color = ColorStyle.GRAY_600,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
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
fun CustomDialogTwoButton(
    onDismiss: () -> Unit,
    buttonClick: () -> Unit,
    titleText: String,
    contentText: String,
    buttonText: String,
    dismissButtonText: String,
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = ColorStyle.GRAY_200,
                    shape = RoundedCornerShape(24.dp)
                )
                .width(324.dp)
                .height(232.dp)
                .background(color = ColorStyle.WHITE_100, shape = RoundedCornerShape(24.dp))
                .padding(start = 24.dp, top = 24.dp, end = 24.dp, bottom = 24.dp),
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = titleText,
                    style = AppTextStyles.TITLE_20_28_SEMI,
                    color = ColorStyle.GRAY_800,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = contentText,
                    style = AppTextStyles.BODY_14_20_MEDIUM,
                    color = ColorStyle.GRAY_600,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ColorStyle.PURPLE_400
                    ),
                    onClick = buttonClick,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = buttonText,
                        color = ColorStyle.WHITE_100,
                        style = AppTextStyles.SUBTITLE_16_24_SEMI
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    onClick = onDismiss,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ColorStyle.GRAY_200
                    )
                ) {
                    Text(
                        text = dismissButtonText,
                        color = ColorStyle.GRAY_800,
                        style = AppTextStyles.SUBTITLE_16_24_SEMI
                    )
                }
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
fun CustomDialogImageOneButton(
    image: Int,
    title: String,
    content: String,
    buttonText: String,
    onClick: () -> Unit,
) {
    Dialog(onDismissRequest = onClick) {
        Surface(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = ColorStyle.GRAY_100,
                    shape = RoundedCornerShape(24.dp)
                )
                .width(324.dp)
                .height(280.dp)
                .background(color = ColorStyle.WHITE_100, shape = RoundedCornerShape(24.dp))
                .padding(24.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize().background(color = ColorStyle.WHITE_100),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(image),
                    contentDescription = "dialog_image",
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = title,
                    style = AppTextStyles.TITLE_20_28_SEMI,
                    color = ColorStyle.GRAY_800,
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = content,
                    style = AppTextStyles.BODY_14_20_MEDIUM,
                    color = ColorStyle.GRAY_600
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    onClick = onClick,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ColorStyle.PURPLE_400
                    )
                ){
                    Text(
                        text = buttonText,
                        color = ColorStyle.WHITE_100,
                        style = AppTextStyles.SUBTITLE_16_24_SEMI
                    )
                }
            }
        }
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
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClickDetail() },
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
                tint = ColorStyle.GRAY_500
            )
        }
        //content View
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = meetState,
                style = AppTextStyles.CAPTION_12_18_SEMI,
                color = when(meetState){
                    "취소" -> ColorStyle.RED_100
                    else -> ColorStyle.PURPLE_400
                },
            )
            Spacer(modifier = Modifier.width(6.dp))
            Icon(
                painter = painterResource(R.drawable.ic_point),
                contentDescription = meetState,
                modifier = Modifier
                    .padding(1.dp)
                    .width(2.dp)
                    .height(2.dp),
                tint = ColorStyle.GRAY_500
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = meetKind,
                style = AppTextStyles.CAPTION_12_18_SEMI,
                color = ColorStyle.GRAY_800,
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
    buttonShow : Boolean,
    buttonText : String,
    latButtonShow : Boolean = false ,
    lateButtonText : String = "",
    lateButtonTime : String = ""
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
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = title,
            style = AppTextStyles.HEAD_24_34_BOLD,
            color = ColorStyle.GRAY_800,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))
        NoticeItemView(image = painterResource(R.drawable.ic_match_notice_calendar), text = dateDetail , highlighted = dateDetail)
        NoticeItemView(image = painterResource(R.drawable.ic_match_notice_location), text = "$restaurant($location)", highlighted = restaurant)
        NoticeItemView(image = painterResource(R.drawable.ic_people), text = people , highlighted = job)
        NoticeItemView(image = painterResource(R.drawable.ic_cuisine), text = cuisine , highlighted = cuisineHighLight)
        NoticeItemView(image = painterResource(R.drawable.ic_detail), text = detail , highlighted = "")
        NoticeItemView(image = painterResource(R.drawable.ic_pay), text = pay , highlighted = "" , isLinePrint = buttonShow)
        if(buttonShow && !latButtonShow){
            Spacer(modifier = Modifier.height(10.dp))
            ButtonLWhite(
                text = buttonText,
                onClick = onClick
            )
        }
        if(buttonShow && latButtonShow){
            LateButton(
                lateTime = lateButtonTime,
                onClick = onClick,
                text = lateButtonText
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleBottomSheet(
    item: List<String>,
    click: (String , Int) -> Unit,
    buttonText : String,
    title: String,
    content: String,
    noticeId : Int,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()
    var selectedItem by remember { mutableStateOf("") }
    ModalBottomSheet(
        onDismissRequest = {
            coroutineScope.launch {
                sheetState.hide()
                onDismiss()
            }
        },
        sheetState = sheetState,
        containerColor = ColorStyle.WHITE_100,
        dragHandle = { BottomSheetDefaults.DragHandle() },
        contentColor = ColorStyle.WHITE_100
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = ColorStyle.WHITE_100)
                .heightIn(min = 200.dp, max = 400.dp)
                .padding(top = 20.dp, bottom = 34.dp, start = 20.dp, end = 24.dp)
        ) {
            Text(
                text = title,
                style = AppTextStyles.HEAD_24_34_BOLD,
                color = ColorStyle.GRAY_800
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = content,
                style = AppTextStyles.SUBTITLE_16_24_SEMI,
                color = ColorStyle.GRAY_600
            )
            Spacer(modifier = Modifier.height(24.dp))
            item.forEachIndexed { _, label ->
                SimpleSheetItem(
                    text = label,
                    onClick = {
                        selectedItem = label
                    },
                    isClick = selectedItem == label
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
            SimpleSheetOkay(
                text = buttonText,
                isEnable = selectedItem != "",
                onClick = {
                    click(selectedItem ,noticeId)
                    coroutineScope.launch {
                        sheetState.hide()
                        onDismiss()
                    }
                }
            )
        }
    }
}

@Composable
fun SimpleSheetItem(
    text: String,
    isClick: Boolean,
    onClick: () -> Unit,
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .border(
                width = 1.dp,
                color = if (isClick) ColorStyle.PURPLE_200 else ColorStyle.GRAY_300,
                shape = RoundedCornerShape(size = 8.dp)
            ),
        shape = RoundedCornerShape(size = 8.dp),
        colors = ButtonDefaults.buttonColors(
            if (isClick) ColorStyle.PURPLE_100 else ColorStyle.WHITE_100
        ),
        contentPadding = PaddingValues(start = 17.dp, top = 10.dp, end = 16.dp, bottom = 10.dp),
        onClick = onClick
    ) {
        Text(
            text = text,
            maxLines = 1,
            style = AppTextStyles.BODY_14_20_MEDIUM,
            color = ColorStyle.GRAY_800
        )
    }
}

@Composable
fun SimpleSheetOkay(
    text: String,
    isEnable: Boolean,
    onClick: () -> Unit,
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        shape = RoundedCornerShape(size = 8.dp),
        colors = ButtonDefaults.buttonColors(
            if (isEnable) ColorStyle.PURPLE_400 else ColorStyle.GRAY_100
        ),
        contentPadding = PaddingValues(start = 17.dp, top = 10.dp, end = 16.dp, bottom = 10.dp),
        onClick = {
            if (isEnable) onClick()
        }
    ) {
        Text(
            text = text,
            maxLines = 1,
            style = AppTextStyles.BODY_14_20_MEDIUM,
            color = if (isEnable) ColorStyle.WHITE_100 else ColorStyle.GRAY_800
        )
    }
}

@Composable
fun LateButton(
    text: String,
    lateTime: String,
    onClick: () -> Unit,
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        shape = RoundedCornerShape(size = 8.dp),
        colors = ButtonDefaults.buttonColors(
            ColorStyle.GRAY_100
        ),
        contentPadding = PaddingValues(start = 17.dp, top = 10.dp, end = 16.dp, bottom = 10.dp),
        onClick = {
            onClick()
        }
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = text,
                maxLines = 1,
                style = AppTextStyles.BODY_14_20_MEDIUM,
                color = ColorStyle.GRAY_800
            )
            VerticalDivider(
                modifier = Modifier
                    .width(1.dp)
                    .fillMaxHeight()
                    .background(color = ColorStyle.PURPLE_200)
            )
            Text(
                text = lateTime,
                style = AppTextStyles.BODY_14_20_MEDIUM,
                color = ColorStyle.GRAY_800
            )
        }
    }
}