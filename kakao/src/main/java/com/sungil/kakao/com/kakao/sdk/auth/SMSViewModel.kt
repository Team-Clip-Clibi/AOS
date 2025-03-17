package com.sungil.kakao.com.kakao.sdk.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sungil.domain.useCase.SaveToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SMSViewModel @Inject constructor(private val saveToken: SaveToken) : ViewModel() {

    private val _actionFlow = MutableSharedFlow<Action>()
    val actionFlow: SharedFlow<Action> = _actionFlow.asSharedFlow()

    fun saveToken(token: String) {
        viewModelScope.launch {
            when (val result = saveToken.invoke(SaveToken.Param(token = token))) {
                is SaveToken.Result.Fail -> {
                    throw IllegalArgumentException(result.errorMessage)
                }

                is SaveToken.Result.Success -> {
                    _actionFlow.emit(Action.SaveSuccess(result.message))
                }
            }
        }
    }

    sealed interface Action {
        data class SaveSuccess(val message: String) : Action
    }
}