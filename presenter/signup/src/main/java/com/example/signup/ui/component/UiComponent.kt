package com.example.signup.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import com.example.core.AppTextStyles
import com.example.core.ButtonXXLPurple400
import com.example.core.ColorStyle
import com.example.signup.City
import com.example.signup.ISArea
import com.example.signup.ISCity
import com.example.signup.ISDay
import com.example.signup.ISMonth
import com.example.signup.ISYear
import com.example.signup.cityToCountyMap
import kotlinx.coroutines.launch


@Composable
fun CustomCheckBox(
    text: String,
    checked: Boolean,
    modifier: Modifier = Modifier,
    onCheckChange: (Boolean) -> Unit,
    isIconShow: Boolean = true,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .clickable { onCheckChange(!checked) },
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
            color =  if (checked) ColorStyle.GRAY_800 else ColorStyle.GRAY_400
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
                color = if (checked) ColorStyle.PURPLE_400 else ColorStyle.GRAY_400,
                shape = CircleShape
            )
            .clickable { onCheckedChange(!checked) },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = if (checked) "동의항목" else "비동의항목",
            tint = Color.White,
            modifier = Modifier.size(32.dp)
        )
    }
}

@Composable
fun CustomTextField(
    text: String,
    modifier: Modifier,
    onValueChange: (String) -> Unit,
    inputType: KeyboardType,
    hint: String,
) {
    TextField(
        value = text,
        onValueChange = onValueChange,
        modifier = modifier.background(
            color = ColorStyle.GRAY_100,
            shape = RoundedCornerShape(12.dp)
        ),
        keyboardOptions = KeyboardOptions(keyboardType = inputType),
        singleLine = true,
        placeholder = {
            Text(
                hint,
                style = AppTextStyles.SUBTITLE_16_24_SEMI,
                color = ColorStyle.GRAY_600,
                modifier = Modifier.padding(end = 16.dp)
            )
        },
        textStyle = AppTextStyles.SUBTITLE_16_24_SEMI.copy(color = ColorStyle.GRAY_800),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = ColorStyle.GRAY_100,
            unfocusedContainerColor = ColorStyle.GRAY_100,
            disabledContainerColor = ColorStyle.GRAY_100,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun RowScope.CustomSpinnerBox(text: String, onclick: () -> Unit) {
    Row(
        modifier = Modifier
            .weight(1f)
            .height(60.dp)
            .background(color = ColorStyle.GRAY_100, shape = RoundedCornerShape(size = 12.dp))
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
                    selectedCity = City.entries.find { it.displayName == selectedName }
                    onSelect(selectedName)
                },
                onDismiss = onDismiss
            )
        }

        areaBottomSheet -> {
            CustomBottomSheet(
                kind = ISArea,
                selectedCity = selectedCity,
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
    selectedCity: City? = null
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()

    val dataList: List<String> = when (kind) {
        ISYear -> (2025 downTo 1926).map { "${it}년" }
        ISMonth -> (1..12).map { "${it}월" }
        ISDay -> (1..31).map { "${it}일" }

        ISCity -> City.entries.map { it.displayName }

        ISArea -> {
            selectedCity?.let { city ->
                cityToCountyMap[city]?.map { it.displayName } ?: emptyList()
            } ?: emptyList()
        }

        else -> throw IllegalArgumentException("Invalid kind: $kind")
    }


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
                            .background(ColorStyle.GRAY_100)
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
                            color = ColorStyle.GRAY_800,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun UnderLineText(
    text: String,
    modifier: Modifier,
) {
    Text(
        text = text,
        style = AppTextStyles.BODY_14_20_MEDIUM,
        color = ColorStyle.GRAY_600,
        textDecoration = TextDecoration.Underline,
        modifier = modifier
    )
}

@Composable
fun BottomBar(
    isEnable: Boolean,
    buttonText: String,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(bottom = 8.dp)
    ) {
        HorizontalDivider(thickness = 1.dp, color = ColorStyle.GRAY_200)
        Spacer(modifier = Modifier.height(8.dp))
        ButtonXXLPurple400(
            onClick = onClick,
            buttonText = buttonText,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 17.dp),
            isEnable = isEnable
        )
    }
}

@Composable
fun AlreadySignUpBottomBar(
    onClick: () -> Unit,
    isNotMyAccount: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(bottom = 8.dp)
    ) {
        HorizontalDivider(thickness = 1.dp, color = ColorStyle.GRAY_200)
        Spacer(modifier = Modifier.height(8.dp))
        ButtonXXLPurple400(
            onClick = onClick,
            buttonText = stringResource(R.string.btn_signUp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 17.dp),
        )
        Spacer(modifier = Modifier.height(18.dp))
        UnderLineText(
            text = stringResource(R.string.btn_signUp_not_mine),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clickable {
                    isNotMyAccount()
                }
        )
    }
}