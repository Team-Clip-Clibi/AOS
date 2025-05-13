package com.sungil.onethingmatch.ui.budget

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
import com.example.core.ButtonCheckBoxLeftL
import com.example.core.ButtonXXLPurple400
import com.example.core.ColorStyle
import com.sungil.onethingmatch.Budget
import com.sungil.onethingmatch.OneThingViewModel
import com.sungil.onethingmatch.R

@Composable
internal fun BudgetView(
    viewModel: OneThingViewModel,
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
                    isEnable = uiState.budget != Budget.RANGE_NONE
                )
            }
        },
        containerColor = ColorStyle.WHITE_100
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding() + 32.dp,
                    start = 17.dp,
                    end = 16.dp
                )
        ) {
            Text(
                text = stringResource(R.string.txt_budget_title),
                style = AppTextStyles.HEAD_28_40_BOLD,
                color = ColorStyle.GRAY_800,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = stringResource(R.string.txt_budget_sub_title),
                style = AppTextStyles.SUBTITLE_16_24_SEMI,
                color = ColorStyle.GRAY_600,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            ButtonCheckBoxLeftL(
                content = Budget.RANGE_10_30.displayName,
                isChecked = uiState.budget == Budget.RANGE_10_30,
                onCheckChange = { viewModel.onBudgetChanged(Budget.RANGE_10_30) }
            )

            Spacer(modifier = Modifier.height(10.dp))

            ButtonCheckBoxLeftL(
                content = Budget.RANGE_30_50.displayName,
                isChecked = uiState.budget.name == Budget.RANGE_30_50.name,
                onCheckChange = { viewModel.onBudgetChanged(Budget.RANGE_30_50) }
            )

            Spacer(modifier = Modifier.height(10.dp))

            ButtonCheckBoxLeftL(
                content = Budget.RANGE_50_70.displayName,
                isChecked = uiState.budget.name == Budget.RANGE_50_70.name,
                onCheckChange = { viewModel.onBudgetChanged(Budget.RANGE_50_70) }
            )
        }
    }
}