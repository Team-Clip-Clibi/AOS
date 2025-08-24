package com.sungil.main.ui.myMatch.matchHistory

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.core.ColorStyle
import com.example.core.CustomDialogOneButton
import com.sungil.main.MainViewModel
import com.sungil.main.R
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.paging.compose.LazyPagingItems
import com.example.core.AppTextStyles
import com.sungil.domain.model.MatchingData
import com.sungil.main.ERROR_NETWORK_ERROR
import com.sungil.main.ERROR_RE_LOGIN
import com.sungil.main.MATCH_ALL
import com.sungil.main.MATCH_APPLY
import com.sungil.main.MATCH_CANCEL
import com.sungil.main.MATCH_COMPLETE
import com.sungil.main.MATCH_CONFIRM
import com.sungil.main.MATCH_DATA_EMPTY
import com.sungil.main.component.SmallButton


@Composable
internal fun MatchHistoryView(
    viewModel: MainViewModel,
    matchDetail: () -> Unit,
    login: () -> Unit,
    snackBarHostState: SnackbarHostState,
) {
    val selectButton by viewModel.matchButton.collectAsState()
    val state by viewModel.userState.collectAsState()

    val matchItems = when (selectButton) {
        MATCH_ALL -> viewModel.matchAllData.collectAsLazyPagingItems()
        MATCH_APPLY -> viewModel.matchApplied.collectAsLazyPagingItems()
        MATCH_CONFIRM -> viewModel.matchConfirmed.collectAsLazyPagingItems()
        MATCH_COMPLETE -> viewModel.matchComplete.collectAsLazyPagingItems()
        MATCH_CANCEL -> viewModel.matchCancelled.collectAsLazyPagingItems()
        else -> viewModel.matchAllData.collectAsLazyPagingItems()
    }

    val buttonLabels = listOf(
        stringResource(R.string.btn_match_all),
        stringResource(R.string.btn_apply),
        stringResource(R.string.btn_confirm),
        stringResource(R.string.btn_complete),
        stringResource(R.string.btn_cancel)
    )

    val context = LocalContext.current
    LaunchedEffect(state.matchDetail) {
        when (val result = state.matchDetail) {
            is MainViewModel.UiState.Error -> {
                when (result.message) {
                    ERROR_RE_LOGIN -> {
                        snackBarHostState.showSnackbar(
                            message = context.getString(R.string.msg_re_login),
                            duration = SnackbarDuration.Short
                        )
                        login()
                    }

                    ERROR_NETWORK_ERROR -> {
                        snackBarHostState.showSnackbar(
                            message = context.getString(R.string.msg_network_error),
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            }

            is MainViewModel.UiState.Success -> {
                matchDetail()
            }

            else -> Unit
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = ColorStyle.GRAY_200)
            .padding(top = 18.dp, end = 20.dp, start = 20.dp, bottom = 24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            buttonLabels.forEachIndexed { index, label ->
                SmallButton(
                    text = label,
                    isClick = selectButton == index,
                    onClick = { viewModel.setMatchButton(index) }
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (matchItems.itemCount == MATCH_DATA_EMPTY) {
            NotMatchView()
        } else {
            MatchView(matchAllData = matchItems, viewModel = viewModel)
        }
    }
}

@Composable
fun MatchView(
    matchAllData: LazyPagingItems<MatchingData>,
    viewModel: MainViewModel
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        repeat(matchAllData.itemCount) { index ->
            matchAllData[index]?.let { matchData ->
                CustomDialogOneButton(
                    time = matchData.formattedTime,
                    meetState = matchData.matchStatus,
                    meetKind = matchData.matchType,
                    title = matchData.myOneThingContent,
                    reviewWrite = matchData.isReviewWritten,
                    buttonText = stringResource(R.string.btn_write_review),
                    onClick = {
                        viewModel.getParticipants(
                            matchId = matchData.id,
                            matchType = matchData.matchingType
                        )
                    },
                    onClickDetail = {
                        viewModel.matchDetail(
                            matchId = matchData.id,
                            matchType = matchData.matchingType
                        )
                    },
                    buttonTextColor = if(matchData.isReviewWritten) ColorStyle.GRAY_800 else ColorStyle.WHITE_100
                )
            }
        }
    }
}


@Composable
fun NotMatchView() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 390.dp)
            .background(color = ColorStyle.GRAY_200)
            .padding(top = 18.dp, end = 20.dp, start = 20.dp, bottom = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_no_match_data),
                contentDescription = "No Match Data",
                modifier = Modifier
                    .size(60.dp)
                    .background(
                        color = ColorStyle.WHITE_100,
                        shape = RoundedCornerShape(100.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = ColorStyle.GRAY_300,
                        shape = RoundedCornerShape(100.dp)
                    )
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
}
