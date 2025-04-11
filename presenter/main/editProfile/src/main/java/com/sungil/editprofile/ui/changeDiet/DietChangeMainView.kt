package com.sungil.editprofile.ui.changeDiet

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.sungil.editprofile.DIET
import com.sungil.editprofile.ERROR_FAIL_SAVE
import com.sungil.editprofile.ERROR_FAIL_TO_UPDATE_LOVE
import com.sungil.editprofile.ERROR_TOKEN_NULL
import com.sungil.editprofile.ProfileEditViewModel
import com.sungil.editprofile.R
import com.sungil.editprofile.ui.CustomButton
import com.sungil.editprofile.ui.CustomChangeDataTextField
import com.sungil.editprofile.ui.CustomItemPick

@Composable
internal fun DietChangeMainView(
    viewModel: ProfileEditViewModel,
    paddingValues: PaddingValues,
    snackBarHost: SnackbarHostState,
) {
    val diet by viewModel.dietData.collectAsState()
    var inputValue by remember { mutableStateOf("") }
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.editProfileState.collect{ state ->
            when(state){
                is ProfileEditViewModel.EditProfileState.SuccessToChange -> {
                    snackBarHost.showSnackbar(
                        message = context.getString(R.string.msg_save_success),
                        duration = SnackbarDuration.Short
                    )
                    viewModel.initFlow()
                }
                is ProfileEditViewModel.EditProfileState.Error ->{
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
                    viewModel.initFlow()
                }
                else -> Unit
            }
        }
    }
    val scrollState = rememberScrollState()
    Box(
        Modifier
            .background(Color(0xFFFFFFFF))
            .fillMaxSize()
            .padding(top = paddingValues.calculateTopPadding() + 32.dp, bottom = 8.dp)
            .navigationBarsPadding()
            .verticalScroll(scrollState)
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .align(Alignment.TopCenter)
                .padding(start = 17.dp, end = 16.dp)
        ) {
            CustomItemPick(
                modifier = Modifier.dietItemModifier((diet.name == DIET.VG.name)),
                text = stringResource(R.string.txt_diet_vg),
                clickable = { viewModel.setDiet(DIET.VG) }
            )
            Spacer(Modifier.height(10.dp))

            CustomItemPick(
                modifier = Modifier.dietItemModifier((diet.name == DIET.VT.name)),
                text = stringResource(R.string.txt_diet_vt),
                clickable = { viewModel.setDiet(DIET.VT) }
            )
            Spacer(Modifier.height(10.dp))

            CustomItemPick(
                modifier = Modifier.dietItemModifier((diet.name == DIET.GF.name)),
                text = stringResource(R.string.txt_diet_gf),
                clickable = { viewModel.setDiet(DIET.GF) }
            )

            Spacer(Modifier.height(10.dp))

            CustomItemPick(
                modifier = Modifier.dietItemModifier((diet.name == DIET.ETC.name)),
                text = stringResource(R.string.txt_diet_etc),
                clickable = { viewModel.setDiet(DIET.ETC) }
            )
            Spacer(modifier = Modifier.height(10.dp))

            HorizontalDivider(thickness = 1.dp, color = Color(0xFFEFEFEF))

            Spacer(modifier = Modifier.height(10.dp))

            if (diet.name == DIET.ETC.name) {
                CustomChangeDataTextField(
                    beforeText = inputValue,
                    inputType = KeyboardType.Text,
                    onValueChange = { data ->
                        inputValue = data
                    }
                )
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .background(Color(0xFFFFFFFF))
                .fillMaxWidth()
        ) {
            HorizontalDivider(thickness = 1.dp, color = Color(0xFFEFEFEF))
            Spacer(modifier = Modifier.height(8.dp))
            CustomButton(
                stringResource(R.string.btn_finish),
                onclick = {
                    viewModel.updateDiet()
                },
                enable = when {
                    diet.name == DIET.NONE.name -> false
                    diet.name == DIET.ETC.name && inputValue.isBlank() -> false
                    else -> true
                }
            )
        }

    }
}

fun Modifier.dietItemModifier(isSelected: Boolean): Modifier {
    return this
        .fillMaxWidth()
        .height(48.dp)
        .background(
            color = if (isSelected) Color(0xFFF9F0FF) else Color(0xFFF7F7F7),
            shape = RoundedCornerShape(8.dp)
        )
        .let {
            if (isSelected) {
                it.border(
                    width = 1.dp,
                    color = Color(0xFFD3ADF7),
                    shape = RoundedCornerShape(12.dp)
                )
            } else it
        }
}