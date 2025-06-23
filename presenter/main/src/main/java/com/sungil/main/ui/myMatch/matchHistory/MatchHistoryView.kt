package com.sungil.main.ui.myMatch.matchHistory

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
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
import com.example.core.AppTextStyles


@Composable
internal fun MatchHistoryView(viewModel: MainViewModel) {
    val matchAllData = viewModel.matchAllData.collectAsLazyPagingItems()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = ColorStyle.GRAY_200)
            .padding(top = 18.dp, end = 20.dp, start = 20.dp , bottom = 24.dp)
    ) {
        when (matchAllData.itemCount) {
            0 -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            painter = painterResource(R.drawable.ic_no_match_data),
                            contentDescription = "No Match Data",
                            modifier = Modifier
                                .border(
                                    width = 1.dp,
                                    color = ColorStyle.GRAY_300,
                                    shape = RoundedCornerShape(size = 100.dp)
                                )
                                .width(60.dp)
                                .height(60.dp)
                                .background(
                                    color = ColorStyle.WHITE_100,
                                    shape = RoundedCornerShape(size = 100.dp)
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

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(matchAllData.itemCount) { index ->
                        matchAllData[index]?.let { matchData ->
                            CustomDialogOneButton(
                                time = matchData.formattedTime,
                                meetState = matchData.matchStatus,
                                meetKind = matchData.matchType,
                                title = matchData.myOneThingContent,
                                reviewWrite = matchData.isReviewWritten,
                                buttonText = stringResource(R.string.btn_write_review),
                                onClick = {

                                }
                            )
                        }
                    }
                }
            }
        }
    }
}