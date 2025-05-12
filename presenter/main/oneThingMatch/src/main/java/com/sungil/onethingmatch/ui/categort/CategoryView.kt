package com.sungil.onethingmatch.ui.categort

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core.ButtonXXLPurple400
import com.example.core.ColorStyle
import com.sungil.onethingmatch.CATEGORY
import com.sungil.onethingmatch.OneThingViewModel
import com.sungil.onethingmatch.R
import com.sungil.onethingmatch.component.CategoryItemView

@Composable
internal fun CategoryView(
    goNextPage: () -> Unit,
    viewModel: OneThingViewModel,
) {
    val uiState by viewModel.uiState.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorStyle.WHITE_100)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 8.dp)
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(start = 17.dp, end = 16.dp, top = 32.dp, bottom = 32.dp)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(CATEGORY.entries){ category ->
                    CategoryItemView(
                        category = category,
                        isSelect = category in uiState.selectedCategories,
                        onClick = {viewModel.toggleCategory(category)}
                    )
                }
            }
        }
        ButtonXXLPurple400(
            onClick = goNextPage,
            buttonText = stringResource(R.string.btn_next),
            modifier = Modifier.align(Alignment.BottomCenter)
                .fillMaxWidth(),
            isEnable = uiState.selectedCategories.isNotEmpty()
        )
    }
}