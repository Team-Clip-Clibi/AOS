package com.oneThing.first_matrch.ui.diet

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
import com.example.core.TextFieldComponent
import com.oneThing.first_matrch.DIET
import com.oneThing.first_matrch.FirstMatchViewModel
import com.oneThing.first_matrch.R


@Composable
internal fun DietView(
    viewModel: FirstMatchViewModel,
) {
    val uiState by viewModel.uiState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = ColorStyle.WHITE_100)
            .padding(
                top = 32.dp,
                start = 17.dp,
                end = 16.dp
            )
    ) {
        Text(
            text = stringResource(R.string.txt_diet_title),
            style = AppTextStyles.HEAD_28_40_BOLD,
            color = ColorStyle.GRAY_800,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = stringResource(R.string.txt_diet_sub_title),
            style = AppTextStyles.SUBTITLE_16_24_SEMI,
            color = ColorStyle.GRAY_600,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))

        ButtonLeftLarge(
            text = stringResource(R.string.txt_diet_item_vg),
            isSelected = uiState.diet == DIET.VG.displayName,
            onClick = {
                viewModel.diet(DIET.VG.displayName)
            }
        )
        Spacer(modifier = Modifier.height(10.dp))
        ButtonLeftLarge(
            text = stringResource(R.string.txt_diet_item_vt),
            isSelected = uiState.diet == DIET.VT.displayName,
            onClick = {
                viewModel.diet(DIET.VT.displayName)
            }
        )
        Spacer(modifier = Modifier.height(10.dp))
        ButtonLeftLarge(
            text = stringResource(R.string.txt_diet_item_gf),
            isSelected = uiState.diet == DIET.GF.displayName,
            onClick = {
                viewModel.diet(DIET.GF.displayName)
            }
        )
        Spacer(modifier = Modifier.height(10.dp))
        ButtonLeftLarge(
            text = stringResource(R.string.txt_diet_item_all),
            isSelected = uiState.diet == DIET.ALL.displayName,
            onClick = {
                viewModel.diet(DIET.ALL.displayName)
            }
        )
        Spacer(modifier = Modifier.height(10.dp))
        ButtonLeftLarge(
            text = stringResource(R.string.txt_diet_item_etc),
            isSelected = uiState.diet == DIET.ETC.displayName,
            onClick = {
                viewModel.diet(DIET.ETC.displayName)
            }
        )
        if (uiState.diet == DIET.ETC.displayName) {
            TextFieldComponent(
                value = uiState.dietContent,
                onValueChange = viewModel::dietContent,
                maxLength = 1,
                maxLine = 200,
                hint = stringResource(R.string.txt_diet_item_hint)
            )
        }
    }
}