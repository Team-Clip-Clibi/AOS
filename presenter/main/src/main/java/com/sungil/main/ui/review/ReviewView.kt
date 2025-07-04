package com.sungil.main.ui.review

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core.ColorStyle
import com.example.core.TopAppBarWithCloseButton
import com.sungil.main.MainViewModel
import com.sungil.main.R
import com.sungil.main.ReviewIcon
import com.sungil.main.component.ReviewImageView
import androidx.compose.material3.Text
import com.example.core.AppTextStyles
import com.sungil.main.AllAttend
import com.sungil.main.REVIEW_DISAPPOINTED_BTN
import com.sungil.main.REVIEW_SHAME_BTN
import com.sungil.main.ReviewBadItem
import com.sungil.main.ReviewGoodItem
import com.sungil.main.component.ReviewItemContent
import com.sungil.main.component.ReviewTextField
import com.example.core.ButtonCheckBoxLeftL
import com.example.core.ButtonXXLPurple400
import com.sungil.domain.model.Participants
import com.sungil.main.REVIEW_BEST_BTN
import com.sungil.main.REVIEW_GOOD_BTN
import com.sungil.main.REVIEW_NORMAL_BTN
import com.sungil.main.REVIEW_SELECT_NOTHING

@Composable
internal fun ReviewView(viewModel: MainViewModel, onClose: () -> Unit) {
    val uiState by viewModel.userState.collectAsState()
    val review = uiState.reviewButton
    val badItem = uiState.badReviewItem
    val goodItem = uiState.goodReviewItem
    val detail = uiState.reviewDetail
    val allAttend = uiState.allAttend
    val person = (uiState.participants as MainViewModel.UiState.Success).data
    val selectPerson = uiState.unAttendMember
    Scaffold(
        topBar = {
            TopAppBarWithCloseButton(
                title = stringResource(R.string.review_app_bar),
                onBackClick = { onClose() },
                isNavigationShow = false
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = ColorStyle.WHITE_100)
                .padding(
                    top = paddingValues.calculateTopPadding() + 16.dp,
                    start = 17.dp,
                    end = 16.dp,
                    bottom = paddingValues.calculateBottomPadding()
                )
                .verticalScroll(rememberScrollState())
        ) {
            TopView(viewModel = viewModel, review = review)
            Spacer(modifier = Modifier.height(24.dp))
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = ColorStyle.GRAY_200
            )
            Spacer(modifier = Modifier.height(24.dp))
            when (review) {
                REVIEW_DISAPPOINTED_BTN, REVIEW_SHAME_BTN -> {
                    BadReviewView(
                        selectReviewButton = review,
                        selectItem = badItem,
                        viewModel = viewModel
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    GoodReview(
                        selectReviewButton = review,
                        selectItem = goodItem,
                        viewModel = viewModel
                    )
                }

                REVIEW_GOOD_BTN, REVIEW_NORMAL_BTN, REVIEW_BEST_BTN -> {
                    GoodReview(
                        selectReviewButton = review,
                        selectItem = goodItem,
                        viewModel = viewModel
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    BadReviewView(
                        selectReviewButton = review,
                        selectItem = badItem,
                        viewModel = viewModel
                    )
                }
            }
            if (review != REVIEW_NORMAL_BTN) {
                Spacer(modifier = Modifier.height(20.dp))
                WriteReview(
                    input = detail,
                    viewModel = viewModel
                )
                Spacer(modifier = Modifier.height(32.dp))
                AllMemberAttendView(viewModel = viewModel, selectItem = allAttend)
                when (allAttend) {
                    true -> {
                        Spacer(modifier = Modifier.height(22.dp))
                    }

                    false -> {
                        Spacer(modifier = Modifier.height(24.dp))
                        HorizontalDivider(
                            modifier = Modifier.fillMaxWidth(),
                            thickness = 1.dp,
                            color = ColorStyle.GRAY_200
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        UnAttendMemberView(
                            person = person.person,
                            selectItem = selectPerson,
                            viewModel = viewModel
                        )
                        Spacer(modifier = Modifier.height(22.dp))
                    }
                }
            }
        }
        ButtonXXLPurple400(
            buttonText = stringResource(R.string.btn_finish),
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                viewModel.sendReview(
                    matchId = person.matchId,
                    matchType = person.matchType
                )
            },
            isEnable = review != REVIEW_SELECT_NOTHING
        )
    }
}

@Composable
private fun TopView(viewModel: MainViewModel, review: Int) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(R.string.review_title),
            style = AppTextStyles.HEAD_24_34_BOLD,
            color = ColorStyle.GRAY_800
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = stringResource(R.string.review_sub_title),
            style = AppTextStyles.SUBTITLE_16_24_SEMI,
            color = ColorStyle.GRAY_700
        )
        Spacer(modifier = Modifier.height(32.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally)
        ) {
            ReviewIcon.entries.forEach { data ->
                ReviewImageView(
                    content = stringResource(data.content),
                    image = data.image,
                    isSelect = review == data.buttonInt,
                    isClick = {
                        viewModel.setReviewItem(data.buttonInt)
                    }
                )
            }
        }
    }
}

@Composable
private fun BadReviewView(
    viewModel: MainViewModel,
    selectItem: ArrayList<String>,
    selectReviewButton: Int,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = if (selectReviewButton in REVIEW_DISAPPOINTED_BTN..REVIEW_SHAME_BTN) stringResource(
                R.string.review_bad_title
            ) else stringResource(R.string.review_bad_good_case_title),
            style = AppTextStyles.SUBTITLE_18_26_SEMI,
            color = ColorStyle.GRAY_800
        )
        Spacer(modifier = Modifier.height(16.dp))
        ReviewBadItem.entries.forEach { data ->
            val label = stringResource(data.content)
            ReviewItemContent(
                content = stringResource(data.content),
                isSelect = selectItem.contains(label),
                isClick = {
                    viewModel.setBadItem(label)
                }
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
private fun GoodReview(
    viewModel: MainViewModel,
    selectItem: ArrayList<String>,
    selectReviewButton: Int,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = if (selectReviewButton in REVIEW_DISAPPOINTED_BTN..REVIEW_SHAME_BTN) stringResource(
                R.string.review_good_bad_case_title
            ) else stringResource(R.string.review_good_title),
            style = AppTextStyles.SUBTITLE_18_26_SEMI,
            color = ColorStyle.GRAY_800
        )
        Spacer(modifier = Modifier.height(16.dp))
        ReviewGoodItem.entries.forEach { data ->
            val label = stringResource(data.content)
            ReviewItemContent(
                content = label,
                isSelect = selectItem.contains(label),
                isClick = {
                    viewModel.setGoodItem(label)
                }
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
private fun WriteReview(viewModel: MainViewModel, input: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(R.string.review_detail_title),
            style = AppTextStyles.SUBTITLE_18_26_SEMI,
            color = ColorStyle.GRAY_800
        )
        Spacer(modifier = Modifier.height(16.dp))
        ReviewTextField(
            value = input,
            onValueChange = { newText ->
                viewModel.reviewDetail(newText)
            }
        )
    }
}

@Composable
private fun AllMemberAttendView(viewModel: MainViewModel, selectItem: Boolean) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(R.string.review_not_attend_title),
            style = AppTextStyles.SUBTITLE_18_26_SEMI,
            color = ColorStyle.GRAY_800
        )
        Spacer(modifier = Modifier.height(16.dp))
        AllAttend.entries.forEach { data ->
            ButtonCheckBoxLeftL(
                content = stringResource(data.content),
                isChecked = selectItem == data.value,
                onCheckChange = { check ->
                    viewModel.setAllAttend(check)
                },
                checkImageShow = false
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
private fun UnAttendMemberView(
    person: List<Participants>,
    viewModel: MainViewModel,
    selectItem: ArrayList<String>,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.review_not_attend_title),
            style = AppTextStyles.SUBTITLE_16_24_SEMI,
            color = ColorStyle.GRAY_700
        )
        Spacer(modifier = Modifier.height(16.dp))
        person.forEach { data ->
            ReviewItemContent(
                content = data.nickName,
                isClick = {
                    viewModel.setUnAttendMember(data.nickName)
                },
                isSelect = selectItem.contains(data.nickName)
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}