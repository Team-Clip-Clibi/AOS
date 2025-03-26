package com.example.signup.ui.detail

import androidx.compose.foundation.background
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
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

@Composable
internal fun InPutDetailInfoScreenMain(
    paddingValues: PaddingValues,
    viewModel: SignUpViewModel,
    buttonClick: () -> Unit,
) {
    val gender by viewModel.gender.collectAsState()
    var yearBottomSheet by remember { mutableStateOf(false) }
    var dayBottomSheet by remember { mutableStateOf(false) }
    var monthBottomSheet by remember { mutableStateOf(false) }
    var cityBottomSheet by remember { mutableStateOf(false) }
    var areaBottomSheet by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 17.dp,
                end = 17.dp,
                top = paddingValues.calculateTopPadding() + 32.dp,
                bottom = 21.dp
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize() . verticalScroll(rememberScrollState())
        ) {
            CustomTitleText(stringResource(R.string.txt_info_title))
            CustomContentText(stringResource(R.string.txt_info_content))

            Spacer(modifier = Modifier.height(32.dp))
            CustomTextLittle(stringResource(R.string.txt_info_gender))

            Spacer(modifier = Modifier.height(10.dp))
            //남성버튼
            CustomGenderPick(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(
                        colorResource(
                            when (gender) {
                                MALE -> {
                                    R.color.light_lavender
                                }

                                FEMALE -> {
                                    R.color.light_gray
                                }

                                else -> R.color.light_gray
                            }
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(start = 17.dp, end = 16.dp),
                text = stringResource(R.string.txt_info_male),
                clickable = { viewModel.inputGender(MALE) }
            )
            Spacer(modifier = Modifier.height(10.dp))
            //여성버튼
            CustomGenderPick(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(
                        colorResource(
                            when (gender) {
                                MALE -> {
                                    R.color.light_gray
                                }

                                FEMALE -> {
                                    R.color.light_lavender
                                }

                                else -> R.color.light_gray
                            }
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(start = 17.dp, end = 16.dp),
                text = stringResource(R.string.txt_info_female),
                clickable = { viewModel.inputGender(FEMALE) }
            )

            Spacer(modifier = Modifier.height(24.dp))
            CustomTextLittle(stringResource(R.string.txt_info_birth))
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CustomSpinnerBox(
                        text = stringResource(R.string.txt_info_year),
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
                        text = stringResource(R.string.txt_info_month),
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
                        text = stringResource(R.string.txt_info_day),
                        onclick = {
                            yearBottomSheet = false
                            monthBottomSheet = false
                            dayBottomSheet = true
                            cityBottomSheet = false
                            areaBottomSheet = false
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            //시군구
            CustomTextLittle(stringResource(R.string.txt_info_activate))
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CustomSpinnerBox(
                        text = stringResource(R.string.txt_info_city),
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
                        text = stringResource(R.string.txt_info_area),
                        onclick = {
                            yearBottomSheet = false
                            monthBottomSheet = false
                            dayBottomSheet = false
                            cityBottomSheet = false
                            areaBottomSheet = true
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Spacer(modifier = Modifier.weight(1f))

            CustomButton(
                stringResource(R.string.btn_finish),
                onclick = { buttonClick() },
                enable = true,
            )

            BottomSheetSelector(
                yearBottomSheet = yearBottomSheet,
                monthBottomSheet = monthBottomSheet,
                dayBottomSheet = dayBottomSheet,
                cityBottomSheet = cityBottomSheet,
                areaBottomSheet = areaBottomSheet,
                onSelect = {
                    println("선택된 값: $it")
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