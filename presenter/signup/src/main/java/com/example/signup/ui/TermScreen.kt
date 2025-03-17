package com.example.signup.ui

import androidx.compose.runtime.Composable

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.signup.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TermScreen() {
    var allChecked by remember { mutableStateOf(false) }
    val terms = listOf(
        TermItem("필수 약관 모두 동의", true, isAllCheck = true),
        TermItem("[필수] 서비스 이용약관", true),
        TermItem("[필수] 개인정보 수집/이용 동의", true),
        TermItem("[선택] 맞춤형 광고 및 마케팅 수집 동의", false)
    )

    var termsState by remember { mutableStateOf(terms.map { it.checked }) }

    LaunchedEffect(termsState) {
        allChecked = termsState.drop(1).all { it }
    }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    modifier = Modifier.padding(start = 12.dp, end = 16.dp),
                    title = {
                        Text(
                            text = stringResource(R.string.txt_topBar_title),
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp,
                            fontFamily = FontFamily(Font(R.font.bold))
                        )
                    },
                    navigationIcon = {
                        Image(
                            painter = painterResource(id = R.drawable.ic_arrow_back),
                            contentDescription = "뒤로가기",
                            modifier = Modifier
                                .padding(1.dp)
                                .width(24.dp)
                                .height(24.dp)
                                .clickable { }
                        )
                    },
                    actions = {
                        Text(
                            text = "1/5",
                            style = TextStyle(
                                fontSize = 12.sp,
                                lineHeight = 18.sp,
                                fontFamily = FontFamily(Font(R.font.light)),
                                fontWeight = FontWeight(600),
                                color = Color(0xFF9254DE),
                                textAlign = TextAlign.End
                            )
                        )
                    }
                )
                HorizontalDivider(thickness = 1.dp, color = Color(0xFFEFEFEF))
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(20.dp)
        ) {
            Text(
                text = "원띵 서비스 이용에\n동의해주세요",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 20.dp)
            )

            LazyColumn(modifier = Modifier.weight(1f)) {
                itemsIndexed(terms) { index, term ->
                    val termIndex = terms.indexOf(term)
                    TermItemView(
                        term = term,
                        checked = termsState[termIndex],
                        onCheckedChange = { checked ->
                            termsState = termsState.toMutableList().apply {
                                this[termIndex] = checked
                                if (term.isAllCheck) {
                                    // 전체 동의 버튼 클릭 시 모든 항목 변경
                                    fill(checked)
                                }
                            }
                        }
                    )
                    if (index < terms.size - 1) { // 마지막 항목 이후에는 Divider 추가 안 함
                        Divider(color = Color.LightGray, thickness = 0.5.dp)
                    }
                }
            }

            Button(
                onClick = { /* TODO: 회원가입 진행 */ },
                enabled = termsState.drop(1).all { it }, // 필수 항목 체크 여부 확인
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(text = "원띵 시작하기", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun TermItemView(term: TermItem, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onCheckedChange(!checked) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = null // 클릭 이벤트는 Row에서 처리
        )
        Text(
            text = term.title,
            fontSize = 16.sp,
            fontWeight = if (term.isRequired) FontWeight.Bold else FontWeight.Normal,
            modifier = Modifier.weight(1f)
        )
        if (!term.isAllCheck) {
            Text(
                text = ">",
                fontSize = 16.sp,
                textAlign = TextAlign.End,
                modifier = Modifier.padding(end = 8.dp)
            )
        }
    }
}

@Preview
@Composable
fun PreviewTermScreen() {
    TermScreen()
}

// 데이터 클래스
data class TermItem(val title: String, val isRequired: Boolean, val isAllCheck: Boolean = false, val checked: Boolean = false)

