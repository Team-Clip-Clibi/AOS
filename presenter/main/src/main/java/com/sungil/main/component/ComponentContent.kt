package com.sungil.main.component

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.core.AppTextStyles
import com.sungil.domain.CATEGORY
import com.sungil.domain.model.MatchInfo
import com.sungil.main.CONTENT_NOTICE
import com.sungil.main.R
import com.sungil.main.Screen
import com.sungil.main.bottomNavItems

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
                    color = Color(0xFFEFEFEF),
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth = strokeWidth
                )
            }
            .background(Color.White),
        contentAlignment = Alignment.TopCenter
    ) {
        Row(
            modifier = Modifier
                .wrapContentWidth(Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(36.dp) // 아이템 간 간격
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
    item: Screen,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val contentColor = if (isSelected) Color(0xFF171717) else Color(0xFF989898)

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
fun CustomMyPageAppBar(text: String) {
    Box(
        modifier = Modifier
            .background(Color(0xFFF7F7F7))
            .fillMaxWidth()
            .height(48.dp)
            .padding(start = 17.dp, end = 12.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = text,
            modifier = Modifier.fillMaxWidth(),
            style = AppTextStyles.TITLE_20_28_SEMI,
            color = Color(0xFF000000)
        )
    }
}

@Composable
fun CustomMyPageButton(
    text: String,
    color: Long,
    textColor: Long,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(color),
            contentColor = Color(textColor)
        ),
        elevation = null
    ) {
        Text(
            text = text,
            style = AppTextStyles.BODY_14_20_MEDIUM,
            textAlign = TextAlign.Center
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
fun CustomHomeTopBar(
    text: String,
    bellImage: Int,
    click: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(Color(0xFFF7F7F7))
            .padding(horizontal = 17.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_logo_str),
                contentDescription = "logo",
                modifier = Modifier.size(
                    width = 100.dp,
                    height = 24.dp
                )
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = text,
                style = AppTextStyles.TITLE_20_28_SEMI,
                color = Color.Black
            )
            Spacer(Modifier.weight(1f))
            Icon(
                painter = painterResource(id = bellImage),
                contentDescription = "알람",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { click() }
            )
        }
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
            painter = painterResource(id = com.sungil.editprofile.R.drawable.ic_message),
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
fun CustomNotifyBar(
    noticeType: String,
    content: String,
    link: String,
    notifyClick: (String) -> Unit,
    notifyClose: () -> Unit,
) {
    val titleColor = if (noticeType == CONTENT_NOTICE) Color(0xFFFB4F4F) else Color(0xFF171717)
    val titleText = if (noticeType == CONTENT_NOTICE)
        stringResource(R.string.notify_notice)
    else
        stringResource(R.string.notify_article)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(34.dp)
            .background(Color(0xFFEFEFEF))
            .clickable { notifyClick(link) }
            .padding(start = 17.dp , end = 8.5.dp)
    ) {
        // 왼쪽(타이틀 + 내용)을 한 Row에 배치
        Row(
            modifier = Modifier
                .align(Alignment.CenterStart),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = titleText,
                style = AppTextStyles.CAPTION_12_18_SEMI,
                color = titleColor
            )

            Spacer(Modifier.width(12.dp))

            Text(
                text = content,
                style = AppTextStyles.CAPTION_12_18_SEMI,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        // 오른쪽 닫기 아이콘
        Icon(
            painter = painterResource(R.drawable.ic_close_gray),
            contentDescription = "close alarm",
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .size(17.dp)
                .clickable { notifyClose() },
            tint = Color(0xFF718096)
        )
    }
}

@Composable
fun HomeTitleText(
    text: String,
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .height(28.dp),
        text = text,
        style = AppTextStyles.TITLE_20_28_SEMI,
        color = Color(0xFF383838)
    )
}

@Composable
fun MeetingCardList(
    matchList: List<MatchInfo>,
    onAddClick: () -> Unit,
    canAdd: Boolean,
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        itemsIndexed(matchList, key = { _, item -> item.matchingId }) { _, match ->
            AnimatedVisibility(
                visible = true,
                enter = fadeIn(tween(300)) + slideInHorizontally(
                    animationSpec = tween(300),
                    initialOffsetX = { it / 2 }
                )
            ) {
                MeetingCard(
                    category = match.category,
                    time = "${match.daysUntilMeeting}일 후",
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
    modifier: Modifier = Modifier

) {
    Column(
        modifier = modifier
            .height(174.dp)
            .background(color = Color(0xFFEFEFEF), shape = RoundedCornerShape(size = 24.dp))
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
            color = Color(0xFF666666),
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
                    CATEGORY.CONTENT_ONE_THING -> Color(0xFF6700CE)
                    CATEGORY.CONTENT_RANDOM -> Color(0xFF61C9A0)
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
            color = Color(0xFFFFFFFF)
        )

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
                    CATEGORY.CONTENT_ONE_THING -> painterResource(R.drawable.ic_calendar_purple)
                    CATEGORY.CONTENT_RANDOM -> painterResource(R.drawable.ic_calendar_green)
                },
                contentDescription = "match date",
                modifier = Modifier.size(24.dp)
            )

            Text(
                text = time,
                style = AppTextStyles.SUBTITLE_16_24_SEMI,
                color = Color(0xFF171717)
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
                color = Color(0xFF171717)
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
            .border(1.dp, Color(0xFFF7F7F7), shape = RoundedCornerShape(14.dp)),
        onClick = onClick,
        contentPadding = PaddingValues(horizontal = 0.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFFFFFFF)
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
                    color = Color(0xFF171717)
                )
                Text(
                    text = contentText,
                    style = AppTextStyles.CAPTION_12_18_SEMI,
                    color = Color(0xFF666666)
                )
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun Banner(
    image: String,
) {
    GlideImage(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp),
        model = image,
        contentDescription = "banner"
    )
}