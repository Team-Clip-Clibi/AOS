package com.sungil.editprofile.ui.loveState

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sungil.editprofile.LOVE
import com.sungil.editprofile.MEETING
import com.sungil.editprofile.ProfileEditViewModel
import com.sungil.editprofile.R
import com.sungil.editprofile.ui.CustomButton
import com.sungil.editprofile.ui.CustomLovePick
import com.sungil.editprofile.ui.CustomUnderTextFieldText

@Composable
internal fun LoveStateMainView(
    viewModel: ProfileEditViewModel,
    dataChangedFinished: () -> Unit,
    paddingValues: PaddingValues,
    snackBarHost: SnackbarHostState,
) {
    val loveState by viewModel.loveState.collectAsState()
    val button by viewModel.loveButton.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = paddingValues.calculateTopPadding() + 32.dp, bottom = 8.dp)
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
            CustomLovePick(
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
            CustomLovePick(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(
                        color = if (loveState.love == LOVE.TAKEN) {
                            Color(0xFFF9F0FF)
                        } else {
                            Color(0xFFF7F7F7)
                        },
                        shape = RoundedCornerShape(8.dp)
                    )
                    .then(
                        if (loveState.love == LOVE.TAKEN) {
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
                clickable = { viewModel.changeLoveState(LOVE.TAKEN) }
            )
            Spacer(modifier = Modifier.height(10.dp))
            CustomLovePick(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(
                        color = if (loveState.love == LOVE.MARRIED) {
                            Color(0xFFF9F0FF)
                        } else {
                            Color(0xFFF7F7F7)
                        },
                        shape = RoundedCornerShape(8.dp)
                    )
                    .then(
                        if (loveState.love == LOVE.MARRIED) {
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
                clickable = { viewModel.changeLoveState(LOVE.MARRIED) }
            )
            Spacer(modifier = Modifier.height(10.dp))
            CustomLovePick(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(
                        color = if (loveState.love == LOVE.NO_SHOW) {
                            Color(0xFFF9F0FF)
                        } else {
                            Color(0xFFF7F7F7)
                        },
                        shape = RoundedCornerShape(8.dp)
                    )
                    .then(
                        if (loveState.love == LOVE.NO_SHOW) {
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
                clickable = { viewModel.changeLoveState(LOVE.NO_SHOW) }
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
            CustomLovePick(
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
            CustomLovePick(
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