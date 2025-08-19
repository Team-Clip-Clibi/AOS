package com.sungil.main.ui.myPage

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.core.ColorStyle
import com.sungil.main.MainViewModel
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.core.AppTextStyles
import com.example.core.ButtonL
import com.sungil.domain.model.MatchData
import com.sungil.main.R
import com.sungil.main.component.MyPageItem
import kotlinx.coroutines.launch

@Composable
internal fun MyPageViewScreen(
    viewModel: MainViewModel,
    profileEdit: () -> Unit,
    reportClick: () -> Unit,
    lowGuide: () -> Unit,
    alarmSetting : () -> Unit,
    snackBarHostState: SnackbarHostState
) {
    val userState by viewModel.userState.collectAsState()
    var lastBackPressed by remember { mutableLongStateOf(0L) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    BackHandler {
        val now = System.currentTimeMillis()
        if (now - lastBackPressed <= 2000L) {
            (context as? Activity)?.finish() // 또는 finishAffinity()로 완전 종료
        } else {
            lastBackPressed = now
            scope.launch {
                snackBarHostState.currentSnackbarData?.dismiss()
                snackBarHostState.showSnackbar(
                    message = context.getString(R.string.app_finish),
                    duration = SnackbarDuration.Short
                )
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = ColorStyle.GRAY_100)
            .padding(top = 12.dp)
            .verticalScroll(rememberScrollState())
    ) {
        ProfileBoxView(userState = userState, profileEdit = profileEdit)
        ButtonView(reportClick = reportClick ,alarmSetting = alarmSetting)
        Spacer(modifier = Modifier.weight(1f))
        BottomView(lowGuide = lowGuide)
    }
}

@Composable
private fun ProfileBoxView(userState: MainViewModel.MainViewState, profileEdit: () -> Unit) {
    val userName = when (userState.userDataState) {
        is MainViewModel.UiState.Success -> userState.userDataState.data.nickName ?: "error"
        is MainViewModel.UiState.Loading -> ""
        is MainViewModel.UiState.Error -> "error"
    }
    val matchState = userState.matchState
    val matchData = (matchState as? MainViewModel.UiState.Success<MatchData>)?.data
    val oneThingSize = matchData?.oneThingMatch?.size ?: 0
    val randomMatchSize = matchData?.randomMatch?.size ?: 0
    val totalMatchSize = oneThingSize + randomMatchSize
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 17.dp, end = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(171.dp)
                .background(color = ColorStyle.WHITE_100, shape = RoundedCornerShape(12.dp))
                .padding(20.dp)
        ) {
            Row {
                Text(
                    text = stringResource(R.string.txt_mayPage_hi),
                    style = AppTextStyles.SUBTITLE_18_26_SEMI,
                    color = ColorStyle.PURPLE_400
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = stringResource(R.string.txt_myPage_sir, userName),
                    style = AppTextStyles.SUBTITLE_18_26_SEMI,
                    color = ColorStyle.GRAY_800
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider(
                thickness = 1.dp,
                color = ColorStyle.GRAY_200,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = stringResource(R.string.txt_myPage_count, totalMatchSize),
                style = AppTextStyles.BODY_14_20_MEDIUM,
                color = ColorStyle.GRAY_700
            )
            Spacer(modifier = Modifier.height(16.dp))
            ButtonL(
                text = stringResource(R.string.btn_myPage_edit_profile),
                onClick = {
                    profileEdit()
                },
                buttonColor = ColorStyle.GRAY_200,
                textColor = ColorStyle.GRAY_800
            )
        }
    }
}

@Composable
private fun ButtonView(reportClick: () -> Unit, alarmSetting: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp, start = 17.dp, end = 16.dp)
    ) {
        MyPageItem(
            text = stringResource(R.string.txt_myPage_notify),
            icon = R.drawable.ic_bell,
            click = { alarmSetting() }
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
    }
}

@Composable
private fun BottomView(lowGuide: () -> Unit) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 8.dp)) {
        HorizontalDivider(
            thickness = 1.dp,
            color = ColorStyle.GRAY_300,
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            modifier = Modifier.padding(top = 10.dp, start = 16.dp, bottom = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.txt_myPage_term),
                style = AppTextStyles.CAPTION_12_14_MEDIUM,
                color = ColorStyle.GRAY_700,
                modifier = Modifier.clickable { lowGuide() }
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
                color = ColorStyle.GRAY_700,
                modifier = Modifier.clickable { /* TODO */ }
            )
        }
    }
}