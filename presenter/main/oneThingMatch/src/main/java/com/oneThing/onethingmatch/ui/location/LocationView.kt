package com.oneThing.onethingmatch.ui.location

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.oneThing.core.AppTextStyles
import com.oneThing.core.ButtonCheckBoxLeftL
import com.oneThing.core.ColorStyle
import com.oneThing.onethingmatch.Location
import com.oneThing.onethingmatch.OneThingViewModel
import com.oneThing.onethingmatch.R
import androidx.compose.material3.Text
@Composable
internal fun LocationView(
    viewModel: OneThingViewModel,
) {
    val uiState by viewModel.uiState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = ColorStyle.WHITE_100)
            .padding(
                top = 32.dp,
                start = 17.dp,
                end = 16.dp,
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