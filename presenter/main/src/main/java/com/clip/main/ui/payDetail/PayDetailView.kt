package com.clip.main.ui.payDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.clip.core.ColorStyle
import com.clip.core.TopAppBarWithCloseButton
import com.clip.main.MainViewModel
import com.clip.main.R
import androidx.compose.material3.Text
import com.clip.core.AppTextStyles
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.clip.main.MATCH_KEY_APPLIED
import com.clip.main.MATCH_KEY_CANCELLED
import com.clip.main.MatchType

@Composable
internal fun PayDetailView(viewModel: MainViewModel, onBack: () -> Unit) {
    val viewState by viewModel.userState.collectAsState()
    val detail = (viewState.matchDetail as MainViewModel.UiState.Success).data
    Scaffold(
        topBar = {
            TopAppBarWithCloseButton(
                title = stringResource(R.string.pay_detail_info),
                onBackClick = onBack,
                isNavigationShow = true,
                isActionShow = false
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
            PayDetailTopView(
                approveState = detail.matchStatus,
                matchSubject = detail.matchContent,
                matchType = detail.matchType,
                payInfo = detail.paymentPrice,
                simpleTime = detail.simpleTime
            )
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth(),
                color = ColorStyle.GRAY_200,
                thickness = 8.dp
            )
            PayDetailView(
                matchPrice = detail.matchPrice,
                discountPrice = detail.discount,
                payPrice = detail.paymentPrice
            )
        }
    }
}

@Composable
private fun PayDetailTopView(
    simpleTime: String,
    approveState: String,
    matchType: String,
    matchSubject: String,
    payInfo: Int,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 17.dp, end = 16.dp, bottom = 24.dp)
    ) {
        Text(
            text = simpleTime,
            style = AppTextStyles.CAPTION_12_18_SEMI,
            color = ColorStyle.GRAY_800
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
            text = matchSubject,
            style = AppTextStyles.SUBTITLE_18_26_SEMI,
            color = ColorStyle.GRAY_800
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = stringResource(R.string.pay_detail_won_gray, payInfo),
            style = AppTextStyles.CAPTION_10_14_MEDIUM,
            color = ColorStyle.GRAY_600
        )
    }
}

@Composable
private fun PayDetailView(matchPrice: Int, discountPrice: Int, payPrice: Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp, start = 17.dp, end = 16.dp)
    ) {
        Text(
            text = stringResource(R.string.pay_detail_info),
            style = AppTextStyles.SUBTITLE_18_26_SEMI,
            color = ColorStyle.GRAY_800
        )
        Spacer(modifier = Modifier.height(12.dp))
        PayDetailItemView(
            title = stringResource(R.string.pay_detail_match_price),
            content = stringResource(R.string.pay_detail_won, matchPrice)
        )
        PayDetailItemView(
            title = stringResource(R.string.pay_detail_match_discount_title),
            content = stringResource(R.string.pay_detail_match_discount_content, discountPrice)
        )
        PayDetailItemView(
            title = stringResource(R.string.pay_detail_match_pay),
            content = stringResource(R.string.pay_detail_won, payPrice),
            textColor = ColorStyle.RED_100
        )
    }
}

@Composable
private fun PayDetailItemView(
    title: String,
    content: String,
    textColor: Color = ColorStyle.GRAY_800,
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
                color = textColor,
                modifier = Modifier
                    .width(80.dp)
            )
            Text(
                text = content,
                style = AppTextStyles.BODY_14_20_MEDIUM,
                color = textColor,
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