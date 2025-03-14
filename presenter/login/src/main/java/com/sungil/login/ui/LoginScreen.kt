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
import androidx.compose.foundation.layout.padding
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
import com.sungil.login.R
import com.sungil.login.component.CustomButton
import com.sungil.login.component.CustomText
import com.sungil.login.component.PageIndicator
import kotlin.math.absoluteValue

@Composable
internal fun LoginScreen(
    kakaoLogin: () -> Unit
) {
    val test = listOf(
        "한 가지 주제로 깊이 있게",
        "고민 없이 딱 맞는 모임 추천"
    )
    val testImage = listOf(
        R.drawable.ic_cat,
        R.drawable.ic_dog
    )
    val pageState = rememberPagerState(pageCount = {
        2
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
                    font = FontFamily(Font(R.font.bold)),
                    textSize = 20.sp,
                    textColor = colorResource(R.color.black),
                    maxLine = 1
                )

                CustomText(
                    text = stringResource(R.string.txt_login_content),
                    font = FontFamily(Font(R.font.light)),
                    textSize = 16.sp,
                    textColor = colorResource(R.color.dark_gray),
                    maxLine = 1
                )

                Spacer(modifier = Modifier.height(16.dp))
                HorizontalPager(
                    state = pageState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f) // Adjust weight to allocate space properly
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
                        ) {
                            Image(
                                painter = painterResource(id = testImage[page]),
                                contentDescription = "page$page Image",
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier.fillMaxSize()
                            )
                            Text(
                                text = test[page],
                                fontSize = 24.sp,
                                fontFamily = FontFamily(Font(R.font.light)),
                                color = Color.Black,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .padding(16.dp)
                            )
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
            }

            CustomButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(size = 8.dp),
                buttonColor = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.yellow)),
                textColor = colorResource(R.color.black),
                text = stringResource(R.string.btn_kako),
                textSize = 14.sp,
                font = FontFamily(Font(R.font.regular)),
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
                textSize = 14.sp,
                font = FontFamily(Font(R.font.regular)),
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