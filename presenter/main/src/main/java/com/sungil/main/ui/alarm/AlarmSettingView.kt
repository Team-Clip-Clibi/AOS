package com.sungil.main.ui.alarm

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.core.ColorStyle
import com.sungil.main.MainViewModel
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.core.AppTextStyles
import com.sungil.main.ERROR_NETWORK_ERROR
import com.sungil.main.ERROR_RE_LOGIN
import com.sungil.main.R

@Composable
internal fun AlarmSettingView(
    viewModel: MainViewModel,
    snackbarHostState: SnackbarHostState,
    reLogin: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = ColorStyle.WHITE_100)
            .padding(top = 20.dp, start = 17.dp, end = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        AlarmStatus(viewModel = viewModel, snackbarHostState = snackbarHostState, reLogin = reLogin)
    }
}

@Composable
private fun AlarmStatus(
    viewModel: MainViewModel,
    snackbarHostState: SnackbarHostState,
    reLogin: () -> Unit,
) {
    val userState by viewModel.userState.collectAsState()
    val alarmState = userState.alarmStatus
    val context = LocalContext.current
    val isAlarmOn = when (alarmState) {
        is MainViewModel.UiState.Success -> alarmState.data as? Boolean ?: false
        else -> false
    }
    LaunchedEffect(alarmState) {
        when (val result = alarmState) {
            is MainViewModel.UiState.Error -> {
                when (result.message) {
                    ERROR_NETWORK_ERROR -> {
                        snackbarHostState.showSnackbar(
                            message = context.getString(R.string.msg_network_error),
                            duration = SnackbarDuration.Short
                        )
                    }

                    ERROR_RE_LOGIN -> {
                        reLogin()
                    }

                    else -> {
                        snackbarHostState.showSnackbar(
                            message = context.getString(com.sungil.alarm.R.string.msg_app_error),
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            }

            else -> Unit
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(62.dp)
            .padding(top = 10.dp, bottom = 10.dp, end = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(R.string.alarm_content),
                style = AppTextStyles.BODY_14_20_MEDIUM,
                color = ColorStyle.GRAY_800,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = stringResource(R.string.alarm_sub_content),
                style = AppTextStyles.CAPTION_12_18_SEMI,
                color = ColorStyle.GRAY_600,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Switch(
            checked = isAlarmOn,
            onCheckedChange = { result ->
                viewModel.changeAlarm(result)
            },
            modifier = Modifier
                .padding(2.dp)
                .width(52.dp)
                .height(28.dp),
            thumbContent = {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = null,
                    tint = ColorStyle.GRAY_300,
                    modifier = Modifier.size(10.dp)
                )
            },
            colors = SwitchDefaults.colors(
                checkedThumbColor = ColorStyle.WHITE_100,
                checkedTrackColor = ColorStyle.PURPLE_400,
                uncheckedThumbColor = ColorStyle.WHITE_100,
                uncheckedTrackColor = ColorStyle.GRAY_500
            )
        )
    }
}

