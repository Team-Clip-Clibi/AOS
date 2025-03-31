package com.example.signup.ui.term

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.core.AppTextStyles
import com.example.signup.R
import com.example.signup.SignUpViewModel
import com.example.signup.ui.component.CustomCheckBox
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun TermScreenMain(
    paddingValues: PaddingValues,
    viewModel: SignUpViewModel,
    buttonClick: () -> Unit,
) {
    var allChecked by remember { mutableStateOf(false) }
    val termItems by viewModel.termItem.collectAsState(initial = emptyList())

    LaunchedEffect(termItems) {
        allChecked = termItems.drop(1).all { it.checked }
        viewModel.userInfoState.collectLatest { userInfo ->
            when (val termState = userInfo.termSendState) {
                is SignUpViewModel.CheckState.StanBy -> {
                    Log.d(javaClass.name.toString(), "wait for send Term")
                }

                is SignUpViewModel.CheckState.ValueNotOkay -> {
                    Log.d(
                        javaClass.name.toString(),
                        "Error to send Term Data ${termState.errorMessage}"
                    )
                }

                is SignUpViewModel.CheckState.ValueOkay -> {
                    viewModel.resetTermData()
                    buttonClick()
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 17.dp,
                end = 17.dp,
                top = paddingValues.calculateTopPadding() + 32.dp,
                bottom = 21.dp
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = stringResource(R.string.txt_term_title),
                style = AppTextStyles.HEAD_24_34_BOLD,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp)
            )


            CustomCheckBox(
                text = stringResource(R.string.txt_term_okay_everyThing),
                checked = allChecked,
                modifier = if (allChecked) {
                    Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .border(
                            1.dp,
                            color = Color(0xFF6700CE),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .background(
                            color = Color(0xFFF9F0FF),
                            shape = RoundedCornerShape(12.dp)
                        )
                } else {
                    Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .background(
                            color = Color(0xFFF7F7F7),
                            shape = RoundedCornerShape(size = 12.dp)
                        )
                },
                onCheckChange = { isChecked ->
                    allChecked = isChecked
                    termItems.forEach { terms ->
                        viewModel.changeTermItem(terms.copy(checked = isChecked))
                    }
                },
                isIconShow = false
            )

            CustomCheckBox(
                text = stringResource(R.string.txt_term_service),
                checked = termItems.getOrNull(1)?.checked ?: false,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                onCheckChange = { isChecked ->
                    termItems.getOrNull(1)?.let { termItem ->
                        viewModel.changeTermItem(termItem.copy(checked = isChecked))
                    }
                }
            )

            CustomCheckBox(
                text = stringResource(R.string.txt_term_collect_person),
                checked = termItems.getOrNull(2)?.checked ?: false,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                onCheckChange = { isChecked ->
                    termItems.getOrNull(2)?.let { termItem ->
                        viewModel.changeTermItem(termItem.copy(checked = isChecked))
                    }
                }
            )

            CustomCheckBox(
                text = stringResource(R.string.txt_term_marketing),
                checked = termItems.getOrNull(3)?.checked ?: false,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                onCheckChange = { isChecked ->
                    termItems.getOrNull(3)?.let { termItem ->
                        viewModel.changeTermItem(termItem.copy(checked = isChecked))
                    }
                }
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { viewModel.sendTerm() },
                enabled = termItems.getOrNull(1)?.checked == true && termItems.getOrNull(2)?.checked == true,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (termItems.getOrNull(1)?.checked == true &&
                        termItems.getOrNull(2)?.checked == true
                    ) Color(0xFF6700CE) else Color(0xFFEFEFEF)
                )
            ) {
                Text(
                    text = stringResource(R.string.btn_start_oneThing),
                    style = AppTextStyles.TITLE_20_28_SEMI,
                    color = Color(0xFFFFFFFF),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
