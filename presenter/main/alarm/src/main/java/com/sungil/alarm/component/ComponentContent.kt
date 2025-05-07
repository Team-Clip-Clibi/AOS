package com.sungil.alarm.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.core.AppTextStyles
import com.sungil.alarm.R
import com.sungil.domain.model.Notification
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.shape.RoundedCornerShape

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar(
    title: String,
    onBackClick: () -> Unit,
) {
    Column {
        CenterAlignedTopAppBar(
            modifier = Modifier.fillMaxWidth(),
            title = {
                Text(
                    text = title,
                    style = AppTextStyles.TITLE_20_28_SEMI,
                    color = Color(0xFF000000),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(start = 5.dp, end = 16.dp)
                )
            },
            navigationIcon = {
                Image(
                    painter = painterResource(id = R.drawable.ic_left_out),
                    contentDescription = "뒤로가기",
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .size(24.dp)
                        .clickable { onBackClick() }
                )
            },
            actions = {},
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Color(0xFFFFFFFF)
            )
        )
        HorizontalDivider(
            color = Color(0xFFEFEFEF),
            thickness = 1.dp
        )
    }
}


@Composable
fun CustomNoticeList(
    pagingItems: LazyPagingItems<Notification>,
    modifier : Modifier
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(
            count = pagingItems.itemCount,
            key = { index -> pagingItems[index]?.id ?: "loading-$index" }
        ) { index ->
            pagingItems[index]?.let { notification ->
                CustomNotifyAdapter(data = notification)
            }
        }

        pagingItems.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item { CircularProgressIndicator(modifier = Modifier.fillMaxWidth().padding(16.dp)) }
                }

                loadState.append is LoadState.Loading -> {
                    item { CircularProgressIndicator(modifier = Modifier.fillMaxWidth().padding(16.dp)) }
                }

                loadState.refresh is LoadState.Error -> {
                    val e = loadState.refresh as LoadState.Error
                    item { Text("Error: ${e.error.localizedMessage}", color = Color.Red) }
                }

                loadState.append is LoadState.Error -> {
                    val e = loadState.append as LoadState.Error
                    item { Text("Append Error: ${e.error.localizedMessage}", color = Color.Red) }
                }

                loadState.append.endOfPaginationReached && pagingItems.itemCount > 0 -> {
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            HorizontalDivider(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 12.dp),
                                color = Color(0xFFEFEFEF),
                                thickness = 1.dp
                            )
                            Text(
                                text = stringResource(R.string.txt_item_alarm_last),
                                style = AppTextStyles.BODY_14_20_MEDIUM,
                                color = Color(0xFF666666),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(horizontal = 12.dp)
                            )
                            HorizontalDivider(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(end = 12.dp),
                                color = Color(0xFFEFEFEF),
                                thickness = 1.dp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CustomNotifyAdapter(
    data: Notification,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(horizontal = 24.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = getIconResByType(data.notificationType)),
                        contentDescription = "alarm",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(id = getTextByType(data.notificationType)),
                        style = AppTextStyles.BODY_14_20_MEDIUM,
                        color = Color(0xFF989898)
                    )
                }
                Text(
                    text = data.formattedTime,
                    style = AppTextStyles.BODY_14_20_MEDIUM,
                    color = Color(0xFF989898)
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = data.content,
                style = AppTextStyles.SUBTITLE_16_24_SEMI,
                color = Color(0xFF383838),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        HorizontalDivider(
            thickness = 1.dp,
            color = Color(0xFFEFEFEF),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

fun getIconResByType(type: String): Int {
    return when (type) {
        NotificationType.MEETING.name -> R.drawable.ic_one_thing
        NotificationType.NOTICE.name -> R.drawable.ic_notice
        NotificationType.EVENT.name -> R.drawable.ic_event
        else -> R.drawable.ic_message
    }
}

fun getTextByType(type: String): Int {
    return when (type) {
        NotificationType.MEETING.name -> R.string.txt_oneThing_meeting
        NotificationType.NOTICE.name -> R.string.txt_oneThing_notify
        NotificationType.EVENT.name -> R.string.txt_oneThing_event
        else -> R.string.msg_error
    }
}