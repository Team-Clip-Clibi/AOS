package com.sungil.onethingmatch.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.core.AppTextStyles
import com.example.core.ColorStyle
import com.example.core.TopAppBarNumber
import com.sungil.domain.model.WeekData
import com.sungil.onethingmatch.CATEGORY
import kotlinx.coroutines.delay
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.sungil.domain.MATCHTIME
import com.sungil.onethingmatch.R

@Composable
fun SlidingTextBox(textList: List<String>) {
    val index = remember { mutableIntStateOf(0) }
    val currentText = textList.getOrNull(index.intValue) ?: ""

    // 자동 순회 (2초마다 1개씩)
    LaunchedEffect(Unit) {
        while (true) {
            delay(2000L)
            index.intValue = (index.intValue + 1) % textList.size
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(32.dp)
            .background(color = ColorStyle.PURPLE_100, shape = RoundedCornerShape(size = 4.dp))
            .padding(start = 12.dp, top = 6.dp, end = 12.dp, bottom = 6.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        AnimatedContent(
            targetState = currentText,
            transitionSpec = {
                slideInVertically { height -> height } + fadeIn() togetherWith
                        slideOutVertically { height -> -height } + fadeOut()
            },
            label = "subject"
        ) { text ->
            Text(
                text = text,
                style = AppTextStyles.BODY_14_20_MEDIUM,
                color = ColorStyle.GRAY_800
            )
        }
    }
}

@Composable
fun CategoryItemView(
    category: CATEGORY,
    isSelect: Boolean,
    onClick: () -> Unit,
) {
    val modifier = when (isSelect) {
        true -> {
            Modifier
                .width(114.dp)
                .height(88.dp)
                .border(
                    width = 1.dp,
                    color = ColorStyle.PURPLE_200,
                    shape = RoundedCornerShape(8.dp)
                )
                .background(color = ColorStyle.PURPLE_100, shape = RoundedCornerShape(8.dp))
                .padding(10.dp)
        }

        false -> {
            Modifier
                .width(114.dp)
                .height(88.dp)
                .background(ColorStyle.GRAY_100, shape = RoundedCornerShape(8.dp))
                .padding(10.dp)
        }
    }
    Box(
        modifier = modifier
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = category.displayName,
                style = AppTextStyles.BODY_14_20_MEDIUM,
                color = ColorStyle.GRAY_800
            )
        }
    }
}

@Composable
fun TopAppBarWithProgress(
    title: String,
    currentPage: Int,
    totalPage: Int,
    onBackClick: () -> Unit,
) {
    val animatedProgress by animateFloatAsState(
        targetValue = if (currentPage >= 0) currentPage / totalPage.toFloat() else 0f,
        animationSpec = tween(durationMillis = 500),
        label = "progress"
    )

    Column {
        TopAppBarNumber(
            title = title,
            currentPage = if (currentPage >= 0) currentPage else 0,
            totalPage = totalPage,
            onBackClick = onBackClick
        )
        if (currentPage >= 0) {
            LinearProgressIndicator(
                progress = { animatedProgress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp),
                color = ColorStyle.PURPLE_400,
                trackColor = ColorStyle.GRAY_200
            )
        } else {
            Spacer(modifier = Modifier.height(0.dp))
        }
    }
}

@Composable
fun OneThingDayList(
    selectData: Set<WeekData>,
    item: List<WeekData>,
    onItemSelect: (WeekData) -> Unit,
) {
    val flattened = item.flatMap { day ->
        day.timeSlots.map { slot ->
            day.copy(timeSlots = listOf(slot))
        }
    }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(flattened) { current ->
            OneThingDayAdapter(
                isSelect = selectData.contains(current),
                onClick = { onItemSelect(current) },
                date = current.date,
                dayOfWeek = current.dayOfWeek,
                timeSlots = current.timeSlots.firstOrNull() ?: ""
            )
        }
    }
}

@Composable
fun OneThingDayAdapter(
    isSelect: Boolean,
    onClick: () -> Unit,
    date: String,
    dayOfWeek: String,
    timeSlots: String,
) {
    val cardColor = if (isSelect) ColorStyle.PURPLE_100 else ColorStyle.GRAY_100
    val borderColor = if (isSelect) ColorStyle.PURPLE_200 else Color.Transparent
    val bottomColor = if (isSelect) ColorStyle.PURPLE_200 else ColorStyle.GRAY_200

    Column(
        modifier = Modifier
            .width(88.dp)
            .height(108.dp)
            .background(color = cardColor, shape = RoundedCornerShape(8.dp))
            .border(1.dp, borderColor, shape = RoundedCornerShape(8.dp))
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = dayOfWeek,
                style = AppTextStyles.BODY_14_20_MEDIUM,
                color = ColorStyle.GRAY_800
            )
            Text(
                text = date,
                style = AppTextStyles.SUBTITLE_18_26_SEMI,
                color = ColorStyle.GRAY_800
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
                .clip(RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp))
                .background(bottomColor),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = when(timeSlots){
                    MATCHTIME.LAUNCH.name -> stringResource(R.string.txt_date_launch)
                    MATCHTIME.DINNER.name -> stringResource(R.string.txt_date_dinner)
                    else -> stringResource(R.string.txt_date_dinner)
                },
                style = AppTextStyles.CAPTION_12_18_SEMI,
                color = ColorStyle.GRAY_700
            )
        }
    }
}

@Composable
fun SelectDateList(
    selectItem : Set<WeekData>,
    onRemoveClick : (WeekData) -> Unit,
    modifier : Modifier
){
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(selectItem.toList()){ item->
            SelectDate(
                onClick = {onRemoveClick(item)},
                date = item.date,
                dayOfWeek = item.dayOfWeek,
                timeSlots = item.timeSlots.firstOrNull() ?: ""
            )
        }
    }
}

@Composable
fun SelectDate(
    onClick: () -> Unit,
    date: String,
    dayOfWeek: String,
    timeSlots: String,
) {
    val timeDate =  when(timeSlots){
        MATCHTIME.LAUNCH.name -> stringResource(R.string.txt_date_launch)
        MATCHTIME.DINNER.name -> stringResource(R.string.txt_date_dinner)
        else -> stringResource(R.string.txt_date_dinner)
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$date($dayOfWeek) · $timeDate",
                style = AppTextStyles.BODY_14_20_REGULAR,
                color = ColorStyle.GRAY_800
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_close),
                contentDescription = "close",
                tint = ColorStyle.GRAY_300,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onClick() }
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = ColorStyle.GRAY_200
        )
    }
}

@Composable
fun EventView(
    title: String,
    content: String,
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .height(68.dp)
            .background(color = ColorStyle.WHITE_100, shape = RoundedCornerShape(size = 12.dp))
            .padding(start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_discount),
            contentDescription = "discount",
            tint = Color.Unspecified,
            modifier = Modifier.size(36.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = title,
                style = AppTextStyles.CAPTION_12_18_SEMI,
                color = ColorStyle.PURPLE_400
            )
            Text(
                text = content,
                style = AppTextStyles.SUBTITLE_16_24_SEMI,
                color = ColorStyle.GRAY_800
            )
        }
    }
}