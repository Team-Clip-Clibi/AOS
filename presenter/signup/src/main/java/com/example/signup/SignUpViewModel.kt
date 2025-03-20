package com.example.signup

import androidx.annotation.NonUiContext
import androidx.compose.runtime.mutableStateOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.signup.model.TermItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor() : ViewModel() {
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
    val phoneNumber : StateFlow<String> = _phoneNumber.asStateFlow()

    private val _smsCode = MutableStateFlow("")
    val smsCode : StateFlow<String> = _smsCode.asStateFlow()

    private val _smsViewShow = MutableStateFlow(false)
    val smsViewShow : StateFlow<Boolean> = _smsViewShow.asStateFlow()
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

    fun inputPhoneNumber(number : String){
        _phoneNumber.value = number
    }

    fun signCodeNumber(number : String){
        _smsCode.value = number
    }

    fun smsRequest(phoneNumber : String){
        _smsViewShow.value = true
    }

    fun checkSmsNumber(smsNumber : String){

    }

}