package com.clip.signup


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clip.signup.model.TermItem
import com.clip.domain.useCase.CheckAlreadySignUpNumber
import com.clip.domain.useCase.CheckInPutNickName
import com.clip.domain.useCase.CheckNameOkay
import com.clip.domain.useCase.CheckNickName
import com.clip.domain.useCase.SendAlreadySignUp
import com.clip.domain.useCase.SendTermData
import com.clip.domain.useCase.SendFirstNickName
import com.clip.domain.useCase.SendUserDetail
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
    private val checkNumber: CheckAlreadySignUpNumber,
    private val checkNickName: CheckNickName,
    private val checkInPutNickName: CheckInPutNickName,
    private val checkName: CheckNameOkay,
    private val sendTermData: SendTermData,
    private val detail: SendUserDetail,
    private val saveSignUp: SendAlreadySignUp,
    private val sendNickName : SendFirstNickName
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

    private fun updateStepState(step: SignUpStep, state: SignUpStepState) {
        _userInfoState.update {
            it.copy(stepStates = it.stepStates + (step to state))
        }
    }

    fun changeTermItem(termItem: TermItem) {
        val updateList = _termItem.value.map {
            if (it.termName == termItem.termName) termItem else it
        }
        _termItem.value = updateList
    }

    fun inputNickName(data: String) {
        _userInfoState.update { it.copy(nickName = data) }
        viewModelScope.launch {
            when (val result = checkInPutNickName.invoke(CheckInPutNickName.Param(data))) {
                is CheckInPutNickName.Result.Fail -> updateStepState(
                    SignUpStep.NICKNAME,
                    SignUpStepState.Error(result.errorMessage)
                )

                is CheckInPutNickName.Result.Success -> updateStepState(
                    SignUpStep.NICKNAME,
                    SignUpStepState.Success(result.message)
                )
            }
        }
    }

    fun checkNickName() {
        viewModelScope.launch {
            updateStepState(SignUpStep.NICKNAME, SignUpStepState.Loading)
            when (val result = checkNickName.invoke(CheckNickName.Param(_userInfoState.value.nickName))) {
                is CheckNickName.Result.Success -> updateStepState(SignUpStep.NICKNAME, SignUpStepState.Success(result.message))
                is CheckNickName.Result.Fail -> updateStepState(SignUpStep.NICKNAME, SignUpStepState.Error(result.message))
            }
        }
    }
    fun sendNickName(){
        viewModelScope.launch {
            when(val result = sendNickName.invoke(SendFirstNickName.Param(_userInfoState.value.nickName))){
                is SendFirstNickName.Result.Fail -> updateStepState(SignUpStep.NICKNAME, SignUpStepState.Error(result.errorMessage))
                is SendFirstNickName.Result.Success -> updateStepState(SignUpStep.NICKNAME, SignUpStepState.Success(result.message))
            }
        }
    }

    fun inputName(data: String) {
        _userInfoState.update { it.copy(name = data) }
    }

    fun checkName() {
        viewModelScope.launch {
            updateStepState(SignUpStep.NAME, SignUpStepState.Loading)
            when (val result = checkName.invoke(CheckNameOkay.Param(_userInfoState.value.name))) {
                is CheckNameOkay.Result.Success -> updateStepState(SignUpStep.NAME, SignUpStepState.Success(result.message))
                is CheckNameOkay.Result.Fail -> updateStepState(SignUpStep.NAME, SignUpStepState.Error(result.errorMessage))
            }
        }
    }

    fun inputPhoneNumber(number: String) {
        _userInfoState.update { it.copy(phoneNumber = number) }
    }

    fun checkSignUpNumber() {
        viewModelScope.launch(Dispatchers.IO) {
            updateStepState(SignUpStep.PHONE, SignUpStepState.Loading)
            when (val result = checkNumber.invoke(CheckAlreadySignUpNumber.Param(_userInfoState.value.phoneNumber))) {
                is CheckAlreadySignUpNumber.Result.Success -> {
                    updateStepState(SignUpStep.PHONE, SignUpStepState.Success(result.message))
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
                    updateStepState(SignUpStep.PHONE, SignUpStepState.Error(result.errorMessage))
                }
            }
        }
    }

    fun sendTerm() {
        viewModelScope.launch {
            updateStepState(SignUpStep.TERM, SignUpStepState.Loading)
            val result = sendTermData.invoke(
                SendTermData.Param(
                    termServicePermission = _termItem.value.firstOrNull { it.termName == "servicePermission" }?.checked ?: false,
                    privatePermission = _termItem.value.firstOrNull { it.termName == "privatePermission" }?.checked ?: false,
                    marketingPermission = _termItem.value.firstOrNull { it.termName == "marketingPermission" }?.checked ?: false
                )
            )
            when (result) {
                is SendTermData.Result.Success -> updateStepState(SignUpStep.TERM, SignUpStepState.Success(result.message))
                is SendTermData.Result.Fail -> updateStepState(SignUpStep.TERM, SignUpStepState.Error(result.errorMessage))
            }
        }
    }

    fun sendDetail() {
        viewModelScope.launch(Dispatchers.IO) {
            updateStepState(SignUpStep.INFO, SignUpStepState.Loading)
            val user = _userInfoState.value
            val result = detail.invoke(
                SendUserDetail.Param(
                    gender = user.gender,
                    birthYear = user.birthYear,
                    birthMonth = user.birthMonth,
                    birthDay = user.birtDay,
                    city = user.cityRealValue,
                    county = user.areaRealValue,
                    cityDisplayName = user.city,
                    countyDisplayName = user.area,
                    marketingPermission = _termItem.value.firstOrNull { it.termName == "marketingPermission" }?.checked ?: false,
                    name = user.name,
                    nickName = user.nickName,
                    phoneNumber = user.phoneNumber
                )
            )
            when (result) {
                is SendUserDetail.Result.Success -> updateStepState(SignUpStep.INFO, SignUpStepState.Success(result.message))
                is SendUserDetail.Result.Fail -> updateStepState(SignUpStep.INFO, SignUpStepState.Error(result.errorMessage))
            }
        }
    }

    fun setSignUp() {
        viewModelScope.launch {
            updateStepState(SignUpStep.ALREADY_SIGNED_UP, SignUpStepState.Loading)
            when (val result = saveSignUp.invoke()) {
                is SendAlreadySignUp.Result.Success -> updateStepState(SignUpStep.ALREADY_SIGNED_UP, SignUpStepState.Success(result.message))
                is SendAlreadySignUp.Result.Fail -> updateStepState(SignUpStep.ALREADY_SIGNED_UP, SignUpStepState.Error(result.errorMessage))
            }
        }
    }

    fun setBirthYear(value: String) = _userInfoState.update { it.copy(birthYear = value) }
    fun setBirthMonth(value: String) = _userInfoState.update { it.copy(birthMonth = value) }
    fun setBirthDay(value: String) = _userInfoState.update { it.copy(birtDay = value) }
    fun setCity(value: String) {
        _userInfoState.update { it.copy(cityRealValue = City.fromDisplayName(value).toString()) }
        _userInfoState.update { it.copy(city = value) }
    }
    fun setArea(value: String) {
        _userInfoState.update { it.copy(areaRealValue = County.fromDisplayName(value).toString()) }
        _userInfoState.update { it.copy(area = value) }
    }
    fun inputGender(data: String) = _userInfoState.update { it.copy(gender = data) }
}

enum class SignUpStep {
    TERM, PHONE, NAME, NICKNAME, INFO, ALREADY_SIGNED_UP
}

sealed class SignUpStepState {
    data object Loading : SignUpStepState()
    data class Success(val message: String = "") : SignUpStepState()
    data class Error(val message: String) : SignUpStepState()
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
    val stepStates: Map<SignUpStep, SignUpStepState> = emptyMap()
)