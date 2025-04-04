package com.sungil.login.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import com.example.core.AppTextStyles
import com.sungil.login.R
import com.sungil.login.component.CustomButton
import com.sungil.login.component.CustomText
import com.sungil.login.component.PageIndicator
import kotlin.math.absoluteValue

@Composable
internal fun LoginScreen(
    kakaoLogin: () -> Unit,
) {
    val test = listOf(
        "한 가지 주제로 깊이 있게",
        "고민 없이 딱 맞는 모임 추천",
        "긿 잃을 필요 없이, 바로 연결",
    )
    val test2 = listOf(
        "원하는 대화, 원하는 사람과 함께해요.",
        "원띵에서 추천해드려요",
        "나와 맞는 모임을 찾아드려요."
    )
    val pageState = rememberPagerState(pageCount = {
        3
    })
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Spacer(modifier = Modifier.height(40.dp))

                // 제목
                CustomText(
                    text = stringResource(R.string.txt_login_title),
                    style = AppTextStyles.HEAD_28_40_BOLD,
                    textColor = Color(0xFF171717),
                    maxLine = 1
                )

                CustomText(
                    text = stringResource(R.string.txt_login_content),
                    style = AppTextStyles.SUBTITLE_16_24_SEMI,
                    textColor = Color(0xFF666666),
                    maxLine = 1
                )

                Spacer(modifier = Modifier.height(16.dp))
                HorizontalPager(
                    state = pageState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)

                ) { page ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .graphicsLayer {
                                val pageOffset = (
                                        (pageState.currentPage - page) + pageState.currentPageOffsetFraction
                                        ).absoluteValue
                                alpha = lerp(
                                    start = 0.5f,
                                    stop = 1f,
                                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                )
                            },
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize()
                                .background(color  =Color(0xFFF7F7F7))
                                .padding(bottom = 44.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = test[page],
                                    style = AppTextStyles.HEAD_24_34_BOLD,
                                    color = Color(0xFF171717),
                                    textAlign = TextAlign.Center,
                                )

                                Text(
                                    text = test2[page],
                                    style = AppTextStyles.SUBTITLE_16_24_SEMI,
                                    color = Color(0xFF383838),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 인디케이터 추가
                PageIndicator(
                    numberOfPages = pageState.pageCount,
                    selectedPage = pageState.currentPage,
                    selectedColor = colorResource(R.color.purple),
                    defaultColor = colorResource(R.color.dark_gray),
                    defaultRadius = 8.dp,
                    selectedLength = 16.dp,
                    space = 4.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )

                Spacer(modifier = Modifier.height(22.dp))
            }

            CustomButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(size = 8.dp),
                buttonColor = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.yellow)),
                textColor = colorResource(R.color.black),
                text = stringResource(R.string.btn_kako),
                onclick = kakaoLogin,
                textUnderLine = false,
                imageId = R.drawable.ic_kako,
                imageDescription = "snsLogin"
            )

            Spacer(modifier = Modifier.height(8.dp))

            CustomButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(size = 8.dp),
                buttonColor = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.transparent)),
                textColor = colorResource(R.color.dark_gray),
                text = stringResource(R.string.btn_one_thing_preview),
                onclick = {},
                textUnderLine = true
            )
        }
    }
}
//@Preview
//@Composable
//private fun preview() {
//    LoginScreen()
//}