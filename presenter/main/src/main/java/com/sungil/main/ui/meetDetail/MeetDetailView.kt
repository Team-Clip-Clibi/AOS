package com.sungil.main.ui.meetDetail

import androidx.compose.material3.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core.ColorStyle
import com.example.core.CustomSnackBar
import com.example.core.TopAppBarWithCloseButton
import com.sungil.main.MainViewModel
import com.sungil.main.R
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import com.example.core.AppTextStyles
import com.sungil.domain.model.MatchDate
import com.sungil.main.CATEGORY
import com.sungil.main.MATCH_KEY_APPLIED
import com.sungil.main.MATCH_KEY_CANCELLED
import com.sungil.main.MatchType

@Composable
internal fun MeetDetailView(viewModel: MainViewModel, myMatch: () -> Unit) {
    val snackBarHostState = remember { SnackbarHostState() }
    val viewState by viewModel.userState.collectAsState()
    val detail = (viewState.matchDetail as MainViewModel.UiState.Success).data

    Scaffold(
        topBar = {
            TopAppBarWithCloseButton(
                title = stringResource(R.string.match_detail_top_bar),
                onBackClick = myMatch,
                isNavigationShow = true,
                isActionShow = false
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState,
                snackbar = { data ->
                    CustomSnackBar(data)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 17.dp,
                        end = 16.dp,
                        bottom = WindowInsets.navigationBars
                            .asPaddingValues()
                            .calculateTopPadding()
                    )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = ColorStyle.WHITE_100)
                .padding(
                    top = paddingValues.calculateTopPadding() + 24.dp,
                    bottom = paddingValues.calculateBottomPadding()
                )
                .verticalScroll(rememberScrollState())
        ) {
            TopView(
                simpleTime = detail.simpleTime,
                approveState = detail.matchStatus,
                matchType = detail.matchType,
                content = detail.matchContent,
                payPrice = detail.paymentPrice.toString(),
                matchTime = detail.detailTime
            )
            Spacer(modifier = Modifier.height(24.dp))
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .background(color = ColorStyle.GRAY_200)
            )
            Spacer(modifier = Modifier.height(24.dp))
            MatchInfoView(
                matchType = detail.matchType,
                applyDate = detail.matchTime,
                district = detail.district,
                oneThingCategory = detail.matchCategory,
                budget = detail.matchBudget
            )
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun TopView(
    simpleTime: String,
    approveState: String,
    matchType: String,
    content: String,
    payPrice: String,
    matchTime: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 17.dp, end = 16.dp)
    ) {
        Text(
            text = simpleTime,
            style = AppTextStyles.CAPTION_12_18_SEMI,
            color = ColorStyle.GRAY_800,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row {
            Text(
                text = when (approveState) {
                    MATCH_KEY_APPLIED -> stringResource(R.string.txt_match_applied)
                    MATCH_KEY_CANCELLED -> stringResource(R.string.txt_match_cancelled)
                    else -> stringResource(R.string.txt_match_complete)
                },
                style = AppTextStyles.CAPTION_12_18_SEMI,
                color = when (approveState) {
                    MATCH_KEY_CANCELLED -> ColorStyle.RED_100
                    else -> ColorStyle.PURPLE_400
                }
            )
            Spacer(modifier = Modifier.width(6.dp))
            Icon(
                painter = painterResource(R.drawable.ic_point),
                contentDescription = when (approveState) {
                    MATCH_KEY_APPLIED -> stringResource(R.string.txt_match_applied)
                    MATCH_KEY_CANCELLED -> stringResource(R.string.txt_match_cancelled)
                    else -> stringResource(R.string.txt_match_complete)
                }
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = MatchType.fromRoute(matchType).matchType,
                style = AppTextStyles.CAPTION_12_18_SEMI,
                color = ColorStyle.GRAY_800
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = content,
            style = AppTextStyles.SUBTITLE_18_26_SEMI,
            color = ColorStyle.GRAY_800,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = stringResource(R.string.match_detail_pay_gray, payPrice),
            style = AppTextStyles.CAPTION_10_14_MEDIUM,
            color = ColorStyle.GRAY_600
        )
        Spacer(modifier = Modifier.height(2.dp))
        if (MatchType.fromRoute(matchType).route == MatchType.RANDOM.route) {
            Text(
                text = matchTime,
                style = AppTextStyles.CAPTION_10_14_MEDIUM,
                color = ColorStyle.GRAY_600
            )
        }
    }
}

@Composable
fun MatchInfoView(
    matchType: String,
    applyDate: List<MatchDate>,
    district: String,
    oneThingCategory: String,
    budget: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 17.dp, end = 16.dp)
    ) {
        Text(
            text = stringResource(R.string.match_detail_apply_info),
            style = AppTextStyles.SUBTITLE_18_26_SEMI,
            color = ColorStyle.GRAY_800,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))
        when (MatchType.fromRoute(matchType)) {
            MatchType.RANDOM -> {
                Text(
                    text = stringResource(R.string.match_detail_district),
                    style = AppTextStyles.BODY_14_20_MEDIUM,
                    color = ColorStyle.GRAY_800
                )
                Spacer(modifier = Modifier.height(10.dp))
                MatchItemView(text = district)
            }

            MatchType.ONE_THING -> {
                Row {
                    Text(
                        text = stringResource(R.string.match_detail_time),
                        style = AppTextStyles.BODY_14_20_MEDIUM,
                        color = ColorStyle.GRAY_800,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    VerticalDivider(
                        modifier = Modifier
                            .width(1.dp)
                            .height(8.dp)
                            .background(color = ColorStyle.GRAY_400)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = stringResource(R.string.match_detail_time_content),
                        style = AppTextStyles.CAPTION_10_14_MEDIUM,
                        color = ColorStyle.GRAY_600
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                applyDate.forEachIndexed { index, matchDate ->
                    MatchItemView(text = "${matchDate.date}(${matchDate.week}) 오후 7시")
                    if (index != applyDate.lastIndex) {
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = stringResource(R.string.match_detail_district),
                    style = AppTextStyles.BODY_14_20_MEDIUM,
                    color = ColorStyle.GRAY_800
                )
                Spacer(modifier = Modifier.height(10.dp))
                MatchItemView(text = district)
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = stringResource(R.string.match_detail_category),
                    style = AppTextStyles.BODY_14_20_MEDIUM,
                    color = ColorStyle.GRAY_800
                )
                Spacer(modifier = Modifier.height(10.dp))
                MatchItemView(text = CATEGORY.fromRoute(oneThingCategory).displayName)
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = stringResource(R.string.match_detail_budget),
                    style = AppTextStyles.BODY_14_20_MEDIUM,
                    color = ColorStyle.GRAY_800
                )
                Spacer(modifier = Modifier.height(10.dp))
                MatchItemView(text = budget)
            }
        }
    }
}

@Composable
private fun MatchItemView(text: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = ColorStyle.GRAY_100, shape = RoundedCornerShape(4.dp))
            .padding(start = 10.dp, top = 8.dp, end = 10.dp, bottom = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = text,
            style = AppTextStyles.BODY_14_20_MEDIUM,
            color = ColorStyle.GRAY_800
        )
    }
}