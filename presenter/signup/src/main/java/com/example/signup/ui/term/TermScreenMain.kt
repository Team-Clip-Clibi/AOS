package com.example.signup.ui.term

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.signup.R
import com.example.signup.SignUpViewModel
import com.example.signup.ui.component.CustomCheckBox

@Composable
internal fun TermScreenMain(paddingValues: PaddingValues, viewModel: SignUpViewModel) {
    var allChecked by remember { mutableStateOf(false) }
    val termItems by viewModel.termItem.collectAsState(initial = emptyList())


    LaunchedEffect(termItems) {
        allChecked = termItems.drop(1).all { it.checked }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 17.dp, end = 17.dp, top = paddingValues.calculateTopPadding() + 32.dp)
    ) {
        Text(
            text = stringResource(R.string.txt_term_title),
            fontSize = 28.sp,
            fontFamily = FontFamily(Font(R.font.bold)),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 32.dp)
        )


        Column(modifier = Modifier.weight(1f)) {
            CustomCheckBox(
                text = stringResource(R.string.txt_term_okay_everyThing),
                checked = allChecked,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(8.dp).then(
                        if(allChecked){
                            Modifier
                            .border(2.dp, color = colorResource(R.color.lavender), shape = RoundedCornerShape(12.dp))
                                .background(color = colorResource(R.color.light_lavender), shape = RoundedCornerShape(12.dp))
                        }else{
                            Modifier
                        }
                    ),
                onCheckChange = { isChecked ->
                    allChecked = isChecked
                    termItems.forEach{ terms ->
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
                    .padding(top = 12.dp, start = 8.dp, end = 8.dp, bottom = 8.dp),
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
                    .padding(8.dp),
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
                    .padding(8.dp),
                onCheckChange = { isChecked ->
                    termItems.getOrNull(3)?.let { termItem ->
                        viewModel.changeTermItem(termItem.copy(checked = isChecked))
                    }
                }
            )
        }


        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { /* TODO: 회원가입 진행 */ },
            enabled = termItems.getOrNull(1)?.checked == true && termItems.getOrNull(2)?.checked == true,
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = stringResource(R.string.btn_start_oneThing), fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }


        Spacer(modifier = Modifier.height(21.dp))
    }
}
