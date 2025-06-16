package com.sungil.login.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core.AppTextStyles
import com.example.core.ColorStyle
import com.sungil.login.LoginViewModel
import com.sungil.login.R
import com.sungil.login.component.LoginPager
import com.sungil.login.component.PageIndicator

@Composable
internal fun LoginScreen(
    kakaoLogin: () -> Unit,
    viewModel: LoginViewModel,
) {
    val uiState by viewModel.actionFlow.collectAsState()
    val banners = (uiState.banner as? LoginViewModel.UiState.Success)?.data.orEmpty()
    val pageState = rememberPagerState { banners.size }

    Scaffold(
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = ColorStyle.WHITE_100)
                    .padding(start = 17.dp, end = 16.dp)
                    .navigationBarsPadding()
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    onClick = kakaoLogin,
                    enabled = uiState.banner is LoginViewModel.UiState.Success,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ColorStyle.YELLOW_100,
                        disabledContainerColor = ColorStyle.YELLOW_100.copy(alpha = 0.3f)
                    )
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_kako),
                        contentDescription = "kakao login",
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(
                        text = stringResource(R.string.btn_kako),
                        style = AppTextStyles.BODY_14_20_MEDIUM,
                        color = ColorStyle.GRAY_10_10
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    onClick = { /* TODO: Implement One Thing Preview */ },
                    enabled = true,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ColorStyle.COLOR_TRANSPARENT,
                        disabledContainerColor = ColorStyle.COLOR_TRANSPARENT
                    ),
                    elevation = ButtonDefaults.buttonElevation(0.dp) // 그림자 제거
                ) {
                    Text(
                        text = stringResource(R.string.btn_one_thing_preview),
                        style = AppTextStyles.BODY_14_20_MEDIUM,
                        color = ColorStyle.GRAY_700
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = ColorStyle.WHITE_100)
                .padding(
                    top = paddingValues.calculateTopPadding() + 40.dp,
                    start = 17.dp,
                    end = 16.dp,
                    bottom = paddingValues.calculateBottomPadding() + 52.dp
                )
        ) {
            Text(
                text = stringResource(R.string.txt_login_title),
                style = AppTextStyles.HEAD_28_40_BOLD,
                color = ColorStyle.GRAY_800,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = stringResource(R.string.txt_login_content),
                style = AppTextStyles.SUBTITLE_16_24_SEMI,
                color = ColorStyle.GRAY_600,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            LoginPager(
                state = pageState,
                data = banners,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            Spacer(modifier = Modifier.height(12.dp))
            PageIndicator(
                numberOfPages = pageState.pageCount,
                selectedPage = pageState.currentPage,
            )
        }
    }
}
