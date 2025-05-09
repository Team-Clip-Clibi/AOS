package com.sungil.onethingmatch.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core.Purple400Progress
import com.example.core.TopAppBarNumber
import com.sungil.onethingmatch.OneThingViewModel
import com.sungil.onethingmatch.R
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


@Composable
internal fun InputMatchDataView(
    nextPage: () -> Unit,
    home: () -> Unit,
    viewModel: OneThingViewModel,
) {
    val pageCount = 5
    val pagerState = rememberPagerState(pageCount = { pageCount })
    val coroutineScope = rememberCoroutineScope()
    BackHandler(enabled = pagerState.currentPage > 0) {
        coroutineScope.launch {
            pagerState.animateScrollToPage(pagerState.currentPage - 1)
        }
    }
    Scaffold(
        topBar = {
            Column {
                TopAppBarNumber(
                    title = stringResource(R.string.top_app_bar),
                    currentPage = 0,
                    totalPage = 0,
                    onBackClick = {
                        coroutineScope.launch {
                            when (pagerState.currentPage) {
                                0 -> home()
                                else -> pagerState.animateScrollToPage(pagerState.currentPage - 1)
                            }
                        }
                    }
                )
                val progress = (pagerState.currentPage * 0.1f) + 0.5f
                Purple400Progress(progress = progress.coerceIn(0f, 1f))
            }
        },
    ) { paddingValues ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = 8.dp
                )
                .navigationBarsPadding(),
            userScrollEnabled = false
        ) { page ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                when (page) {

                }
            }
        }
    }
}