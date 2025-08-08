package com.oneThing.first_matrch.ui.language

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
import com.example.core.AppTextStyles
import com.example.core.ButtonLeftLarge
import com.example.core.ColorStyle
import com.oneThing.first_matrch.FirstMatchViewModel
import com.oneThing.first_matrch.LANGUAGE
import com.oneThing.first_matrch.R

@Composable
internal fun LanguageView(
    viewModel: FirstMatchViewModel,
    onBackClick: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    BackHandler(enabled = true) {
        onBackClick()
    }
    Column(
        modifier = Modifier
            .background(color = ColorStyle.WHITE_100)
            .fillMaxSize()
            .padding(
                top = 32.dp,
                start = 17.dp,
                end = 16.dp
            )
    ) {
        Text(
            text = stringResource(R.string.txt_language_title),
            style = AppTextStyles.HEAD_28_40_BOLD,
            color = ColorStyle.GRAY_800,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = stringResource(R.string.txt_language_sub_title),
            style = AppTextStyles.SUBTITLE_16_24_SEMI,
            color = ColorStyle.GRAY_600,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))
        ButtonLeftLarge(
            text = stringResource(R.string.txt_language_item_korean),
            isSelected = uiState.language == LANGUAGE.KOREAN.name,
            onClick = {
                viewModel.language(LANGUAGE.KOREAN.name)
            }
        )
        Spacer(modifier = Modifier.height(10.dp))
        ButtonLeftLarge(
            text = stringResource(R.string.txt_language_item_english),
            isSelected = uiState.language == LANGUAGE.ENGLISH.name,
            onClick = {
                viewModel.language(LANGUAGE.ENGLISH.name)
            }
        )
        Spacer(modifier = Modifier.height(10.dp))
        ButtonLeftLarge(
            text = stringResource(R.string.txT_language_item_both),
            isSelected = uiState.language == LANGUAGE.BOTH.name,
            onClick = {
                viewModel.language(LANGUAGE.BOTH.name)
            }
        )
    }
}