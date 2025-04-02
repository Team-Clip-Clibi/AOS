package com.example.signup.ui.alreadySignUp

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.signup.R
import com.example.signup.SignUpViewModel
import com.example.signup.ui.component.CustomButton
import com.example.signup.ui.component.CustomContentText
import com.example.signup.ui.component.CustomTitleText
import com.example.signup.ui.component.ElementCategory
import com.example.signup.ui.component.ElementTitle
import com.example.signup.ui.component.ElementValue
import com.example.signup.ui.component.UnderLineText
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun AlreadySignUpScreenMain(
    paddingValues: PaddingValues,
    viewModel: SignUpViewModel,
    buttonClick: () -> Unit,
) {
    val userInfo by viewModel.userInfoState.collectAsState()
    val scrollState = rememberScrollState()
    LaunchedEffect(Unit) {
        viewModel.userInfoState.collectLatest { userInfoStaet ->
            when(val saveSignUp = userInfoStaet.setSignUpState){
                is SignUpViewModel.CheckState.StanBy -> {
                    Log.d(javaClass.name.toString() , "Stand by send Detail")
                }
                is SignUpViewModel.CheckState.ValueNotOkay ->{
                    Log.e(javaClass.name.toString() , saveSignUp.errorMessage)

                }
                is SignUpViewModel.CheckState.ValueOkay ->{
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
                top = paddingValues.calculateTopPadding() + 32.dp, bottom = 21.dp
            )
            .background(color = Color(0xFFFFFFFF))
            .verticalScroll(scrollState)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 17.dp, end = 16.dp)
        ) {
            CustomTitleText(
                text = stringResource(R.string.txt_signUp_title),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp))

            CustomContentText(
                text = stringResource(R.string.txt_signUp_content),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))

            ElementTitle(
                text = stringResource(R.string.txt_signUp_id),
                color = 0xFF171717
            )

            Spacer(modifier = Modifier.height(22.dp))
            //계정 Layout
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                ElementCategory(
                    text = stringResource(R.string.txt_signUp_name),
                    color = 0xFF171717,
                    modifier = Modifier.align(Alignment.CenterStart)
                )
                ElementValue(
                    text = userInfo.name.ifEmpty { "김성일" },
                    color = 0xFF666666,
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            //연동계정 Layout
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                ElementCategory(
                    text = stringResource(R.string.txt_signUp_account),
                    color = 0xFF171717,
                    modifier = Modifier.align(Alignment.CenterStart)
                )
                ElementValue(
                    text = userInfo.platform.ifEmpty{"카카오"},
                    color = 0xFF666666,
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            //가입날짜 Layout
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                ElementCategory(
                    text = stringResource(R.string.txt_signUp_day),
                    color = 0xFF171717,
                    modifier = Modifier.align(Alignment.CenterStart)
                )
                ElementValue(
                    text = userInfo.createdAt.ifEmpty{"2024.02.12"},
                    color = 0xFF666666,
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            HorizontalDivider(
                thickness = 1.dp,
                color = Color(0xFFEFEFEF)
            )
            Spacer(modifier = Modifier.height(10.dp))

            CustomButton(
                stringResource(R.string.btn_signUp),
                onclick = {
                    viewModel.setSignUp()
                },
                enable = true
            )

            Spacer(modifier = Modifier.height(18.dp))

            UnderLineText(
                text = stringResource(R.string.btn_signUp_not_mine),
                color = 0xFF666666,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clickable {
                        //TODO 아직 미구현 ㅠㅠ
                }
            )
        }
    }
}