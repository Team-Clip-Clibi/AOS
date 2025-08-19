package com.example.signup.ui.detail

import androidx.compose.foundation.background
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core.AppTextStyles
import com.example.core.ButtonL
import com.example.core.ColorStyle
import com.example.signup.FEMALE
import com.example.signup.MALE
import com.example.signup.NON_BINARY
import com.example.signup.R
import com.example.signup.SignUpViewModel
import com.example.signup.ui.component.BottomSheetSelector
import com.example.signup.ui.component.CustomSpinnerBox

@Composable
internal fun DetailView(
    viewModel: SignUpViewModel,
) {
    var yearBottomSheet by remember { mutableStateOf(false) }
    var dayBottomSheet by remember { mutableStateOf(false) }
    var monthBottomSheet by remember { mutableStateOf(false) }
    var cityBottomSheet by remember { mutableStateOf(false) }
    var areaBottomSheet by remember { mutableStateOf(false) }
    val userInfo by viewModel.userInfoState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = ColorStyle.WHITE_100)
            .padding(top = 32.dp, start = 17.dp, end = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = stringResource(R.string.txt_info_title),
            style = AppTextStyles.SUBTITLE_16_24_SEMI,
            color = ColorStyle.GRAY_600,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = stringResource(R.string.txt_info_sub_title),
            style = AppTextStyles.HEAD_28_40_BOLD,
            color = ColorStyle.GRAY_800,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = stringResource(R.string.txt_info_gender),
            style = AppTextStyles.BODY_14_20_MEDIUM,
            color = ColorStyle.GRAY_800,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))
        ButtonL(
            text = stringResource(R.string.txt_info_male),
            isSelected = userInfo.gender == MALE,
            onClick = {
                viewModel.inputGender(MALE)
            },
            borderUse = userInfo.gender == MALE,
            borderColor = if (userInfo.gender == MALE) ColorStyle.PURPLE_100 else ColorStyle.GRAY_100,
            textCenter = false
        )
        Spacer(modifier = Modifier.height(10.dp))
        ButtonL(
            text = stringResource(R.string.txt_info_female),
            isSelected = userInfo.gender == FEMALE,
            onClick = {
                viewModel.inputGender(FEMALE)
            },
            borderUse = userInfo.gender == FEMALE,
            borderColor = if (userInfo.gender == FEMALE) ColorStyle.PURPLE_100 else ColorStyle.GRAY_100,
            textCenter = false
        )
        Spacer(modifier = Modifier.height(10.dp))
        ButtonL(
            text = stringResource(R.string.txt_info_non_sex),
            isSelected = userInfo.gender == NON_BINARY,
            onClick = {
                viewModel.inputGender(NON_BINARY)
            },
            borderUse = userInfo.gender == NON_BINARY,
            borderColor = if (userInfo.gender == NON_BINARY) ColorStyle.PURPLE_100 else ColorStyle.GRAY_100,
            textCenter = false
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = stringResource(R.string.txt_info_birth),
            style = AppTextStyles.BODY_14_20_MEDIUM,
            color = ColorStyle.GRAY_800,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
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
        Text(
            text = stringResource(R.string.txt_info_activate),
            style = AppTextStyles.BODY_14_20_MEDIUM,
            color = ColorStyle.GRAY_800,
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
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
