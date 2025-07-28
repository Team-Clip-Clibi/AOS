package com.sungil.main.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.core.AppTextStyles
import com.example.core.ColorStyle
import com.sungil.domain.CATEGORY
import com.sungil.domain.model.BannerData
import com.sungil.domain.model.MatchInfo
import com.sungil.domain.model.MatchProgressUiModel
import com.sungil.domain.model.NotificationData
import com.sungil.main.BottomSheetView
import com.sungil.main.CONTENT_NOTICE
import com.sungil.main.R
import com.sungil.main.BottomView
import com.sungil.main.MainViewModel
import com.sungil.main.bottomNavItems
import com.sungil.main.bottomSheetView.ConversationMatchView
import com.sungil.main.bottomSheetView.EndMatchView
import com.sungil.main.bottomSheetView.HostView
import com.sungil.main.bottomSheetView.MatchParticipantView
import com.sungil.main.bottomSheetView.StartMatchView
import com.sungil.main.bottomSheetView.TmiMatchView
import com.sungil.main.bottomSheetView.OneThingContentView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun BottomNavigation(navController: NavHostController) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp)
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
            .background(Color.White),
        contentAlignment = Alignment.TopCenter
    ) {
        Row(
            modifier = Modifier
                .wrapContentWidth(Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(36.dp)
        ) {
            bottomNavItems.forEach { item ->
                val isSelected = currentRoute == item.screenRoute
                BottomNavItem(
                    item = item,
                    isSelected = isSelected,
                    onClick = {
                        if (currentRoute != item.screenRoute) {
                            navController.navigate(item.screenRoute) {
                                navController.graph.startDestinationRoute?.let {
                                    popUpTo(it) { saveState = true }
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
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
fun CustomMainPageTopBar(text: String) {
    Box(
        modifier = Modifier
            .background(color = ColorStyle.WHITE_100)
            .fillMaxWidth()
            .statusBarsPadding()
            .height(48.dp)
            .padding(start = 17.dp, end = 12.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = text,
            modifier = Modifier.fillMaxWidth(),
            style = AppTextStyles.TITLE_20_28_SEMI,
            color = ColorStyle.GRAY_800
        )
    }
}

@Composable
fun MyPageItem(text: String, icon: Int, click: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(
                color = Color(0xFFFFFFFF),
                shape = RoundedCornerShape(size = 8.dp)
            )
            .padding(start = 17.dp, end = 16.dp, top = 10.dp, bottom = 10.dp)
            .clickable { click() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = "icon",
        )

        Spacer(modifier = Modifier.width(14.dp))

        Text(
            text = text,
            modifier = Modifier.weight(1f),
            style = AppTextStyles.BODY_14_20_MEDIUM,
            color = Color(0xFF171717),
            textAlign = TextAlign.Start
        )
    }
}

@Composable
fun HomeViewTopBar(
    click: () -> Unit,
    image: Int,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .height(48.dp)
            .background(color = ColorStyle.GRAY_100)
            .padding(start = 17.dp, end = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_logo_str),
            contentDescription = "logo",
            modifier = Modifier.size(
                width = 100.dp,
                height = 24.dp
            )
        )

        Image(
            painter = painterResource(id = image),
            contentDescription = "알람",
            modifier = Modifier
                .size(24.dp)
                .clickable { click() }
        )
    }
}

@Composable
fun NotificationBarListStable(
    notifications: List<NotificationData>,
    notifyClick: (String) -> Unit,
) {
    var currentIndex by remember { mutableIntStateOf(0) }
    var visible by remember { mutableStateOf(true) }

    LaunchedEffect(notifications) {
        while (true) {
            delay(1800)
            visible = false
            delay(300)
            currentIndex = (currentIndex + 1) % notifications.size
            visible = true
        }
    }

    val currentNotification = notifications[currentIndex]

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
        CustomNotifyBar(
            notifications = listOf(currentNotification),
            notifyClick = notifyClick,
        )
    }
}

@Composable
fun CustomNotifyBar(
    notifications: List<NotificationData>,
    notifyClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val currentIndex by remember { mutableIntStateOf(0) }
    val currentNotification = notifications[currentIndex]

    val titleColor = if (currentNotification.noticeType == CONTENT_NOTICE) Color(0xFFFB4F4F) else Color(0xFF171717)
    val titleText = if (currentNotification.noticeType == CONTENT_NOTICE)
        stringResource(R.string.notify_notice)
    else
        stringResource(R.string.notify_article)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(34.dp)
            .background(Color(0xFFEFEFEF))
            .clickable { notifyClick(currentNotification.link) }
            .padding(start = 17.dp, end = 8.5.dp)
    ) {
        Row(
            modifier = Modifier.align(Alignment.CenterStart),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = titleText,
                style = AppTextStyles.CAPTION_12_18_SEMI,
                color = titleColor
            )

            Spacer(Modifier.width(12.dp))

            Text(
                text = currentNotification.content,
                style = AppTextStyles.CAPTION_12_18_SEMI,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun MeetingCardList(
    matchList: List<MatchInfo>,
    onAddClick: () -> Unit,
    canAdd: Boolean,
) {
    val visibleMatchIds = remember { mutableStateListOf<Int>() }

    LaunchedEffect(matchList.map { it.matchingId }) {
        visibleMatchIds.clear()
        matchList.forEachIndexed { _, item ->
            delay(200L)
            visibleMatchIds.add(item.matchingId)
        }
    }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 17.dp, end = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        itemsIndexed(matchList, key = { _, item -> item.matchingId }) { _, match ->
            val isVisible = match.matchingId in visibleMatchIds
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
                MeetingCard(
                    category = match.category,
                    time = if (match.daysUntilMeeting == 0) "오늘" else "${match.daysUntilMeeting}일 후",
                    location = match.meetingPlace
                )
            }
        }

        if (canAdd) {
            val isFirstCard = matchList.isEmpty()
            item {
                MainCardView(
                    contentString = stringResource(R.string.txt_home_not_meeting),
                    addClick = onAddClick,
                    modifier = if (isFirstCard) Modifier.fillParentMaxWidth() else Modifier.width(
                        192.dp
                    )
                )
            }
        }
    }
}

@Composable
fun MainCardView(
    contentString: String,
    addClick: () -> Unit,
    modifier: Modifier = Modifier,

    ) {
    Column(
        modifier = modifier
            .height(174.dp)
            .background(color = ColorStyle.GRAY_200, shape = RoundedCornerShape(size = 24.dp))
            .border(width = 1.dp, color = ColorStyle.GRAY_300, shape = RoundedCornerShape(24.dp))
            .padding(20.dp)
            .clickable { addClick() },
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
fun MeetingCard(
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
                .background(color = ColorStyle.WHITE_100, shape = RoundedCornerShape(size = 12.dp))
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
                modifier = Modifier.size(44.dp)
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
fun AutoSlidingBanner(
    image: List<BannerData>,
    intervalMillis: Long = 3000L,
    scrollDuration: Int = 500,
) {
    val pagerState = rememberPagerState(pageCount = { image.size })

    LaunchedEffect(Unit) {
        while (true) {
            delay(intervalMillis)
            if (!pagerState.isScrollInProgress) {
                val nextPage = (pagerState.currentPage + 1) % image.size
                pagerState.animateScrollToPage(
                    page = nextPage,
                    animationSpec = tween(
                        durationMillis = scrollDuration,
                        easing = FastOutSlowInEasing
                    )
                )
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
        ) { page ->
            val context = LocalContext.current
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(image[page].image)
                    .crossfade(true)
                    .build(),
                contentDescription = "banner",
                modifier = Modifier.fillMaxWidth()
            )
        }
        PageIndicator(
            numberOfPages = image.size,
            selectedPage = pagerState.currentPage,
            modifier = Modifier.padding(bottom = 2.dp),
            selectedColor = Color(0xFF6700CE) ,
            defaultColor = Color(0xFFDCDCDC),
            space = 8.dp,
        )
    }
}
@Composable
fun PageIndicator(
    numberOfPages: Int,
    selectedPage: Int = 0,
    selectedColor: Color = Color.White,
    defaultColor: Color = Color.Gray,
    space: Dp = 6.dp,
    modifier: Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(space)
        ) {
            repeat(numberOfPages) {
                Indicator(
                    isSelected = it == selectedPage,
                    selectedColor = selectedColor,
                    defaultColor = defaultColor,
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
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(1.dp)
            .size(6.dp)
            .aspectRatio(1f)
            .clip(CircleShape)
            .background(if (isSelected) selectedColor else defaultColor)
    )
}

@Composable
fun SmallButton(
    text: String,
    onClick: () -> Unit,
    isClick: Boolean,
) {
    Button(
        onClick = onClick,
        modifier = Modifier.height(30.dp),
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isClick) ColorStyle.PURPLE_400 else ColorStyle.WHITE_100,
            contentColor = if (isClick) ColorStyle.WHITE_100 else ColorStyle.GRAY_600
        ),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = text,
            style = AppTextStyles.CAPTION_12_18_SEMI,
            color = if (isClick) ColorStyle.WHITE_100 else ColorStyle.GRAY_600
        )
    }
}

@Composable
fun ReviewImageView(
    image: Int,
    content: String,
    isSelect: Boolean,
    isClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .width(60.dp)
            .height(86.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(
                    color = ColorStyle.GRAY_200,
                    shape = RoundedCornerShape(size = 12.dp)
                )
                .clickable { isClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(image),
                contentDescription = content,
                tint = if (isSelect) ColorStyle.PURPLE_400 else ColorStyle.GRAY_400,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = content,
            style = AppTextStyles.BODY_14_20_MEDIUM,
            color = if (isSelect) ColorStyle.PURPLE_400 else ColorStyle.GRAY_600,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
fun MatchIngFlowView(modifier: Modifier,onClick: () -> Unit) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(74.dp)
            .background(color = ColorStyle.GRAY_700, shape = RoundedCornerShape(50.dp))
            .padding(
                start = 24.dp, top = 14.dp, end = 24.dp, bottom = 14.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_match_flow_person),
            contentDescription = "match is ing",
            modifier = Modifier
                .width(19.dp)
                .height(18.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = stringResource(R.string.match_flow_title),
                style = AppTextStyles.SUBTITLE_16_24_SEMI,
                color = ColorStyle.WHITE_100,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = stringResource(R.string.match_flow_content),
                style = AppTextStyles.BODY_14_20_REGULAR,
                color = ColorStyle.GRAY_300,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Icon(
            painter = painterResource(R.drawable.ic_arrow_right),
            contentDescription = "match detail",
            modifier = Modifier
                .width(24.dp)
                .height(24.dp)
                .clickable { onClick() },
            tint = ColorStyle.WHITE_100
        )
    }
}


@Composable
fun ReviewItemContent(
    isClick: () -> Unit,
    isSelect: Boolean,
    content: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            painter = painterResource(com.example.core.R.drawable.ic_check),
            contentDescription = content,
            tint = ColorStyle.WHITE_100,
            modifier = Modifier
                .size(16.dp)
                .background(
                    color = if (isSelect) ColorStyle.PURPLE_400 else ColorStyle.GRAY_400,
                    shape = RoundedCornerShape(size = 4.dp)
                )
                .padding(start = 2.dp, top = 2.dp, end = 2.dp, bottom = 2.dp)
                .clickable { isClick() }
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = content,
            style = AppTextStyles.BODY_14_20_MEDIUM,
            color = ColorStyle.GRAY_700
        )
    }
}

@Composable
fun ReviewTextField(
    value: String,
    onValueChange: (String) -> Unit,
) {
    TextField(
        value = value,
        onValueChange = { newText ->
            onValueChange(newText)
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(152.dp)
            .background(color = ColorStyle.WHITE_100, shape = RoundedCornerShape(12.dp))
            .border(
                width = 1.dp,
                color = ColorStyle.GRAY_300,
                shape = RoundedCornerShape(size = 12.dp)
            ),
        singleLine = false,
        placeholder = {
            Text(
                text = stringResource(R.string.review_detail_hint),
                style = AppTextStyles.BODY_14_20_MEDIUM,
                color = ColorStyle.GRAY_500
            )
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = ColorStyle.WHITE_100,
            unfocusedContainerColor = ColorStyle.WHITE_100,
            disabledContainerColor = ColorStyle.WHITE_100,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = ColorStyle.GRAY_500,
            cursorColor =ColorStyle.GRAY_800,
            focusedPlaceholderColor = ColorStyle.GRAY_500,
            unfocusedPlaceholderColor = ColorStyle.GRAY_500,
            focusedTextColor = ColorStyle.GRAY_800,
            unfocusedTextColor = ColorStyle.GRAY_800
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchingBottomSheet(
    viewModel: MainViewModel,
    onClick: () -> Unit,
    matchData : MatchProgressUiModel //TODO 배포시 무조건 수정
) {
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    ModalBottomSheet(
        onDismissRequest = {
            scope.launch { bottomSheetState.hide() }
            viewModel.initBottomSheetButton()
        },
        sheetState = bottomSheetState,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.9f),
        contentColor = ColorStyle.WHITE_100,
        containerColor = ColorStyle.WHITE_100
    ) {
        val view = viewModel.bottomSheetButton.collectAsState()
        val dummyConversationData = listOf(
            "원띵에 대해 이야기 해 보아요",
            "Android Native vs Ios Native",
            "가데이터 가데이터 가데이터 가데이터 가데이터"
        )
        when (view.value) {
            BottomSheetView.MATCH_START_HELLO_VIEW -> StartMatchView(
                onClick = {
                    viewModel.setBottomSheetButton(
                        BottomSheetView.MATCH_START_HOST_VIEW
                    )
                }
            )

            BottomSheetView.MATCH_START_HOST_VIEW -> HostView(
                onClick = {
                    viewModel.setBottomSheetButton(BottomSheetView.MATCH_START_INTRODUCE)
                }
            )

            BottomSheetView.MATCH_START_INTRODUCE -> MatchParticipantView(
                onClick = {
                    viewModel.setBottomSheetButton(BottomSheetView.MATCH_START_TMI)
                },
                participant = matchData.nickName
            )

            BottomSheetView.MATCH_START_TMI -> TmiMatchView(
                onClick = {
                    viewModel.setBottomSheetButton(BottomSheetView.MATCH_STAT_ONE_THING)
                },
                data = matchData.tmi
            )

            BottomSheetView.MATCH_STAT_ONE_THING -> OneThingContentView(
                onClick = {
                    viewModel.setBottomSheetButton(BottomSheetView.MATCH_START_CONVERSATION)
                },
                oneThingContent = matchData.content
            )

            BottomSheetView.MATCH_START_CONVERSATION -> ConversationMatchView(
                onClick = {
                    viewModel.setBottomSheetButton(BottomSheetView.MATCH_START_END)
                },
                conversationData = dummyConversationData
            )

            BottomSheetView.MATCH_START_END -> EndMatchView(
                onClick = {
                    scope.launch {
                        bottomSheetState.hide()
                        viewModel.initBottomSheetButton()
                        onClick()
                    }
                }
            )
        }
    }
}