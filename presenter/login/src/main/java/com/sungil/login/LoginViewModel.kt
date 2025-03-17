package com.sungil.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sungil.domain.useCase.GetToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val getToken: GetToken) : ViewModel() {
    private val _actionFlow = MutableSharedFlow<Action>()
    val actionFlow: SharedFlow<Action> = _actionFlow.asSharedFlow()

    fun getToken() {
        viewModelScope.launch {
            when (val result = getToken.invoke()) {
                is GetToken.Result.Success -> {
                    _actionFlow.emit(Action.GetSuccess(result.token))
                }

                is GetToken.Result.Fail -> {
                    _actionFlow.emit(Action.Error("The kakao id is null"))
                }
            }
        }
    }

    sealed interface Action {
        data class GetSuccess(val message: String) : Action
        data class Error(val errorMessage: String) : Action
    }
}