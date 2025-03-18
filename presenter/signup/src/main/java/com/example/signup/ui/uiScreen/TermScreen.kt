package com.example.signup.ui.uiScreen

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.signup.R
import com.example.signup.ui.component.CustomCheckBox

@Composable
internal fun TermScreen(paddingValues: PaddingValues) {
    var allChecked by remember { mutableStateOf(false) }
    val terms = listOf(
        TermItem("allChecked", true, isAllCheck = true),
        TermItem("serviceChecked", true),
        TermItem("collectPerson", true),
        TermItem("marketing", false)
    )

    var termsState by remember { mutableStateOf(terms.map { it.checked }) }

    LaunchedEffect(termsState) {
        allChecked = termsState.drop(1).all { it }
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
                modifier = Modifier.fillMaxWidth().height(60.dp).padding(8.dp),
                onCheckChange = { isChecked ->
                    allChecked = isChecked
                    termsState = termsState.map { _ -> isChecked }
                }
            )

            CustomCheckBox(
                text = stringResource(R.string.txt_term_service),
                checked = termsState[1],
                modifier = Modifier.fillMaxWidth().height(60.dp).padding(top = 12.dp, start = 8.dp, end = 8.dp, bottom = 8.dp),
                onCheckChange = { isChecked ->
                    termsState = termsState.toMutableList().also { it[1] = isChecked }
                }
            )

            CustomCheckBox(
                text = stringResource(R.string.txt_term_collect_person),
                checked = termsState[2],
                modifier = Modifier.fillMaxWidth().height(60.dp).padding(8.dp),
                onCheckChange = { isChecked ->
                    termsState = termsState.toMutableList().also { it[2] = isChecked }
                }
            )

            CustomCheckBox(
                text = stringResource(R.string.txt_term_marketing),
                checked = termsState[3],
                modifier = Modifier.fillMaxWidth().height(60.dp).padding(8.dp),
                onCheckChange = { isChecked ->
                    termsState = termsState.toMutableList().also { it[3] = isChecked }
                }
            )
        }


        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { /* TODO: 회원가입 진행 */ },
            enabled = termsState[1] && termsState[2], // ✅ 버튼 활성화 조건
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




data class TermItem(
    val termName : String,
    val isRequired: Boolean,
    val isAllCheck: Boolean = false,
    val checked: Boolean = false,
)

