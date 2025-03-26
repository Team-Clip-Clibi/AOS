package com.example.signup

import android.app.Activity
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.signup.model.TermItem
import com.sungil.domain.useCase.CheckAlreadySignUpNumber
import com.sungil.domain.useCase.GetFirebaseSMSState
import com.sungil.domain.useCase.GetSMSTime
import com.sungil.domain.useCase.RequestSMS
import com.sungil.domain.useCase.SendCode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val sms: RequestSMS,
    private val code: SendCode,
    private val firebaseSMS: GetFirebaseSMSState,
    private val timer: GetSMSTime,
    private val checkNumber: CheckAlreadySignUpNumber,
) : ViewModel() {
    private val _termItem = MutableStateFlow(
        listOf(
            TermItem("allChecked", true, isAllCheck = true),
            TermItem("serviceChecked", true),
            TermItem("collectPerson", true),
            TermItem("marketing", false)
        )
    )
    val termItem: StateFlow<List<TermItem>> = _termItem.asStateFlow()

    private val _phoneNumber = MutableStateFlow("")
    val phoneNumber: StateFlow<String> = _phoneNumber.asStateFlow()

    private val _smsCode = MutableStateFlow("")
    val smsCode: StateFlow<String> = _smsCode.asStateFlow()

    private val _smsViewShow = MutableStateFlow(false)
    val smsViewShow: StateFlow<Boolean> = _smsViewShow.asStateFlow()

    private val _firebaseSMSState = MutableStateFlow<Action>(Action.LoadingRequestSMS("Loading"))
    val firebaseSMSState: StateFlow<Action> = _firebaseSMSState.asStateFlow()

    private val _smsTime = MutableStateFlow("0")
    val smsTime: StateFlow<String> = _smsTime.asStateFlow()

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name.asStateFlow()

    private val _nickName = MutableStateFlow("")
    val nickName: StateFlow<String> = _nickName.asStateFlow()

    private val _gender = MutableStateFlow(MALE)
    val gender: StateFlow<String> = _gender.asStateFlow()

    fun smsRequest(phoneNumber: String, activity: Activity) {
        _smsViewShow.value = true
        viewModelScope.launch {
            sms.invoke(RequestSMS.Param(phoneNumber, activity))
            _firebaseSMSState.value = Action.SuccessRequestSMS("start to request sms")
            collectFirebaseSMSState()
            startSMSTimer()
        }
    }

    fun sendCode(verifyCode: String) {
        viewModelScope.launch {
            code.invoke(SendCode.Param(verifyCode))
        }
    }

    private fun checkAlreadySignUpNumber() {
        viewModelScope.launch(Dispatchers.IO) {
            val phoneNumber: String = _phoneNumber.value
            when (val result = checkNumber.invoke(CheckAlreadySignUpNumber.Param(phoneNumber))) {
                is CheckAlreadySignUpNumber.Result.Success -> {
                    _firebaseSMSState.value = Action.VerifyFinish(result.message)
                }

                is CheckAlreadySignUpNumber.Result.Fail -> {
                    _firebaseSMSState.value = Action.Error(result.errorMessage)
                }
            }
        }
    }

    private fun collectFirebaseSMSState() {
        viewModelScope.launch {
            firebaseSMS.invoke()
                .catch { e -> e.printStackTrace() }
                .collect { state ->
                    when (state) {
                        "Success" -> {
                            _firebaseSMSState.value =
                                Action.SuccessVerifySMS("Verification successful")
                            checkAlreadySignUpNumber()
                        }

                        "Error" -> {
                            _firebaseSMSState.value = Action.FailVerifySMS("Verification failed")
                        }
                    }
                }
        }
    }

    private fun startSMSTimer() {
        viewModelScope.launch {
            timer.invoke().collect { time ->
                val min = time / 60
                val sec = time % 60
                val formatted = String.format("%d:%02d", min, sec)
                _smsTime.value = formatted.trim()

                if (formatted == "0:00") {
                    _firebaseSMSState.value = Action.Error("error")
                    this.cancel()
                }
            }
        }
    }

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

    fun inputPhoneNumber(number: String) {
        _phoneNumber.value = number
    }

    fun signCodeNumber(number: String) {
        _smsCode.value = number
    }

    fun inputName(data: String) {
        _name.value = data
    }

    fun inputNickName(data: String) {
        _nickName.value = data
    }

    fun inputGender(data: String) {
        _gender.value = data
    }

    sealed interface Action {
        data class LoadingRequestSMS(val message: String) : Action
        data class SuccessRequestSMS(val message: String) : Action
        data class SuccessVerifySMS(val message: String) : Action
        data class VerifyFinish(val message: String) : Action
        data class FailVerifySMS(val message: String) : Action
        data class Error(val message: String) : Action
    }

}