package com.example.signup.ui.term

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core.AppTextStyles
import com.example.core.ColorStyle
import com.example.signup.R
import com.example.signup.SignUpViewModel
import com.example.signup.TERM_MARKET_PERMISSION
import com.example.signup.TERM_PRIVATE_PERMISSION
import com.example.signup.TERM_SERVICE_PERMISSION
import com.example.signup.ui.component.CustomCheckBox

@Composable
internal fun TermView(
    viewModel: SignUpViewModel,
) {
    var allChecked by remember { mutableStateOf(false) }
    val termItems by viewModel.termItem.collectAsState(initial = emptyList())
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = ColorStyle.WHITE_100)
            .padding(top = 32.dp, start = 17.dp, end = 16.dp)
    ) {
        Text(
            text = stringResource(R.string.txt_term_title),
            style = AppTextStyles.HEAD_28_40_BOLD,
            color = ColorStyle.GRAY_800,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(32.dp))
        CustomCheckBox(
            text = stringResource(R.string.txt_term_okay_everyThing),
            checked = allChecked,
            modifier = when (allChecked) {
                true -> {
                    Modifier
                        .border(
                            width = 1.dp,
                            color = ColorStyle.PURPLE_200,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .background(
                            color = ColorStyle.PURPLE_100,
                            shape = RoundedCornerShape(12.dp)
                        )
                }

                false -> {
                    Modifier.background(
                        color = ColorStyle.GRAY_100,
                        shape = RoundedCornerShape(12.dp)
                    )
                }
            },
            onCheckChange = { isChecked ->
                allChecked = isChecked
                termItems.forEach { terms ->
                    viewModel.changeTermItem(terms.copy(checked = isChecked))
                }
            },
            isIconShow = false
        )
        Spacer(modifier = Modifier.height(12.dp))
        CustomCheckBox(
            text = stringResource(R.string.txt_term_service),
            checked = termItems.getOrNull(TERM_SERVICE_PERMISSION)?.checked ?: false,
            onCheckChange = { isChecked ->
                termItems.getOrNull(TERM_SERVICE_PERMISSION)?.let { termItem ->
                    viewModel.changeTermItem(termItem.copy(checked = isChecked))
                }
            },
        )
        CustomCheckBox(
            text = stringResource(R.string.txt_term_collect_person),
            checked = termItems.getOrNull(TERM_PRIVATE_PERMISSION)?.checked ?: false,
            onCheckChange = { isChecked ->
                termItems.getOrNull(TERM_PRIVATE_PERMISSION)?.let { termItem ->
                    viewModel.changeTermItem(termItem.copy(checked = isChecked))
                }
            }
        )
        CustomCheckBox(
            text = stringResource(R.string.txt_term_marketing),
            checked = termItems.getOrNull(TERM_MARKET_PERMISSION)?.checked ?: false,
            onCheckChange = { isChecked ->
                termItems.getOrNull(TERM_MARKET_PERMISSION)?.let { termItem ->
                    viewModel.changeTermItem(termItem.copy(checked = isChecked))
                }
            }
        )
    }
}
