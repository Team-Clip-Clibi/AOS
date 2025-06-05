package com.sungil.onethingmatch.ui.budget

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
import com.example.core.ButtonCheckBoxLeftL
import com.example.core.ColorStyle
import com.sungil.onethingmatch.Budget
import com.sungil.onethingmatch.OneThingViewModel
import com.sungil.onethingmatch.R

@Composable
internal fun BudgetView(
    viewModel: OneThingViewModel
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
    ) {
        Text(
            text = stringResource(R.string.txt_budget_title),
            style = AppTextStyles.HEAD_28_40_BOLD,
            color = ColorStyle.GRAY_800,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = stringResource(R.string.txt_budget_sub_title),
            style = AppTextStyles.SUBTITLE_16_24_SEMI,
            color = ColorStyle.GRAY_600,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        ButtonCheckBoxLeftL(
            content = Budget.LOW.displayName,
            isChecked = uiState.budget == Budget.LOW,
            onCheckChange = { viewModel.onBudgetChanged(Budget.LOW) }
        )

        Spacer(modifier = Modifier.height(10.dp))

        ButtonCheckBoxLeftL(
            content = Budget.MEDIUM.displayName,
            isChecked = uiState.budget.name == Budget.MEDIUM.name,
            onCheckChange = { viewModel.onBudgetChanged(Budget.MEDIUM) }
        )

        Spacer(modifier = Modifier.height(10.dp))

        ButtonCheckBoxLeftL(
            content = Budget.HIGH.displayName,
            isChecked = uiState.budget.name == Budget.HIGH.name,
            onCheckChange = { viewModel.onBudgetChanged(Budget.HIGH) }
        )
    }
}
