package com.oneThing.first_matrch.ui.diet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core.AppTextStyles
import com.example.core.ButtonLeftLarge
import com.example.core.ButtonXXLPurple400
import com.example.core.ColorStyle
import com.example.core.TextFieldComponent
import com.oneThing.first_matrch.DIET
import com.oneThing.first_matrch.FirstMatchViewModel
import com.oneThing.first_matrch.R


@Composable
internal fun DietView(
    viewModel: FirstMatchViewModel,
    goNextPage: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
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
                    isEnable = uiState.diet != DIET.NONE.displayName
                )
            }
        },
        contentColor = ColorStyle.WHITE_100
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding() + 32.dp,
                    bottom = paddingValues.calculateBottomPadding() + 10.dp,
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

            ButtonLeftLarge(
                text = stringResource(R.string.txt_diet_item_vt),
                isSelected = uiState.diet == DIET.VT.displayName,
                onClick = {
                    viewModel.diet(DIET.VT.displayName)
                }
            )

            ButtonLeftLarge(
                text = stringResource(R.string.txt_diet_item_gf),
                isSelected = uiState.diet == DIET.GF.displayName,
                onClick = {
                    viewModel.diet(DIET.GF.displayName)
                }
            )

            ButtonLeftLarge(
                text = stringResource(R.string.txt_diet_item_all),
                isSelected = uiState.diet == DIET.ALL.displayName,
                onClick = {
                    viewModel.diet(DIET.ALL.displayName)
                }
            )

            ButtonLeftLarge(
                text = stringResource(R.string.txt_diet_item_etc),
                isSelected = uiState.diet == DIET.ETC.displayName,
                onClick = {
                    viewModel.diet(DIET.ETC.displayName)
                }
            )
            if (uiState.diet == DIET.ETC.displayName) {
                TextFieldComponent(
                    value = if (uiState.diet == DIET.NONE.displayName) {
                        ""
                    } else {
                        uiState.diet
                    },
                    onValueChange = viewModel::diet,
                    maxLength = 1,
                    maxLine = 200,
                    hint = stringResource(R.string.txt_diet_item_hint)
                )
            }
        }
    }
}