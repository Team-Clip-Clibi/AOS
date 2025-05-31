package com.sungil.onethingmatch.ui.location

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material3.Text
import com.example.core.AppTextStyles
import com.example.core.ButtonCheckBoxLeftL
import com.example.core.ButtonXXLPurple400
import com.example.core.ColorStyle
import com.sungil.onethingmatch.Location
import com.sungil.onethingmatch.OneThingViewModel
import com.sungil.onethingmatch.R
import com.sungil.onethingmatch.UiError

@Composable
internal fun LocationView(
    goNextPage: () -> Unit,
    viewModel: OneThingViewModel,
) {
    val uiState by viewModel.uiState.collectAsState()
    val subTextColor = when (uiState.error) {
        UiError.MaxLocationSelected -> ColorStyle.RED_100
        else -> ColorStyle.GRAY_600
    }
    Scaffold(
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(bottom = 8.dp)

            ) {
                HorizontalDivider(
                    thickness = 1.dp,
                    color = ColorStyle.GRAY_200
                )
                Spacer(modifier = Modifier.height(8.dp))
                ButtonXXLPurple400(
                    onClick = goNextPage,
                    buttonText = stringResource(R.string.btn_next),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 17.dp),
                    isEnable = uiState.location != Location.NONE,
                )
            }
        },
        containerColor = ColorStyle.WHITE_100
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = ColorStyle.WHITE_100)
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    start = 17.dp,
                    end = 16.dp,
                    bottom = paddingValues.calculateBottomPadding()
                )
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = stringResource(R.string.txt_location_title),
                style = AppTextStyles.HEAD_28_40_BOLD,
                color = ColorStyle.GRAY_800,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))
            ButtonCheckBoxLeftL(
                content = Location.HONGDAE_HAPJEONG.displayName,
                isChecked = uiState.location == Location.HONGDAE_HAPJEONG,
                onCheckChange = {
                    viewModel.location(Location.HONGDAE_HAPJEONG)
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
            ButtonCheckBoxLeftL(
                content = Location.GANGNAM.displayName,
                isChecked = uiState.location == Location.GANGNAM,
                onCheckChange = {
                    viewModel.location(Location.GANGNAM)
                }
            )
        }
    }

}