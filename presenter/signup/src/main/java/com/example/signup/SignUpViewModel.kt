package com.example.signup

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.signup.model.TermItem
import com.sungil.domain.useCase.CheckAlreadySignUpNumber
import com.sungil.domain.useCase.CheckNameOkay
import com.sungil.domain.useCase.CheckNickName
import com.sungil.domain.useCase.GetFirebaseSMSState
import com.sungil.domain.useCase.GetSMSTime
import com.sungil.domain.useCase.RequestSMS
import com.sungil.domain.useCase.SendAlreadySignUp
import com.sungil.domain.useCase.SendTermData
import com.sungil.domain.useCase.SendCode
import com.sungil.domain.useCase.SendUserDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val sms: RequestSMS,
    private val code: SendCode,
    private val firebaseSMS: GetFirebaseSMSState,
    private val timer: GetSMSTime,
    private val checkNumber: CheckAlreadySignUpNumber,
    private val checkNickName: CheckNickName,
    private val checkName: CheckNameOkay,
    private val sendTermData: SendTermData,
    private val detail: SendUserDetail,
    private val saveSignUp: SendAlreadySignUp,
) : ViewModel() {
    private val _termItem = MutableStateFlow(
        listOf(
            TermItem("allChecked", true, isAllCheck = true),
            TermItem("servicePermission", true),
            TermItem("privatePermission", true),
            TermItem("marketingPermission", false)
        )
    )
    val termItem: StateFlow<List<TermItem>> = _termItem.asStateFlow()

    private val _userInfoState = MutableStateFlow(UserInfoState())
    val userInfoState: StateFlow<UserInfoState> = _userInfoState

    /**
     * Set function
     */
    fun changeTermItem(termItem: TermItem) {
        val updateList = _termItem.value.map {
            if (it.termName == termItem.termName) {
                termItem
            } else {
                it
            }
        }
        _termItem.value = updateList
    }

    /**
     * 닉네임 중복 확인 및 입력
     */
    fun checkNickName(data: String) {
        viewModelScope.launch {
            when (val result = checkNickName.invoke(CheckNickName.Param(data))) {
                is CheckNickName.Result.Success -> {
                    _userInfoState.update { it.copy(nickCheckStanBy = CheckState.ValueOkay(result.message)) }
                }

                is CheckNickName.Result.Fail -> {
                    _userInfoState.update { it.copy(nickCheckStanBy = CheckState.ValueNotOkay(result.message)) }
                }
            }
        }
    }

    /**
     * 이름 양식 확인 및입력
     */
    fun checkName(data: String) {
        viewModelScope.launch {
            when (val result = checkName.invoke(CheckNameOkay.Param(data))) {
                is CheckNameOkay.Result.Success -> {
                    _userInfoState.update { it.copy(nameCheck = CheckState.ValueOkay(result.message)) }
                }

                is CheckNameOkay.Result.Fail -> {
                    _userInfoState.update { it.copy(nameCheck = CheckState.ValueNotOkay(result.errorMessage)) }
                }
            }
        }
    }

    /**
     * 동의항목 전송
     */
    fun sendTerm() {
        viewModelScope.launch {
            val result = sendTermData.invoke(
                SendTermData.Param(
                    termServicePermission = _termItem.value.firstOrNull { it.termName == "servicePermission" }?.checked
                        ?: false,
                    privatePermission = _termItem.value.firstOrNull { it.termName == "privatePermission" }?.checked
                        ?: false,
                    marketingPermission = _termItem.value.firstOrNull { it.termName == "marketingPermission" }?.checked
                        ?: false,
                )
            )

            when (result) {
                is SendTermData.Result.Success -> {
                    Log.d("SignUp", "회원가입 성공: ${result.message}")
                    _userInfoState.update { it.copy(termSendState = CheckState.ValueOkay(result.message)) }
                }

                is SendTermData.Result.Fail -> {
                    Log.e("SignUp", "회원가입 실패: ${result.errorMessage}")
                    _userInfoState.update { it.copy(termSendState = CheckState.ValueNotOkay(result.errorMessage)) }
                }
            }
        }
    }

    /**
     * 이미 가입된 번호인지 확인
     */
    fun checkSignUpNumber() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result =
                checkNumber.invoke(CheckAlreadySignUpNumber.Param(_userInfoState.value.phoneNumber))) {
                is CheckAlreadySignUpNumber.Result.Success -> {
                    _userInfoState.update {
                        it.copy(
                            phoneNumberCheckState = CheckState.ValueOkay(
                                result.message
                            )
                        )
                    }
                }

                is CheckAlreadySignUpNumber.Result.Fail -> {
                    if (result.errorMessage == ERROR_ALREADY_SIGN_UP) {
                        _userInfoState.update {
                            it.copy(
                                name = result.userName,
                                platform = result.platform,
                                createdAt = result.createdAt
                            )
                        }
                    }
                    _userInfoState.update {
                        it.copy(
                            phoneNumberCheckState = CheckState.ValueNotOkay(
                                result.errorMessage
                            )
                        )
                    }
                }
            }
        }
    }

    /**
     * 자세한 회원정보 입력
     */
    fun sendDetail() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = detail.invoke(
                SendUserDetail.Param(
                    gender = _userInfoState.value.gender,
                    birthYear = _userInfoState.value.birthYear,
                    birthMonth = _userInfoState.value.birthMonth,
                    birthDay = _userInfoState.value.birtDay,
                    city = _userInfoState.value.cityRealValue,
                    county = _userInfoState.value.areaRealValue,
                    cityDisplayName = _userInfoState.value.city,
                    countyDisplayName = _userInfoState.value.area,
                    servicePermission = _termItem.value.firstOrNull { it.termName == "servicePermission" }?.checked
                        ?: false,
                    privatePermission = _termItem.value.firstOrNull { it.termName == "privatePermission" }?.checked
                        ?: false,
                    marketingPermission = _termItem.value.firstOrNull { it.termName == "marketingPermission" }?.checked
                        ?: false,
                    name = _userInfoState.value.name,
                    nickName = _userInfoState.value.nickName,
                    phoneNumber = _userInfoState.value.phoneNumber
                )
            )) {
                is SendUserDetail.Result.Success -> {
                    _userInfoState.update { it.copy(detailState = CheckState.ValueOkay(result.message)) }
                }

                is SendUserDetail.Result.Fail -> {
                    _userInfoState.update { it.copy(detailState = CheckState.ValueNotOkay(result.errorMessage)) }
                }
            }
        }
    }

    /**
     * 이미 가입된 번호가 있을 시 회원가입 설정 후 out
     */
    fun setSignUp() {
        viewModelScope.launch {
            when (val result = saveSignUp.invoke()) {
                is SendAlreadySignUp.Result.Success -> {
                    Log.d(javaClass.name.toString(), result.message)
                    _userInfoState.update { it.copy(setSignUpState = CheckState.ValueOkay(result.message)) }
                }

                is SendAlreadySignUp.Result.Fail -> {
                    Log.e(javaClass.name.toString(), result.errorMessage)
                    _userInfoState.update { it.copy(setSignUpState = CheckState.ValueOkay(result.errorMessage)) }
                }
            }
        }
    }


    fun setBirthYear(value: String) {
        _userInfoState.update { it.copy(birthYear = value) }
    }

    fun setBirthMonth(value: String) {
        _userInfoState.update { it.copy(birthMonth = value) }
    }

    fun setBirthDay(value: String) {
        _userInfoState.update { it.copy(birtDay = value) }
    }

    fun setCity(value: String) {
        _userInfoState.update { it.copy(cityRealValue = City.fromDisplayName(value).toString()) }
        _userInfoState.update { it.copy(city = value) }
    }

    fun setArea(value: String) {
        _userInfoState.update { it.copy(areaRealValue = County.fromDisplayName(value).toString()) }
        _userInfoState.update { it.copy(area = value) }
    }

    fun inputPhoneNumber(number: String) {
        _userInfoState.update { it.copy(phoneNumber = number) }
    }

    fun inputName(data: String) {
        _userInfoState.update { it.copy(name = data) }
    }

    fun inputNickName(data: String) {
        _userInfoState.update { it.copy(nickName = data) }
    }

    fun inputGender(data: String) {
        _userInfoState.update { it.copy(gender = data) }
    }

    fun resetNickName() {
        viewModelScope.launch {
            _userInfoState.update { it.copy(nickCheckStanBy = CheckState.StanBy(STANDBY)) }
        }
    }

    fun resetName() {
        _userInfoState.update { it.copy(nameCheck = CheckState.StanBy(STANDBY)) }
    }

    fun resetTermData() {
        _userInfoState.update { it.copy(termSendState = CheckState.StanBy(STANDBY)) }
    }

    fun resetPhoneNumberState() {
        _userInfoState.update { it.copy(phoneNumberCheckState = CheckState.StanBy(STANDBY)) }
    }


    sealed interface CheckState {
        data class StanBy(val message: String) : CheckState
        data class ValueNotOkay(val errorMessage: String) : CheckState
        data class ValueOkay(val message: String) : CheckState
    }
}

data class UserInfoState(
    val name: String = "",
    val nickName: String = "",
    val gender: String = MALE,
    val birthYear: String = "",
    val birthMonth: String = "",
    val birtDay: String = "",
    val city: String = "",
    val cityRealValue: String = "",
    val area: String = "",
    val areaRealValue: String = "",
    val phoneNumber: String = "",
    val platform: String = "",
    val createdAt: String = "",
    var nickCheckStanBy: SignUpViewModel.CheckState = SignUpViewModel.CheckState.StanBy(
        STANDBY
    ),
    var nameCheck: SignUpViewModel.CheckState = SignUpViewModel.CheckState.StanBy(
        STANDBY
    ),
    var phoneNumberCheckState: SignUpViewModel.CheckState = SignUpViewModel.CheckState.StanBy(
        STANDBY
    ),
    var termSendState: SignUpViewModel.CheckState = SignUpViewModel.CheckState.StanBy(
        STANDBY
    ),
    var detailState: SignUpViewModel.CheckState = SignUpViewModel.CheckState.StanBy(
        STANDBY
    ),
    var setSignUpState: SignUpViewModel.CheckState = SignUpViewModel.CheckState.StanBy(
        STANDBY
    ),
)