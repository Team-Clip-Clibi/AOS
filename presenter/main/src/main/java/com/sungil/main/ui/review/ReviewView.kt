package com.sungil.main.ui.review

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core.ColorStyle
import com.sungil.main.MainViewModel
import com.sungil.main.R
import com.sungil.main.ReviewIcon
import com.sungil.main.component.ReviewImageView
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import com.example.core.AppTextStyles
import com.sungil.main.AllAttend
import com.sungil.main.REVIEW_DISAPPOINTED_BTN
import com.sungil.main.REVIEW_SHAME_BTN
import com.sungil.main.ReviewBadItem
import com.sungil.main.ReviewGoodItem
import com.sungil.main.component.ReviewItemContent
import com.sungil.main.component.ReviewTextField
import com.example.core.ButtonCheckBoxLeftL
import com.example.core.ButtonXXL
import com.sungil.editprofile.ERROR_NETWORK
import com.sungil.main.ERROR_RE_LOGIN
import com.sungil.main.REVIEW_BEST_BTN
import com.sungil.main.REVIEW_GOOD_BTN
import com.sungil.main.REVIEW_NORMAL_BTN
import com.sungil.main.REVIEW_SELECT_NOTHING

@Composable
internal fun ReviewView(
    viewModel: MainViewModel,
    onClose: () -> Unit,
    paddingValues: PaddingValues,
    participant: List<String>,
    matchId: Int,
    matchType: String,
) {
    val uiState by viewModel.userState.collectAsState()
    val review = uiState.reviewButton
    val badItem = uiState.badReviewItem
    val goodItem = uiState.goodReviewItem
    val detail = uiState.reviewDetail
    val allAttend = uiState.allAttend
    val selectPerson = uiState.unAttendMember
    val writeReview = uiState.writeReview
    val snackBarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    BackHandler(enabled = true) {
        viewModel.initParticipants()
        onClose()
    }
    LaunchedEffect(writeReview) {
        when (writeReview) {
            is MainViewModel.UiState.Error -> {
                when (writeReview.message) {
                    ERROR_NETWORK -> {
                        snackBarHostState.showSnackbar(
                            message = context.getString(R.string.msg_network_error),
                            duration = SnackbarDuration.Short
                        )
                    }

                    ERROR_RE_LOGIN -> {
                        snackBarHostState.showSnackbar(
                            message = context.getString(R.string.msg_re_login),
                            duration = SnackbarDuration.Short
                        )
                        viewModel.initParticipants()
                    }
                }
            }

            is MainViewModel.UiState.Success -> {
                snackBarHostState.showSnackbar(
                    message = context.getString(R.string.msg_thank_you_message),
                    duration = SnackbarDuration.Short
                )
                viewModel.initParticipants()
                onClose()
            }

            else -> Unit
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = ColorStyle.WHITE_100)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                focusManager.clearFocus()
            }
            .padding(
                start = 17.dp,
                end = 16.dp,
                bottom = paddingValues.calculateBottomPadding() + 8.dp
            )
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        TopView(viewModel = viewModel, review = review)
        when (review) {
            REVIEW_DISAPPOINTED_BTN, REVIEW_SHAME_BTN -> {
                Spacer(modifier = Modifier.height(24.dp))
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = ColorStyle.GRAY_200
                )
                Spacer(modifier = Modifier.height(24.dp))
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
                Spacer(modifier = Modifier.height(24.dp))
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = ColorStyle.GRAY_200
                )
                Spacer(modifier = Modifier.height(24.dp))
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
        if (review != REVIEW_SELECT_NOTHING) {
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
                        person = participant,
                        selectItem = selectPerson,
                        viewModel = viewModel
                    )
                    Spacer(modifier = Modifier.height(22.dp))
                }
            }
            ButtonXXL(
                text = stringResource(R.string.btn_finish),
                onClick = {
                    viewModel.sendReview(
                        matchId = matchId,
                        matchType = matchType
                    )
                },
            )
        }
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
        val listState = rememberLazyListState()
        val fling = rememberSnapFlingBehavior(listState)
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            state = listState,
            flingBehavior = fling,
            contentPadding = PaddingValues(horizontal = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(15.dp, Alignment.CenterHorizontally)
        ) {
            items(ReviewIcon.entries.size) { index ->
                val data = ReviewIcon.entries[index]
                ReviewImageView(
                    content = stringResource(data.content),
                    image = data.image,
                    isSelect = review == data.buttonInt,
                    isClick = { viewModel.setReviewItem(data.buttonInt) }
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
        ButtonCheckBoxLeftL(
            content = stringResource(AllAttend.ALL_ATTEND.content),
            isChecked = selectItem == AllAttend.ALL_ATTEND.value,
            onCheckChange = {
                viewModel.setAllAttend(AllAttend.ALL_ATTEND.value)
            },
            checkImageShow = false
        )
        Spacer(modifier = Modifier.height(10.dp))
        ButtonCheckBoxLeftL(
            content = stringResource(AllAttend.NOT_ATTEND.content),
            isChecked = selectItem == AllAttend.NOT_ATTEND.value,
            onCheckChange = {
                viewModel.setAllAttend(AllAttend.NOT_ATTEND.value)
            },
            checkImageShow = false
        )
        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
private fun UnAttendMemberView(
    person: List<String>,
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
                content = data,
                isClick = {
                    viewModel.setUnAttendMember(data)
                },
                isSelect = selectItem.contains(data)
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}