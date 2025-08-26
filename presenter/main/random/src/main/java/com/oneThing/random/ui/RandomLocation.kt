package com.oneThing.random.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
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
import com.clip.core.AppTextStyles
import com.clip.core.ButtonCheckBoxLeftL
import com.clip.core.ColorStyle
import com.oneThing.random.R
import com.oneThing.random.RandomMatchViewModel
import com.oneThing.random.component.Location

@Composable
internal fun RandomLocation(
    viewModel: RandomMatchViewModel,
    onBackClick: () -> Unit,
) {
    BackHandler(enabled = true) {
        onBackClick()
    }
    val uiState by viewModel.uiState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = ColorStyle.WHITE_100)
            .padding(top = 32.dp, start = 17.dp, end = 16.dp)
    ) {
        Text(
            text = stringResource(R.string.random_location_title),
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