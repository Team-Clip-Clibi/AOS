package com.sungil.login.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.core.AppTextStyles
import com.example.core.ColorStyle
import com.sungil.domain.model.BannerData
import com.sungil.login.BottomView
import com.sungil.login.CATEGORY
import com.sungil.login.R
import com.sungil.login.bottomNavItems
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue

@Composable
fun BottomNavigation(onClick: () -> Unit) {
    val selectedItem by remember { mutableStateOf(BottomView.Home) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(88.dp)
            .navigationBarsPadding()
            .drawBehind {
                val strokeWidth = 1.dp.toPx()
                drawLine(
                    color = ColorStyle.GRAY_200,
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth = strokeWidth
                )
            }
            .navigationBarsPadding()
            .background(ColorStyle.WHITE_100),
        contentAlignment = Alignment.TopCenter
    ) {
        Row(
            modifier = Modifier
                .wrapContentWidth(Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(36.dp)
        ) {
            bottomNavItems.forEach { item ->
                BottomNavItem(
                    item = item,
                    isSelected = item == selectedItem,
                    onClick = {
                        onClick()
                    }
                )
            }
        }
    }
}

@Composable
fun BottomNavItem(
    item: BottomView,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val contentColor = if (isSelected) ColorStyle.GRAY_800 else ColorStyle.GRAY_500

    Box(
        modifier = modifier
            .width(64.dp)
            .height(56.dp)
            .noVisualFeedbackClickable(onClick),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .padding(top = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Icon(
                painter = painterResource(id = item.icon),
                contentDescription = stringResource(id = item.title),
                modifier = Modifier.size(24.dp),
                tint = contentColor
            )
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = stringResource(id = item.title),
                color = contentColor,
                style = AppTextStyles.CAPTION_12_14_MEDIUM,
                textAlign = TextAlign.Center
            )
        }
    }
}

fun Modifier.noVisualFeedbackClickable(onClick: () -> Unit): Modifier = composed {
    this.then(
        Modifier.pointerInput(Unit) {
            detectTapGestures(onTap = { onClick() })
        }
    )
}

@Composable
fun CustomHomeButton(
    titleText: String,
    contentText: String,
    image: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    padding : Modifier = Modifier
) {
    Button(
        modifier = modifier
            .height(84.dp)
            .border(1.dp, ColorStyle.GRAY_100, shape = RoundedCornerShape(14.dp)),
        onClick = onClick,
        contentPadding = PaddingValues(horizontal = 0.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = ColorStyle.WHITE_100
        ),
        shape = RoundedCornerShape(14.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = painterResource(image),
                contentDescription = "button image",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = padding)
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = titleText,
                    style = AppTextStyles.SUBTITLE_16_24_SEMI,
                    color = ColorStyle.GRAY_800
                )
                Text(
                    text = contentText,
                    style = AppTextStyles.CAPTION_12_18_SEMI,
                    color = ColorStyle.GRAY_600
                )
            }
        }
    }
}
@Composable
fun MatchList() {
    val previewMatchData = ArrayList<MatchInfo>()
    previewMatchData.add(
        MatchInfo(
            matchId = 1,
            category = CATEGORY.CONTENT_RANDOM,
            time = 1,
            location = "홍대 입구역"
        )
    )
    previewMatchData.add(
        MatchInfo(
            matchId = 2,
            category = CATEGORY.CONTENT_ONE_THING,
            time = 0,
            location = "강남역"
        )
    )
    val visibleMatchIds = remember { mutableStateListOf<Int>() }

    LaunchedEffect(previewMatchData.map { it.matchId }) {
        visibleMatchIds.clear()
        previewMatchData.forEachIndexed { _, item ->
            delay(200L)
            visibleMatchIds.add(item.matchId)
        }
    }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        itemsIndexed(previewMatchData, key = { _, item -> item.matchId }) { _, match ->
            val isVisible = match.matchId in visibleMatchIds
            AnimatedVisibility(
                visible = isVisible,
                enter = fadeIn(
                    animationSpec = tween(
                        durationMillis = 600,
                        easing = FastOutSlowInEasing
                    )
                ) + slideInHorizontally(
                    animationSpec = tween(
                        durationMillis = 600,
                        easing = FastOutSlowInEasing
                    ),
                    initialOffsetX = { it / 2 }
                )
            ) {
                MatchCard(
                    category = match.category,
                    time = if (match.time == 0) "오늘" else "${match.time}일 후",
                    location = match.location,
                )
            }
        }

        item {
            MainCardView(
                contentString = stringResource(R.string.txt_home_not_meeting),
            )

        }
    }
}

data class MatchInfo(
    val matchId: Int,
    val category: CATEGORY,
    val time: Int,
    val location: String,
)

@Composable
fun MainCardView(
    contentString: String,
) {
    Column(
        modifier = Modifier
            .width(192.dp)
            .height(174.dp)
            .background(color = ColorStyle.GRAY_200, shape = RoundedCornerShape(size = 24.dp))
            .border(width = 1.dp, color = ColorStyle.GRAY_300, shape = RoundedCornerShape(24.dp))
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.ic_plus_gray),
            modifier = Modifier.size(40.dp),
            contentDescription = "add meeting"
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = contentString,
            style = AppTextStyles.BODY_14_20_MEDIUM,
            color = ColorStyle.GRAY_600,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun MatchCard(
    category: CATEGORY,
    time: String,
    location: String,
) {
    Column(
        modifier = Modifier
            .width(192.dp)
            .height(174.dp)
            .background(
                color = when (category) {
                    CATEGORY.CONTENT_ONE_THING -> ColorStyle.PURPLE_400
                    CATEGORY.CONTENT_RANDOM -> ColorStyle.GREEN_100
                },
                shape = RoundedCornerShape(size = 24.dp)
            )
            .padding(20.dp)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = when (category) {
                CATEGORY.CONTENT_ONE_THING -> stringResource(R.string.card_one_thing_title)
                CATEGORY.CONTENT_RANDOM -> stringResource(R.string.card_random_title)
            },
            style = AppTextStyles.SUBTITLE_18_26_SEMI,
            color = ColorStyle.WHITE_100
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
                .background(color = ColorStyle.WHITE_100, shape = RoundedCornerShape(size = 12.dp))
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = when (category) {
                    CATEGORY.CONTENT_ONE_THING -> painterResource(R.drawable.ic_calendar_purple)
                    CATEGORY.CONTENT_RANDOM -> painterResource(R.drawable.ic_calendar_green)
                },
                contentDescription = "match date",
                modifier = Modifier.size(24.dp)
            )

            Text(
                text = time,
                style = AppTextStyles.SUBTITLE_16_24_SEMI,
                color = ColorStyle.GRAY_800
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
                .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 12.dp))
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = when (category) {
                    CATEGORY.CONTENT_ONE_THING -> painterResource(R.drawable.ic_location_purple)
                    CATEGORY.CONTENT_RANDOM -> painterResource(R.drawable.ic_location_green)
                },
                contentDescription = "match date",
                modifier = Modifier.size(24.dp)
            )

            Text(
                text = location,
                style = AppTextStyles.SUBTITLE_16_24_SEMI,
                color = ColorStyle.GRAY_800
            )
        }
    }
}

@Composable
fun AutoSlideNotice(data: List<String>) {
    var currentIndex by remember { mutableIntStateOf(0) }
    var visible by remember { mutableStateOf(true) }

    LaunchedEffect(data) {
        while (true) {
            delay(1800)
            visible = false
            delay(300)
            currentIndex = (currentIndex + 1) % data.size
            visible = true
        }
    }

    val currentNotification = data[currentIndex]

    val targetWidth = if (visible) 1f else 0.9f
    val animatedWidthFraction by animateFloatAsState(
        targetValue = targetWidth,
        animationSpec = tween(300),
        label = "widthFraction"
    )

    val animatedAlpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(300),
        label = "alpha"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer {
                scaleX = animatedWidthFraction
                alpha = animatedAlpha
            }
    ) {
        NoticeBar(
            title = listOf(currentNotification)
        )
    }
}

@Composable
fun NoticeBar(title: List<String>) {
    val currentIndex by remember { mutableIntStateOf(0) }
    val data = title[currentIndex]
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(34.dp)
            .background(color = ColorStyle.GRAY_200)
            .padding(start = 17.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
    ) {
        Text(
            text = data,
            style = AppTextStyles.CAPTION_12_18_SEMI,
            color = ColorStyle.GRAY_800,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun LoginPager(
    state: PagerState,
    data: List<BannerData>,
    modifier: Modifier,
) {
    HorizontalPager(
        state = state,
        modifier = modifier
    ) { page ->
        Card(
            modifier = Modifier
                .fillMaxSize()
                .background(color = ColorStyle.PURPLE_100, shape = RoundedCornerShape(24.dp))
                .graphicsLayer {
                    val pageOffset = (
                            (state.currentPage - page) + state.currentPageOffsetFraction
                            ).absoluteValue
                    alpha = lerp(
                        start = 0.5f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                },
            shape = RoundedCornerShape(24.dp),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = ColorStyle.PURPLE_100, shape = RoundedCornerShape(24.dp))
            ) {
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .background(
                            color = ColorStyle.PURPLE_100,
                            shape = RoundedCornerShape(24.dp)
                        )
                        .padding(start = 16.dp, end = 16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val context = LocalContext.current
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(data[page].image)
                            .crossfade(true)
                            .build(),
                        contentDescription = data[page].headText,
                        modifier = Modifier.size(200.dp)
                    )
                    Spacer(modifier = Modifier.height(40.dp))
                    Text(
                        text = data[page].headText ?: "",
                        style = AppTextStyles.TITLE_20_28_SEMI,
                        color = ColorStyle.GRAY_800,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}

@Composable
fun PageIndicator(
    numberOfPages: Int,
    selectedPage: Int = 0,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            repeat(numberOfPages) {
                Indicator(
                    isSelected = it == selectedPage,
                    selectedColor = ColorStyle.PURPLE_300,
                    defaultColor = ColorStyle.GRAY_300,
                    defaultRadius = 10.dp,
                    selectedLength = 16.dp,
                )
            }
        }
    }
}

/**
 * pager indicator item
 */
@Composable
fun Indicator(
    isSelected: Boolean,
    selectedColor: Color,
    defaultColor: Color,
    defaultRadius: Dp,
    selectedLength: Dp,
    modifier: Modifier = Modifier,
) {
    val width by animateDpAsState(
        targetValue = if (isSelected) selectedLength else defaultRadius,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy), label = ""
    )
    Box(
        modifier = modifier
            .width(width)
            .clip(CircleShape)
            .background(color = if (isSelected) selectedColor else defaultColor)
    )
}