package com.example.signup.ui.term

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
                top = paddingValues.calculateTopPadding() + 32.dp,
                bottom = 8.dp
            )
            .navigationBarsPadding()
            .background(color = Color(0xFFFFFFFF))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = stringResource(R.string.txt_term_title),
                style = AppTextStyles.HEAD_28_40_BOLD,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp,         start = 17.dp,
                        end = 16.dp,)
            )


            CustomCheckBox(
                text = stringResource(R.string.txt_term_okay_everyThing),
                checked = allChecked,
                modifier = if (allChecked) {
                    Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(         start = 17.dp,
                            end = 16.dp,)
                        .border(
                            1.dp,
                            color = Color(0xFFD3ADF7),
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
                        .padding(         start = 17.dp,
                            end = 16.dp,)
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
                    .height(60.dp)
                    .padding(         start = 17.dp,
                        end = 16.dp,),
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
                    .height(60.dp)
                    .padding(         start = 17.dp,
                        end = 16.dp,),
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
                    .height(60.dp)
                    .padding(         start = 17.dp,
                        end = 16.dp,),
                onCheckChange = { isChecked ->
                    termItems.getOrNull(3)?.let { termItem ->
                        viewModel.changeTermItem(termItem.copy(checked = isChecked))
                    }
                }
            )

            Spacer(modifier = Modifier.weight(1f))

        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            HorizontalDivider(
                thickness = 1.dp,
                color = Color(0xFFEFEFEF)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { viewModel.sendTerm() },
                enabled = termItems.getOrNull(1)?.checked == true && termItems.getOrNull(2)?.checked == true,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(         start = 17.dp,
                        end = 16.dp,),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (termItems.getOrNull(1)?.checked == true &&
                        termItems.getOrNull(2)?.checked == true
                    ) Color(0xFF6700CE) else Color(0xFFEFEFEF)
                )
            ) {
                Text(
                    text = stringResource(R.string.btn_start_oneThing),
                    style = AppTextStyles.TITLE_20_28_SEMI,
                    color = if (termItems.getOrNull(1)?.checked == true && termItems.getOrNull(2)?.checked == true) {
                        Color(0xFFFFFFFF)
                    } else {
                        Color(0xFF171717)
                    },
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
