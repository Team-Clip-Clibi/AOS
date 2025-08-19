package com.oneThing.first_matrch.ui.diet

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
import com.example.core.ButtonL
import com.example.core.ColorStyle
import com.example.core.TextFieldComponent
import com.oneThing.first_matrch.DIET
import com.oneThing.first_matrch.FirstMatchViewModel
import com.oneThing.first_matrch.R


@Composable
internal fun DietView(
    viewModel: FirstMatchViewModel,
    onBackClick: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    BackHandler(enabled = true) {
        onBackClick()
    }
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
        ButtonL(
            text = stringResource(R.string.txt_diet_item_all),
            onClick = {
                viewModel.diet(DIET.ALL.displayName)
            },
            isSelected = uiState.diet == DIET.ALL.displayName,
            borderUse = uiState.diet == DIET.ALL.displayName,
            borderColor = ColorStyle.PURPLE_200,
            buttonColor = if (uiState.diet == DIET.ALL.displayName) ColorStyle.PURPLE_100 else ColorStyle.GRAY_100,
            textCenter = false
        )
        Spacer(modifier = Modifier.height(10.dp))
        ButtonL(
            text = stringResource(R.string.txt_diet_item_vg),
            onClick = {
                viewModel.diet(DIET.VG.displayName)
            },
            isSelected = uiState.diet == DIET.VG.displayName,
            borderUse = uiState.diet == DIET.VG.displayName,
            borderColor = ColorStyle.PURPLE_200,
            buttonColor = if (uiState.diet == DIET.VG.displayName) ColorStyle.PURPLE_100 else ColorStyle.GRAY_100,
            textCenter = false
        )
        Spacer(modifier = Modifier.height(10.dp))
        ButtonL(
            text = stringResource(R.string.txt_diet_item_vt),
            onClick = {
                viewModel.diet(DIET.VT.displayName)
            },
            isSelected = uiState.diet == DIET.VT.displayName,
            borderUse = uiState.diet == DIET.VT.displayName,
            borderColor = ColorStyle.PURPLE_200,
            buttonColor = if (uiState.diet == DIET.VT.displayName) ColorStyle.PURPLE_100 else ColorStyle.GRAY_100,
            textCenter = false
        )
        Spacer(modifier = Modifier.height(10.dp))
        ButtonL(
            text = stringResource(R.string.txt_diet_item_gf),
            onClick = {
                viewModel.diet(DIET.GF.displayName)
            },
            isSelected = uiState.diet == DIET.GF.displayName,
            borderUse = uiState.diet == DIET.GF.displayName,
            borderColor = ColorStyle.PURPLE_200,
            buttonColor = if (uiState.diet == DIET.GF.displayName) ColorStyle.PURPLE_100 else ColorStyle.GRAY_100,
            textCenter = false
        )
        Spacer(modifier = Modifier.height(10.dp))
        ButtonL(
            text = stringResource(R.string.txt_diet_item_etc),
            onClick = {
                viewModel.diet(DIET.ETC.displayName)
            },
            isSelected = uiState.diet == DIET.ETC.displayName,
            borderUse = uiState.diet == DIET.ETC.displayName,
            borderColor = ColorStyle.PURPLE_200,
            buttonColor = if (uiState.diet == DIET.ETC.displayName) ColorStyle.PURPLE_100 else ColorStyle.GRAY_100,
            textCenter = false
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