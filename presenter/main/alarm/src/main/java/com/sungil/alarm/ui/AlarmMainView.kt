package com.sungil.alarm.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.wear.compose.material3.Text
import com.example.core.AppTextStyles
import com.sungil.alarm.AlarmViewModel
import com.sungil.alarm.R
import com.sungil.alarm.component.CustomNoticeList

@Composable
internal fun AlarmMainView(
    viewModel: AlarmViewModel,
    paddingValue: PaddingValues,
    snackBarHost: SnackbarHostState,
) {
    val unReadNotify = viewModel.unReadNotify.collectAsLazyPagingItems()
    val readNotify = viewModel.readNotification.collectAsLazyPagingItems()
    val unReadState = unReadNotify.loadState.refresh
    val readState= readNotify.loadState.refresh

    when(unReadState){
        is LoadState.Error ->{

        }
        LoadState.Loading -> {

        }
        is LoadState.NotLoading -> {

        }
    }

    when(readState){
        is LoadState.Error -> {

        }
        LoadState.Loading -> {

        }
        is LoadState.NotLoading ->{

        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = paddingValue.calculateTopPadding() + 24.dp,
                start = 24.dp, end = 24.dp
            )
            .navigationBarsPadding()
            .background(color = Color(0xFFEFEFEF))
    ) {
        Text(
            text = stringResource(R.string.txt_title_new_alarm),
            style = AppTextStyles.TITLE_20_28_SEMI,
            color = Color(0xFF171717)
        )
        CustomNoticeList(
            pagingItems = unReadNotify,
            height = 380.dp
        )

        Spacer(Modifier.height(24.dp))

        Text(
            text = stringResource(R.string.txt_title_read_alarm),
            style = AppTextStyles.TITLE_20_28_SEMI,
            color = Color(0xFF171717)
        )

        CustomNoticeList(
            pagingItems = readNotify,
            height = 1f.dp
        )
    }
}