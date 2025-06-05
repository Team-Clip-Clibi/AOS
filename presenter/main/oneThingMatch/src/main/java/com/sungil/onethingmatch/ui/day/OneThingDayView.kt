package com.sungil.onethingmatch.ui.day

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core.AppTextStyles
import com.example.core.ColorStyle
import com.sungil.onethingmatch.OneThingViewModel
import com.sungil.onethingmatch.R
import com.sungil.onethingmatch.UiError
import com.sungil.onethingmatch.component.OneThingDayList
import com.sungil.onethingmatch.component.SelectDateList

@Composable
internal fun OneThingDayView(
    viewModel: OneThingViewModel,
) {
    val uiState by viewModel.uiState.collectAsState()
    val subTextColor = when (uiState.error) {
        UiError.MaxDateSelected -> ColorStyle.RED_100
        else -> ColorStyle.GRAY_600
    }
    val subText = when (uiState.error) {
        UiError.MaxDateSelected -> stringResource(R.string.txt_date_sub_title_error)
        else -> stringResource(R.string.txt_date_sub_title)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 32.dp,
                start = 17.dp
            )
    ) {
        Text(
            text = stringResource(R.string.txt_date_title),
            style = AppTextStyles.HEAD_28_40_BOLD,
            color = ColorStyle.GRAY_800,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = subText,
            style = AppTextStyles.SUBTITLE_16_24_SEMI,
            color = subTextColor,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))
        OneThingDayList(
            selectData = uiState.selectDate,
            item = uiState.dateData,
            onItemSelect = { date ->
                viewModel.selectDate(date)
            }
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = stringResource(R.string.txt_date_select),
            style = AppTextStyles.BODY_14_20_MEDIUM,
            color = ColorStyle.GRAY_800,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        SelectDateList(
            selectItem = uiState.selectDate,
            onRemoveClick = { date ->
                viewModel.removeDate(date)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp)
        )
    }
}