package com.sungil.main.ui.myapge

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core.AppTextStyles
import com.sungil.main.MainViewModel
import com.sungil.main.R
import com.sungil.main.component.CustomMyPageButton
import com.sungil.main.component.MyPageItem

@Composable
internal fun MyPageScreenMain(
    paddingValues: PaddingValues,
    viewModel: MainViewModel,
    profileEditButton: () -> Unit,
    reportClick: () -> Unit,
) {

    val userState by viewModel.userState.collectAsState()

    val userName = when (userState) {
        is MainViewModel.UserUiState.Success -> (userState as MainViewModel.UserUiState.Success).userData.nickName
        is MainViewModel.UserUiState.Loading -> ""
        is MainViewModel.UserUiState.Error -> "오류 발생"
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F7F7))
            .padding(
                top = paddingValues.calculateTopPadding() + 12.dp,
                bottom = 30.dp
            )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(start = 17.dp, end = 16.dp)
        ) {
            // 프로필 박스
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFFFFFF), shape = RoundedCornerShape(12.dp))
                    .height(171.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Row {
                        Text(
                            text = stringResource(R.string.txt_mayPage_hi),
                            style = AppTextStyles.SUBTITLE_18_22_SEMI,
                            color = Color(0xFF6700CE)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = stringResource(R.string.txt_myPage_sir, userName),
                            style = AppTextStyles.SUBTITLE_18_22_SEMI,
                            color = Color(0xFF171717)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    HorizontalDivider(
                        thickness = 1.dp,
                        color = Color(0xFFEFEFEF),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = stringResource(R.string.txt_myPage_count, 4),
                        style = AppTextStyles.BODY_14_20_MEDIUM
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    CustomMyPageButton(
                        text = stringResource(R.string.btn_myPage_edit_profile),
                        color = 0xFFEFEFEF,
                        textColor = 0xFF171717,
                        onClick = { profileEditButton() }
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 항목들
            MyPageItem(
                text = stringResource(R.string.txt_myPage_notify),
                icon = R.drawable.ic_bell,
                click = {}
            )
            Spacer(modifier = Modifier.height(12.dp))
            MyPageItem(
                text = stringResource(R.string.txt_myPage_notice),
                icon = R.drawable.ic_noti,
                click = {}
            )
            Spacer(modifier = Modifier.height(12.dp))
            MyPageItem(
                text = stringResource(R.string.txt_myPage_customer),
                icon = R.drawable.ic_customer_service,
                click = {}
            )
            Spacer(modifier = Modifier.height(12.dp))

            MyPageItem(
                text = stringResource(R.string.txt_myPage_police),
                icon = R.drawable.ic_alert,
                click = { reportClick() }
            )


            Spacer(modifier = Modifier.weight(1f))
        }

        // 하단
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            HorizontalDivider(
                thickness = 1.dp,
                color = Color(0xFFEFEFEF)
            )
            Row(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(
                        bottom = 8.dp,
                        top = 10.dp,
                        start = 16.dp
                    ),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.txt_myPage_term),
                    style = AppTextStyles.CAPTION_12_14_MEDIUM,
                    color = Color(0xFF383838),
                    modifier = Modifier.clickable { /* TODO */ }
                )

                Spacer(modifier = Modifier.width(16.dp))

                Image(
                    modifier = Modifier
                        .padding(1.dp)
                        .width(1.dp)
                        .height(8.dp),
                    painter = painterResource(id = R.drawable.ic_line),
                    contentDescription = "image description",
                    contentScale = ContentScale.None
                )

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = stringResource(R.string.txt_myPage_guide),
                    style = AppTextStyles.CAPTION_12_14_MEDIUM,
                    color = Color(0xFF383838),
                    modifier = Modifier.clickable { /* TODO */ }
                )
            }
        }
    }
}