package com.sungil.editprofile.ui.loveState

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sungil.editprofile.ERROR_FAIL_SAVE
import com.sungil.editprofile.ERROR_FAIL_TO_UPDATE_LOVE
import com.sungil.editprofile.ERROR_TOKEN_NULL
import com.sungil.editprofile.LOVE
import com.sungil.editprofile.MEETING
import com.sungil.editprofile.ProfileEditViewModel
import com.sungil.editprofile.R
import com.sungil.editprofile.ui.CustomButton
import com.sungil.editprofile.ui.CustomItemPick
import com.sungil.editprofile.ui.CustomUnderTextFieldText

@Composable
internal fun LoveStateMainView(
    viewModel: ProfileEditViewModel,
    dataChangedFinished: () -> Unit,
    paddingValues: PaddingValues,
    snackBarHost: SnackbarHostState,
) {
    val loveState by viewModel.loveState.collectAsState()
    val button by viewModel.button.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.editProfileState.collect{ state ->
            when(state){
                is ProfileEditViewModel.EditProfileState.Error -> {
                    when(state.message){
                        ERROR_TOKEN_NULL  ->{
                            snackBarHost.showSnackbar(
                                message = context.getString(R.string.txt_network_error),
                                duration = SnackbarDuration.Short
                            )
                        }
                        ERROR_FAIL_TO_UPDATE_LOVE ->{
                            snackBarHost.showSnackbar(
                                message = context.getString(R.string.txt_network_error),
                                duration = SnackbarDuration.Short
                            )
                        }
                        ERROR_FAIL_SAVE ->{
                            snackBarHost.showSnackbar(
                                message = context.getString(R.string.msg_save_error),
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                }
                is ProfileEditViewModel.EditProfileState.SuccessToChange -> {
                    viewModel.initFlow()
                    snackBarHost.showSnackbar(
                        message = context.getString(R.string.msg_save_success),
                        duration = SnackbarDuration.Short
                    )
                }
                else -> Unit
            }
        }
    }
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = paddingValues.calculateTopPadding() + 32.dp, bottom = 8.dp)
            .verticalScroll(scrollState)
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 17.dp, end = 16.dp)
        ) {
            CustomUnderTextFieldText(
                text = stringResource(R.string.txt_love_state),
                color = Color(0xFF171717)
            )
            Spacer(modifier = Modifier.height(10.dp))
            CustomItemPick(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(
                        color = if (loveState.love == LOVE.SINGLE) {
                            Color(0xFFF9F0FF)
                        } else {
                            Color(0xFFF7F7F7)
                        },
                        shape = RoundedCornerShape(8.dp)
                    )
                    .then(
                        if (loveState.love == LOVE.SINGLE) {
                            Modifier.border(
                                width = 1.dp,
                                color = Color(0xFFD3ADF7),
                                shape = RoundedCornerShape(12.dp)
                            )
                        } else {
                            Modifier
                        }
                    ),
                text = stringResource(R.string.txt_love_single),
                clickable = { viewModel.changeLoveState(LOVE.SINGLE) }
            )
            Spacer(modifier = Modifier.height(10.dp))
            CustomItemPick(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(
                        color = if (loveState.love == LOVE.COUPLE) {
                            Color(0xFFF9F0FF)
                        } else {
                            Color(0xFFF7F7F7)
                        },
                        shape = RoundedCornerShape(8.dp)
                    )
                    .then(
                        if (loveState.love == LOVE.COUPLE) {
                            Modifier.border(
                                width = 1.dp,
                                color = Color(0xFFD3ADF7),
                                shape = RoundedCornerShape(12.dp)
                            )
                        } else {
                            Modifier
                        }
                    ),
                text = stringResource(R.string.txt_love_ing),
                clickable = { viewModel.changeLoveState(LOVE.COUPLE) }
            )
            Spacer(modifier = Modifier.height(10.dp))
            CustomItemPick(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(
                        color = if (loveState.love == LOVE.MARRIAGE) {
                            Color(0xFFF9F0FF)
                        } else {
                            Color(0xFFF7F7F7)
                        },
                        shape = RoundedCornerShape(8.dp)
                    )
                    .then(
                        if (loveState.love == LOVE.MARRIAGE) {
                            Modifier.border(
                                width = 1.dp,
                                color = Color(0xFFD3ADF7),
                                shape = RoundedCornerShape(12.dp)
                            )
                        } else {
                            Modifier
                        }
                    ),
                text = stringResource(R.string.txt_love_married),
                clickable = { viewModel.changeLoveState(LOVE.MARRIAGE) }
            )
            Spacer(modifier = Modifier.height(10.dp))
            CustomItemPick(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(
                        color = if (loveState.love == LOVE.SECRET) {
                            Color(0xFFF9F0FF)
                        } else {
                            Color(0xFFF7F7F7)
                        },
                        shape = RoundedCornerShape(8.dp)
                    )
                    .then(
                        if (loveState.love == LOVE.SECRET) {
                            Modifier.border(
                                width = 1.dp,
                                color = Color(0xFFD3ADF7),
                                shape = RoundedCornerShape(12.dp)
                            )
                        } else {
                            Modifier
                        }
                    ),
                text = stringResource(R.string.txt_love_no_show),
                clickable = { viewModel.changeLoveState(LOVE.SECRET) }
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        HorizontalDivider(
            thickness = 1.dp,
            color = Color(0xFFEFEFEF)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 17.dp, end = 16.dp , top = 24.dp)
        ) {
            CustomUnderTextFieldText(
                text = stringResource(R.string.txt_love_matching),
                color = Color(0xFF171717)
            )
            Spacer(modifier = Modifier.height(10.dp))
            CustomItemPick(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(
                        color = if (loveState.Meeting == MEETING.SAME) {
                            Color(0xFFF9F0FF)
                        } else {
                            Color(0xFFF7F7F7)
                        },
                        shape = RoundedCornerShape(8.dp)
                    )
                    .then(
                        if (loveState.Meeting == MEETING.SAME) {
                            Modifier.border(
                                width = 1.dp,
                                color = Color(0xFFD3ADF7),
                                shape = RoundedCornerShape(12.dp)
                            )
                        } else {
                            Modifier
                        }
                    ),
                text = stringResource(R.string.txt_love_same),
                clickable = { viewModel.changeMeetingState(MEETING.SAME) }
            )
            Spacer(modifier = Modifier.height(10.dp))
            CustomItemPick(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(
                        color = if (loveState.Meeting == MEETING.OKAY) {
                            Color(0xFFF9F0FF)
                        } else {
                            Color(0xFFF7F7F7)
                        },
                        shape = RoundedCornerShape(8.dp)
                    )
                    .then(
                        if (loveState.Meeting == MEETING.OKAY) {
                            Modifier.border(
                                width = 1.dp,
                                color = Color(0xFFD3ADF7),
                                shape = RoundedCornerShape(12.dp)
                            )
                        } else {
                            Modifier
                        }
                    ),
                text = stringResource(R.string.txt_love_never_mind),
                clickable = { viewModel.changeMeetingState(MEETING.OKAY) }
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            HorizontalDivider(thickness = 1.dp, color = Color(0xFFEFEFEF))
            Spacer(modifier = Modifier.height(8.dp))
            CustomButton(
                stringResource(R.string.btn_finish),
                onclick = { viewModel.sendLoveState()},
                enable = button
            )
        }
    }
}