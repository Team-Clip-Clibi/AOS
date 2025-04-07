package com.sungil.editprofile.ui.editProfile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sungil.editprofile.R
import com.sungil.editprofile.ui.CustomLittleTitleText
import com.sungil.editprofile.ui.CustomProfileItemWithImage
import com.sungil.editprofile.ui.CustomProfileItemWithMore
import com.sungil.editprofile.ui.CustomTwoText
import com.sungil.editprofile.ui.GraySpacer

@Composable
internal fun EditProfileMainView(
    paddingValues: PaddingValues,
    buttonClick: () -> Unit,
) {
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // 전체 배경 회색
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(
                    top = paddingValues.calculateTopPadding() + 20.dp,
                    bottom = 20.dp
                )
        ) {

            Section {
                CustomLittleTitleText(
                    text = stringResource(R.string.txt_title_default_info),
                    color = 0xFF666666
                )
                Spacer(modifier = Modifier.height(10.dp))
                CustomTwoText(
                    firstText = stringResource(R.string.txt_name),
                    firstTextColor = 0xFF171717,
                    subText = "테스트",
                    subTextColor = 0xFF171717
                )
                Spacer(modifier = Modifier.height(10.dp))
                CustomProfileItemWithImage(
                    title = stringResource(R.string.txt_sns_login),
                    textColor = 0xFF171717,
                    subTitle = "카카오톡",
                    subTitleColor = 0xFF171717,
                    imageResId = R.drawable.ic_kakao
                )
                Spacer(modifier = Modifier.height(10.dp))
                CustomTwoText(
                    firstText = stringResource(R.string.txt_phoneNumber),
                    firstTextColor = 0xFF171717,
                    subText = "010-1234-4532",
                    subTextColor = 0xFF171717
                )
                Spacer(modifier = Modifier.height(10.dp))
                CustomProfileItemWithMore(
                    title = stringResource(R.string.txt_nickName),
                    textColor = 0xFF171717,
                    subTitle = "AOS",
                    subTitleColor = 0xFF171717,
                    buttonClick = buttonClick
                )
            }

            GraySpacer()


            Section {
                CustomLittleTitleText(
                    text = stringResource(R.string.txt_title_oneThing_info),
                    color = 0xFF666666
                )
                Spacer(modifier = Modifier.height(10.dp))
                CustomProfileItemWithMore(
                    title = stringResource(R.string.txt_my_job),
                    textColor = 0xFF171717,
                    subTitle = "",
                    subTitleColor = 0xFF171717,
                    buttonClick = buttonClick
                )
                Spacer(modifier = Modifier.height(10.dp))
                CustomProfileItemWithMore(
                    title = stringResource(R.string.txt_love_state),
                    textColor = 0xFF171717,
                    subTitle = "",
                    subTitleColor = 0xFF171717,
                    buttonClick = buttonClick
                )
                Spacer(modifier = Modifier.height(10.dp))
                CustomProfileItemWithMore(
                    title = stringResource(R.string.txt_diet),
                    textColor = 0xFF171717,
                    subTitle = "",
                    subTitleColor = 0xFF171717,
                    buttonClick = buttonClick
                )
                Spacer(modifier = Modifier.height(10.dp))
                CustomProfileItemWithMore(
                    title = stringResource(R.string.txt_language),
                    textColor = 0xFF171717,
                    subTitle = "",
                    subTitleColor = 0xFF171717,
                    buttonClick = buttonClick
                )
            }

            GraySpacer()


            Section {
                CustomLittleTitleText(
                    text = stringResource(R.string.txt_title_account),
                    color = 0xFF666666
                )
                Spacer(modifier = Modifier.height(10.dp))
                CustomProfileItemWithMore(
                    title = stringResource(R.string.txt_log_out),
                    textColor = 0xFF171717,
                    subTitle = "",
                    subTitleColor = 0xFF171717,
                    buttonClick = buttonClick
                )
                CustomProfileItemWithMore(
                    title = stringResource(R.string.txt_sign_out),
                    textColor = 0xFF171717,
                    subTitle = "",
                    subTitleColor = 0xFF171717,
                    buttonClick = buttonClick
                )
            }
        }
    }
}

@Composable
fun Section(content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(top = 20.dp , start = 16.dp , end  = 17.dp)
    ) {
        content()
    }
}