package com.sungil.onethingmatch.ui.categort

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core.AppTextStyles
import com.example.core.ColorStyle
import com.sungil.onethingmatch.CATEGORY
import com.sungil.onethingmatch.OneThingViewModel
import com.sungil.onethingmatch.R
import com.sungil.onethingmatch.UiError
import com.sungil.onethingmatch.component.CategoryItemView

@Composable
internal fun CategoryView(
    viewModel: OneThingViewModel,
    onBackClick: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val subTextColor = when (uiState.error) {
        UiError.MaxCategorySelected -> ColorStyle.RED_100
        else -> ColorStyle.GRAY_600
    }
    BackHandler(enabled = true) {
        onBackClick()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = ColorStyle.WHITE_100)
            .padding(
                top = 32.dp,
                start = 16.dp,
                end = 17.dp,
            )
    ) {
        Text(
            text = stringResource(R.string.txt_category_title),
            style = AppTextStyles.HEAD_28_40_BOLD,
            color = ColorStyle.GRAY_800
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = stringResource(R.string.txt_category_sub_title),
            style = AppTextStyles.SUBTITLE_16_24_SEMI,
            color = subTextColor,
            modifier = Modifier.padding(top = 4.dp, bottom = 24.dp)
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(bottom = 32.dp)
        ) {
            items(CATEGORY.entries) { category ->
                if (category != CATEGORY.NONE) {
                    CategoryItemView(
                        category = category,
                        isSelect = category == uiState.selectedCategories,
                        onClick = { viewModel.toggleCategory(category) }
                    )
                }
            }
        }
    }
}
