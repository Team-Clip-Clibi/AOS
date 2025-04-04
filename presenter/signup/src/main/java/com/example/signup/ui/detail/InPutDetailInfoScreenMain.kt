package com.example.signup.ui.detail

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material3.OpenOnPhoneDialogDefaults.Icon
import com.example.core.AppTextStyles
import com.example.signup.FEMALE
import com.example.signup.ISArea
import com.example.signup.ISCity
import com.example.signup.ISDay
import com.example.signup.ISMonth
import com.example.signup.ISYear
import com.example.signup.MALE
import com.example.signup.R
import com.example.signup.SignUpViewModel
import com.example.signup.ui.component.BottomSheetSelector
import com.example.signup.ui.component.CustomBottomSheet
import com.example.signup.ui.component.CustomButton
import com.example.signup.ui.component.CustomContentText
import com.example.signup.ui.component.CustomGenderPick
import com.example.signup.ui.component.CustomSpinnerBox
import com.example.signup.ui.component.CustomTextLittle
import com.example.signup.ui.component.CustomTitleText
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun InPutDetailInfoScreenMain(
    paddingValues: PaddingValues,
    viewModel: SignUpViewModel,
    buttonClick: () -> Unit,
) {
    var yearBottomSheet by remember { mutableStateOf(false) }
    var dayBottomSheet by remember { mutableStateOf(false) }
    var monthBottomSheet by remember { mutableStateOf(false) }
    var cityBottomSheet by remember { mutableStateOf(false) }
    var areaBottomSheet by remember { mutableStateOf(false) }

    val userInfo by viewModel.userInfoState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.userInfoState.collectLatest { userInfoState ->
            when (val detailState = userInfoState.detailState) {
                is SignUpViewModel.CheckState.StanBy -> {
                    Log.d(javaClass.name.toString(), "Stand by send Detail")
                }

                is SignUpViewModel.CheckState.ValueNotOkay -> {
                    Log.e(javaClass.name.toString(), detailState.errorMessage)
                }

                is SignUpViewModel.CheckState.ValueOkay -> {
                    Log.e(javaClass.name.toString(), "Success to signUp")
                    buttonClick()
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = paddingValues.calculateTopPadding() + 32.dp,
                bottom = 8.dp
            )
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            CustomTitleText(
                stringResource(R.string.txt_info_title),
                Modifier
                    .fillMaxWidth()
                    .padding(start = 17.dp, end = 17.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            CustomContentText(
                stringResource(R.string.txt_info_content),
                Modifier
                    .fillMaxWidth()
                    .padding(start = 17.dp, end = 17.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            CustomTextLittle(
                stringResource(R.string.txt_info_gender),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 17.dp, end = 17.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            CustomGenderPick(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(start = 17.dp, end = 17.dp)
                    .background(
                        color = colorResource(
                            if (userInfo.gender == MALE) R.color.light_lavender else R.color.light_gray
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .then(
                        if (userInfo.gender == MALE) {
                            Modifier.border(
                                width = 1.dp,
                                color = Color(0xFFD3ADF7),
                                shape = RoundedCornerShape(12.dp)
                            )
                        } else {
                            Modifier
                        }
                    ),
                text = stringResource(R.string.txt_info_male),
                clickable = { viewModel.inputGender(MALE) }
            )

            Spacer(modifier = Modifier.height(10.dp))

            CustomGenderPick(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(start = 17.dp, end = 17.dp)
                    .background(
                        colorResource(
                            if (userInfo.gender == FEMALE) R.color.light_lavender else R.color.light_gray
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .then(
                        if (userInfo.gender == FEMALE) {
                            Modifier.border(
                                width = 1.dp,
                                color = Color(0xFFD3ADF7),
                                shape = RoundedCornerShape(12.dp)
                            )
                        } else {
                            Modifier
                        }
                    ),
                text = stringResource(R.string.txt_info_female),
                clickable = { viewModel.inputGender(FEMALE) }
            )

            Spacer(modifier = Modifier.height(24.dp))

            CustomTextLittle(
                stringResource(R.string.txt_info_birth),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 17.dp, end = 17.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 17.dp, end = 17.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CustomSpinnerBox(
                    text = userInfo.birthYear.ifEmpty { stringResource(R.string.txt_info_year) },
                    onclick = {
                        yearBottomSheet = true
                        monthBottomSheet = false
                        dayBottomSheet = false
                        cityBottomSheet = false
                        areaBottomSheet = false
                    }
                )

                Spacer(modifier = Modifier.width(10.dp))

                CustomSpinnerBox(
                    text = userInfo.birthMonth.ifEmpty { stringResource(R.string.txt_info_month) },
                    onclick = {
                        yearBottomSheet = false
                        monthBottomSheet = true
                        dayBottomSheet = false
                        cityBottomSheet = false
                        areaBottomSheet = false
                    }
                )

                Spacer(modifier = Modifier.width(10.dp))

                CustomSpinnerBox(
                    text = userInfo.birtDay.ifEmpty { stringResource(R.string.txt_info_day) },
                    onclick = {
                        yearBottomSheet = false
                        monthBottomSheet = false
                        dayBottomSheet = true
                        cityBottomSheet = false
                        areaBottomSheet = false
                    }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            CustomTextLittle(
                stringResource(R.string.txt_info_activate),
                modifier = Modifier.padding(start = 17.dp, end = 17.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 17.dp, end = 17.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CustomSpinnerBox(
                    text = userInfo.city.ifEmpty { stringResource(R.string.txt_info_city) },
                    onclick = {
                        yearBottomSheet = false
                        monthBottomSheet = false
                        dayBottomSheet = false
                        cityBottomSheet = true
                        areaBottomSheet = false
                    }
                )

                Spacer(modifier = Modifier.width(10.dp))

                CustomSpinnerBox(
                    text = userInfo.area.ifEmpty { stringResource(R.string.txt_info_area) },
                    onclick = {
                        yearBottomSheet = false
                        monthBottomSheet = false
                        dayBottomSheet = false
                        cityBottomSheet = false
                        areaBottomSheet = true
                    }
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.weight(1f))

            // Divider만 예외! 패딩 없음
            HorizontalDivider(
                thickness = 1.dp,
                color = Color(0xFFEFEFEF)
            )

            Spacer(modifier = Modifier.height(8.dp))

            CustomButton(
                stringResource(R.string.btn_finish),
                onclick = { viewModel.sendDetail() },
                enable = userInfo.birthYear.isNotEmpty() &&
                        userInfo.birthMonth.isNotEmpty() &&
                        userInfo.birtDay.isNotEmpty() &&
                        userInfo.city.isNotEmpty() &&
                        userInfo.area.isNotEmpty() &&
                        userInfo.gender.isNotEmpty(),

            )

            BottomSheetSelector(
                yearBottomSheet = yearBottomSheet,
                monthBottomSheet = monthBottomSheet,
                dayBottomSheet = dayBottomSheet,
                cityBottomSheet = cityBottomSheet,
                areaBottomSheet = areaBottomSheet,
                onSelect = { selectedValue ->
                    when {
                        yearBottomSheet -> viewModel.setBirthYear(selectedValue)
                        monthBottomSheet -> viewModel.setBirthMonth(selectedValue)
                        dayBottomSheet -> viewModel.setBirthDay(selectedValue)
                        cityBottomSheet -> viewModel.setCity(selectedValue)
                        areaBottomSheet -> viewModel.setArea(selectedValue)
                    }
                },
                onDismiss = {
                    yearBottomSheet = false
                    monthBottomSheet = false
                    dayBottomSheet = false
                    cityBottomSheet = false
                    areaBottomSheet = false
                }
            )
        }
    }
}
