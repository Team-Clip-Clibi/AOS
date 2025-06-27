package com.sungil.main.ui.matchDetail

import androidx.compose.material3.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.CircularProgressIndicator
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
import com.sungil.main.MainViewModel
import com.sungil.main.R
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import com.example.core.AppTextStyles
import com.example.core.ButtonLWhite
import com.example.core.TopAppBarNumber
import com.sungil.domain.model.MatchDate
import com.sungil.editprofile.JOB
import com.sungil.main.CATEGORY
import com.sungil.main.MATCH_KEY_APPLIED
import com.sungil.main.MATCH_KEY_CANCELLED
import com.sungil.main.MatchType

@Composable
internal fun MeetDetailView(viewModel: MainViewModel, onBack: () -> Unit, payDetail: () -> Unit) {
    val snackBarHostState = remember { SnackbarHostState() }
    val viewState by viewModel.userState.collectAsState()
    val matchDetail = viewState.matchDetail

    Scaffold(
        topBar = {
            TopAppBarNumber(
                title = stringResource(R.string.match_detail_top_bar),
                onBackClick = {
                    viewModel.setMatchDetailInit()
                    onBack()
                },
                currentPage = 0,
                totalPage = 0
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState,
                snackbar = { data -> CustomSnackBar(data) },
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
        when (matchDetail) {
            is MainViewModel.UiState.Success -> {
                val detail = matchDetail.data

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = ColorStyle.WHITE_100)
                        .padding(
                            top = paddingValues.calculateTopPadding() + 24.dp,
                            bottom = paddingValues.calculateBottomPadding() + 32.dp
                        )
                        .verticalScroll(rememberScrollState())
                ) {
                    TopView(
                        simpleTime = detail.simpleTime,
                        approveState = detail.matchStatus,
                        matchType = detail.matchType,
                        content = detail.matchContent,
                        payPrice = detail.paymentPrice,
                        matchTime = detail.detailTime
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp),
                        thickness = 8.dp,
                        color = ColorStyle.GRAY_200
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
                    MyMatchInfo(
                        diet = detail.diet,
                        job = detail.job,
                        language = detail.language,
                        loveState = detail.loveState
                    )
                    PayInfo(
                        payPrice = detail.paymentPrice,
                        payDetail = payDetail
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    if (detail.cancelButton) {
                        BottomButtonView(onClick = {
                            // TODO 매칭 취소 개발
                        })
                    }
                }
            }

            is MainViewModel.UiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = ColorStyle.WHITE_100)
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {

                }
            }

            is MainViewModel.UiState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = ColorStyle.WHITE_100)
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.error_match_detail),
                        style = AppTextStyles.BODY_14_20_MEDIUM,
                        color = ColorStyle.RED_100
                    )
                }
            }
        }
    }
}


@Composable
fun BottomButtonView(onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 17.dp, end = 16.dp)
    ) {
        ButtonLWhite(text = stringResource(R.string.match_detail_btn_cancel), onClick = {
            onClick()
        })
    }
}

@Composable
private fun TopView(
    simpleTime: String,
    approveState: String,
    matchType: String,
    content: String,
    payPrice: Int,
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
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
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
        if (MatchType.fromRoute(matchType).route == MatchType.ONE_THING.route) {
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

@Composable
private fun MyMatchInfo(job: String, loveState: String, diet: String, language: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp, start = 17.dp, end = 16.dp, bottom = 24.dp)
    ) {
        Text(
            text = stringResource(R.string.match_detail_my_info),
            style = AppTextStyles.SUBTITLE_18_26_SEMI,
            color = ColorStyle.GRAY_800
        )
        Spacer(modifier = Modifier.height(12.dp))
        MyInfoItemView(
            title = stringResource(R.string.match_detail_job),
            content = JOB.fromName(job).displayName
        )
        MyInfoItemView(
            title = stringResource(R.string.match_detail_love_state),
            content = loveState
        )
        MyInfoItemView(
            title = stringResource(R.string.match_detail_diet),
            content = diet
        )
        MyInfoItemView(
            title = stringResource(R.string.match_detail_language),
            content = language
        )
    }
}

@Composable
private fun MyInfoItemView(
    title: String,
    content: String,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 10.dp)
        ) {
            Text(
                text = title,
                style = AppTextStyles.BODY_14_20_REGULAR,
                color = ColorStyle.GRAY_600,
                modifier = Modifier
                    .width(80.dp)
            )
            Text(
                text = content,
                style = AppTextStyles.BODY_14_20_MEDIUM,
                color = ColorStyle.GRAY_800,
                modifier = Modifier.fillMaxWidth()
            )
        }
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = ColorStyle.GRAY_200
        )
    }
}

@Composable
private fun PayInfo(payPrice: Int, payDetail: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 17.dp, end = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.match_detail_pay_info),
                style = AppTextStyles.SUBTITLE_18_26_SEMI,
                color = ColorStyle.GRAY_800
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.match_detail_pay_detail, payPrice),
                    style = AppTextStyles.CAPTION_12_18_SEMI,
                    color = ColorStyle.GRAY_700
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_right),
                    contentDescription = stringResource(R.string.match_detail_pay_gray, payPrice),
                    modifier = Modifier.clickable { payDetail() },
                    tint = ColorStyle.GRAY_500
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.match_detail_pay_price_title),
                style = AppTextStyles.SUBTITLE_16_24_SEMI,
                color = ColorStyle.RED_100
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.match_detail_pay_price_content, payPrice),
                    style = AppTextStyles.SUBTITLE_16_24_SEMI,
                    color = ColorStyle.RED_100
                )
            }
        }
    }
}