package com.clip.login.ui

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.clip.core.ColorStyle
import com.clip.login.R
import com.clip.login.component.AutoSlideNotice
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.clip.core.AppTextStyles
import com.clip.core.CustomDialogOneButton
import com.clip.login.component.BottomNavigation
import com.clip.login.component.CustomHomeButton
import com.clip.login.component.MatchList

@Composable
internal fun PreviewOneThing(onclick: () -> Unit) {
    var dialogShow by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = ColorStyle.GRAY_100)
                    .statusBarsPadding()
                    .padding(start = 17.dp, end = 12.dp, top = 12.dp, bottom = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .width(99.dp)
                        .height(24.dp),
                    painter = painterResource(id = R.drawable.ic_logo_str),
                    contentDescription = "oneThing logo",
                )
                Image(
                    modifier = Modifier
                        .width(48.dp)
                        .height(48.dp)
                        .padding(start = 12.dp, end = 12.dp),
                    painter = painterResource(R.drawable.ic_bell_signal),
                    contentDescription = "oneThing alarm",
                )
            }
        },
        bottomBar = {
            BottomNavigation(
                onClick = {
                    dialogShow = true
                }
            )
        }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = ColorStyle.GRAY_100)
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding() + 20.dp
                )
        ) {
            TopNoticeView()
            MatchView()
            MatchButtonView(
                onclick = {
                    dialogShow = true
                }
            )
            if (dialogShow) {
                CustomDialogOneButton(
                    onDismiss = onclick,
                    buttonClick = onclick,
                    titleText = stringResource(R.string.dialog_login_title),
                    contentText = stringResource(R.string.dialog_login_content),
                    buttonText = stringResource(R.string.dialog_login_button)
                )
            }
        }
    }
}

@Composable
private fun TopNoticeView(){
    val notice = listOf(
        "원띵 미리보기에 오신걸 환영합니다.",
        "천천히 미리보기를 즐겨주세요"
    )
    AutoSlideNotice(notice)
}

@Composable
private fun MatchView() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 32.dp,
                start = 17.dp,
                end = 16.dp,
            )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = stringResource(R.string.preview_nickname_match),
                style = AppTextStyles.TITLE_20_28_SEMI,
                color = ColorStyle.GRAY_700,
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "2",
                style = AppTextStyles.TITLE_20_28_SEMI,
                color = ColorStyle.PURPLE_400
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        MatchList()
    }
}
@Composable
private fun MatchButtonView(
    onclick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp, start = 17.dp, end = 16.dp)
    ) {
        Text(
            text = stringResource(R.string.txt_home_sub_title),
            style = AppTextStyles.TITLE_20_28_SEMI,
            color = ColorStyle.GRAY_700
        )
        Spacer(modifier = Modifier.height(12.dp))
        CustomHomeButton(
            titleText = stringResource(R.string.btn_home_oneThing),
            contentText = stringResource(R.string.btn_home_oneThing_content),
            onClick = {
                onclick()
            },
            image = R.drawable.ic_one_thing_match,
            modifier = Modifier
                .fillMaxWidth(),
            padding = Modifier.width(16.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CustomHomeButton(
                titleText = stringResource(R.string.btn_home_random),
                contentText = stringResource(R.string.btn_home_random_content),
                onClick = {
                    onclick()
                },
                image = R.drawable.ic_random_match,
                modifier = Modifier
                    .weight(1f)
                    .height(83.dp),
                padding = Modifier.width(12.dp)
            )
            CustomHomeButton(
                titleText = stringResource(R.string.btn_home_light),
                contentText = stringResource(R.string.btn_home_light_content),
                onClick = {
                    onclick()
                },
                image = R.drawable.ic_lighting_match,
                modifier = Modifier
                    .weight(1f)
                    .height(83.dp),
                padding = Modifier.width(12.dp)
            )
        }
    }
}