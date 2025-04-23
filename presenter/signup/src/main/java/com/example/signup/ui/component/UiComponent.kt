package com.example.signup.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.signup.R
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.window.Dialog
import com.example.core.AppTextStyles
import com.example.signup.City
import com.example.signup.ISArea
import com.example.signup.ISCity
import com.example.signup.ISDay
import com.example.signup.ISMonth
import com.example.signup.ISYear
import com.example.signup.cityToCountyMap
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    currentPage: Int,
    totalPage: Int,
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
        actions = {
            Text(
                text = if (currentPage == 0 || totalPage == 0) "" else "$currentPage/$totalPage",
                style = AppTextStyles.CAPTION_12_18_SEMI,
                color = Color(0xFF6700CE)
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.White
        )
    )
}

@Composable
fun CustomCheckBox(
    text: String,
    checked: Boolean,
    modifier: Modifier,
    onCheckChange: (Boolean) -> Unit,
    isIconShow: Boolean = true,
) {
//    0xFF666666
    Row(
        modifier = modifier.clickable { onCheckChange(!checked) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(16.dp))
        CircularCheckBox(
            checked = checked,
            onCheckedChange = onCheckChange
        )
        Spacer(modifier = Modifier.width(10.dp))

        Text(
            modifier = Modifier.weight(1f),
            text = text,
            style = AppTextStyles.SUBTITLE_16_24_SEMI,
            color =  if (checked) Color(0xFF171717) else Color(0xFF666666)
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
                color = if (checked) Color(0xFF6700CE) else Color(0xFFCACACA),
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
    inputType: KeyboardType,
    hint: String,
    timeCount: String = "",
) {
    TextField(
        value = text,
        onValueChange = onValueChange,
        modifier = modifier.background(
            color = Color(0xFFF7F7F7),
            shape = RoundedCornerShape(12.dp)
        ),
        keyboardOptions = KeyboardOptions(keyboardType = inputType),
        singleLine = true,
        placeholder = {
            Text(
                hint,
                style = AppTextStyles.SUBTITLE_16_24_SEMI,
                color = Color(0xFF666666),
                modifier = Modifier.padding(end = 16.dp)
            )
        },
        textStyle = AppTextStyles.SUBTITLE_16_24_SEMI,
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
                modifier = Modifier.padding(start = 16.dp, end = 16.dp)
            )
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(0xFFF7F7F7),
            unfocusedContainerColor = Color(0xFFF7F7F7),
            disabledContainerColor = Color(0xFFF7F7F7),
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
                    color = Color(0xFFF7F7F7),
                    shape = RoundedCornerShape(size = 24.dp)
                )
                .width(324.dp)
                .height(174.dp)
                .background(
                    color = Color(0xFFFFFFFF),
                    shape = RoundedCornerShape(size = 24.dp)
                )
                .padding(24.dp),

            ) {
            Column(
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

@Composable
fun CustomTitleText(text: String , modifier: Modifier) {
    Text(
        text = text,
        style = AppTextStyles.SUBTITLE_16_24_SEMI,
        modifier = modifier,
        color = Color(0xFF666666)
    )
}

@Composable
fun CustomContentText(text: String, modifier: Modifier) {
    Text(
        text = text,
        style = AppTextStyles.HEAD_28_40_BOLD,
        modifier = modifier,
        color = Color(0xFF171717)
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
fun CustomSmallButton(
    text: String,
    onclick: () -> Unit,
    enable: Boolean,
){
    Button(
        modifier = Modifier
            .width(104.dp)
            .height(36.dp)
            ,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            when (enable) {
                true -> {
                    Color(0xFFF9F0FF)
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
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = AppTextStyles.BODY_14_20_MEDIUM,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .width(52.dp)
                .height(20.dp),
            color = if(enable){
                Color(0xFF6700CE)
            }else{
                Color(0xFF666666)
            }
        )
    }
}

@Composable
fun CustomTextLittle(text: String , modifier: Modifier) {
    Text(
        text = text,
        modifier = modifier,
        style = AppTextStyles.BODY_14_20_MEDIUM
    )
}

@Composable
fun CustomGenderPick(
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
fun RowScope.CustomSpinnerBox(text: String, onclick: () -> Unit) {
    Row(
        modifier = Modifier
            .weight(1f) // 이제 에러 안 남
            .height(60.dp)
            .background(Color(0xFFF7F7F7), shape = RoundedCornerShape(size = 12.dp))
            .padding(start = 17.dp, end = 16.dp)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                onclick()
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = text,
            style = AppTextStyles.BODY_14_20_MEDIUM
        )

        Spacer(modifier = Modifier.weight(1f))

        Image(
            modifier = Modifier
                .padding(1.dp)
                .width(24.dp)
                .height(24.dp),
            painter = painterResource(R.drawable.ic_down),
            contentDescription = "select$text",
            contentScale = ContentScale.None
        )
    }
}

@Composable
fun BottomSheetSelector(
    yearBottomSheet: Boolean,
    monthBottomSheet: Boolean,
    dayBottomSheet: Boolean,
    cityBottomSheet: Boolean,
    areaBottomSheet: Boolean,
    onSelect: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var selectedCity by remember { mutableStateOf<City?>(null) }

    when {
        cityBottomSheet -> {
            CustomBottomSheet(
                kind = ISCity,
                onSelect = { selectedName ->
                    selectedCity = City.values().find { it.displayName == selectedName }
                    onSelect(selectedName)
                },
                onDismiss = onDismiss
            )
        }

        areaBottomSheet -> {
            CustomBottomSheet(
                kind = ISArea,
                selectedCity = selectedCity, // 이 줄이 핵심!
                onSelect = onSelect,
                onDismiss = onDismiss
            )
        }

        yearBottomSheet -> {
            CustomBottomSheet(kind = ISYear, onSelect = onSelect, onDismiss = onDismiss)
        }

        monthBottomSheet -> {
            CustomBottomSheet(kind = ISMonth, onSelect = onSelect, onDismiss = onDismiss)
        }

        dayBottomSheet -> {
            CustomBottomSheet(kind = ISDay, onSelect = onSelect, onDismiss = onDismiss)
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomBottomSheet(
    kind: String,
    onSelect: (String) -> Unit,
    onDismiss: () -> Unit,
    selectedCity: City? = null // ISArea일 때 사용
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()

    val dataList: List<String> = when (kind) {
        ISYear -> (2025 downTo 1926).map { "${it}년" }
        ISMonth -> (1..12).map { "${it}월" }
        ISDay -> (1..31).map { "${it}일" }

        ISCity -> City.values().map { it.displayName } // 사용자에겐 한글

        ISArea -> {
            selectedCity?.let { city ->
                cityToCountyMap[city]?.map { it.displayName } ?: emptyList()
            } ?: emptyList()
        }

        else -> throw IllegalArgumentException("Invalid kind: $kind")
    }

    // 아래는 그대로 사용
    ModalBottomSheet(
        onDismissRequest = {
            coroutineScope.launch {
                sheetState.hide()
                onDismiss()
            }
        },
        sheetState = sheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
        containerColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .heightIn(min = 200.dp, max = 400.dp)
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .navigationBarsPadding()
        ) {
            // Close Icon
            Box(modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(id = R.drawable.ic_close),
                    contentDescription = "닫기",
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.TopEnd)
                        .clickable {
                            coroutineScope.launch {
                                sheetState.hide()
                                onDismiss()
                            }
                        }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 400.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(dataList) { item ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFFF7F7F7))
                            .clickable {
                                coroutineScope.launch {
                                    onSelect(item)
                                    sheetState.hide()
                                    onDismiss()
                                }
                            }
                            .padding(start = 17.dp, end = 16.dp)
                            .height(48.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = item,
                            style = AppTextStyles.BODY_14_20_MEDIUM,
                            color = Color(0xFF171717),
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ElementTitle(
    text: String,
    color: Long,
) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = text,
        style = AppTextStyles.CAPTION_12_18_SEMI,
        color = Color(color)
    )
}
@Composable
fun ElementCategory(
    text : String,
    color : Long,
    modifier : Modifier
){
    Text(
        modifier = modifier,
        text = text,
        style = AppTextStyles.SUBTITLE_16_24_SEMI,
        color = Color(color)
    )
}

@Composable
fun ElementValue(
    text : String,
    color : Long,
    modifier : Modifier
){
    Text(
        modifier = modifier,
        text = text,
        style = AppTextStyles.BODY_14_20_MEDIUM,
        color = Color(color)
    )
}

@Composable
fun UnderLineText(
    text: String,
    color: Long,
    modifier: Modifier,
) {
    Text(
        text = text,
        style = AppTextStyles.BODY_14_20_MEDIUM,
        color = Color(color),
        textDecoration = TextDecoration.Underline,
        modifier = modifier
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