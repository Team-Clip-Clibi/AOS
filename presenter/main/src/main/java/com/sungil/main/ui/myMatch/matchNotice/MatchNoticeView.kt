package com.sungil.main.ui.myMatch.matchNotice

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.core.AppTextStyles
import com.example.core.ColorStyle
import com.example.core.NoticePage
import com.example.core.SimpleBottomSheet
import com.sungil.domain.model.Job
import com.sungil.domain.model.MatchNotice
import com.sungil.main.LateType
import com.sungil.main.MainViewModel
import com.sungil.main.MatchStatus
import com.sungil.main.MatchType
import com.sungil.main.R

@Composable
internal fun MatchNoticeView(viewModel: MainViewModel) {
    val notice = viewModel.notice.collectAsLazyPagingItems()

    if (notice.itemCount == 0) {
        EmptyNotice()
    } else {
        NoticeView(notice, viewModel = viewModel)
    }
}

@Composable
private fun EmptyNotice() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = ColorStyle.GRAY_200)
            .padding(top = 18.dp, end = 20.dp, start = 20.dp, bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Icon(
            painter = painterResource(R.drawable.ic_no_match_data),
            contentDescription = "No Match Data",
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = ColorStyle.GRAY_300,
                    shape = RoundedCornerShape(size = 100.dp)
                )
                .size(60.dp)
                .background(color = ColorStyle.WHITE_100, shape = RoundedCornerShape(100.dp))
                .padding(12.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = stringResource(R.string.txt_no_match_data),
            style = AppTextStyles.BODY_14_20_MEDIUM,
            color = ColorStyle.GRAY_600
        )
    }
}

@Composable
private fun NoticeView(
    notice: LazyPagingItems<MatchNotice>,
    viewModel: MainViewModel,
) {
    val item = listOf(
        stringResource(R.string.match_notice_late_10),
        stringResource(R.string.match_notice_late_20),
        stringResource(R.string.match_notice_late_30),
    )
    var showBottomSheet by remember { mutableStateOf(false) }
    var noticeId by remember { mutableStateOf(0) }
    var matchType by remember { mutableStateOf(MatchType.RANDOM) }
    val state by viewModel.userState.collectAsState()
    val matchLateState = state.matchLate
    val (lateMatchId, lateTime) = when (matchLateState) {
        is MainViewModel.UiState.Success -> {
            val late = (matchLateState).data
            late.matchId to late.time
        }

        else -> -1 to 0
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = ColorStyle.GRAY_200)
            .padding(top = 18.dp, end = 20.dp, start = 20.dp, bottom = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        repeat(notice.itemCount) { index ->
            notice[index]?.let { data ->
                NoticePage(
                    date = data.simpleTime,
                    meetState = MatchStatus.fromRoute(data.matchStatus).label,
                    meetKind = MatchType.fromRoute(data.matchType).matchType,
                    title = data.category,
                    detail = data.diet,
                    restaurant = data.restaurantName,
                    location = data.restaurantAddress,
                    people = stringResource(
                        R.string.match_notice_people,
                        data.jonInfos.toViewString(LocalContext.current)
                    ),
                    job = data.jonInfos.toViewString(LocalContext.current),
                    cuisine = stringResource(R.string.match_notice_cuisine, data.menuCategory),
                    cuisineHighLight = data.menuCategory,
                    dateDetail = "${data.detailTime}(${data.week})",
                    pay = stringResource(R.string.match_notice_pay_content),
                    onClick = {
                        noticeId = data.matchId
                        matchType = MatchType.fromRoute(data.matchType)
                        showBottomSheet = true
                    },
                    buttonShow = MatchStatus.fromRoute(data.matchStatus) == MatchStatus.MATCH_CONFIRMED,
                    buttonText = stringResource(R.string.btn_late),
                    latButtonShow = lateMatchId == data.matchId && MatchStatus.fromRoute(
                        data.matchStatus
                    ) == MatchStatus.MATCH_CONFIRMED,
                    lateButtonText = stringResource(R.string.btn_late),
                    lateButtonTime = stringResource(R.string.match_notice_late_item, lateTime),
                    dietTitle = stringResource(R.string.match_notice_cuisine_info),
                    dietImage = R.drawable.ic_diet_info,
                    dietContentImage = R.drawable.img_diet_content
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
        if (showBottomSheet) {
            SimpleBottomSheet(
                item = item,
                buttonText = stringResource(R.string.match_notice_late_okay),
                click = { text, id ->
                    viewModel.sendLate(
                        matchType = matchType.route,
                        lateTime = LateType.extractMinutes(text) ?: 10,
                        matchId = id
                    )
                },
                content = stringResource(R.string.match_late_content),
                title = stringResource(R.string.match_late_title),
                noticeId = noticeId,
                onDismiss = {
                    showBottomSheet = false
                }
            )
        }
    }
}

private fun List<Job>.toViewString(context: Context): String {
    return this.joinToString(", ") { job ->
        context.getString(R.string.match_notice_job, job.jobName, job.count)
    }
}