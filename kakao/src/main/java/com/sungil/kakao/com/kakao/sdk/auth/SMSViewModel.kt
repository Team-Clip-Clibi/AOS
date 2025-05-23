package com.sungil.kakao.com.kakao.sdk.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sungil.domain.useCase.CheckAlreadySignUp
import com.sungil.domain.useCase.RequestLogin
import com.sungil.domain.useCase.SaveKaKaoId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SMSViewModel @Inject constructor(
    private val saveToken: SaveKaKaoId,
    private val signUp: CheckAlreadySignUp,
    private val login: RequestLogin,
) : ViewModel() {

    private val _actionFlow = MutableSharedFlow<Action>()
    val actionFlow: SharedFlow<Action> = _actionFlow.asSharedFlow()

    fun saveKaKaoId(kakaoId: String) {
        viewModelScope.launch {
            when (val result = saveToken.invoke(SaveKaKaoId.Param(token = kakaoId))) {
                is SaveKaKaoId.Result.Fail -> {
                    throw IllegalArgumentException(result.errorMessage)
                }

                is SaveKaKaoId.Result.Success -> {
                    _actionFlow.emit(Action.SaveSuccess(result.token))
                }
            }
        }
    }

    fun checkAlreadySignUp(socialId: String) {
        viewModelScope.launch {
            when (val result = signUp.invoke(CheckAlreadySignUp.Param(socialId))) {
                is CheckAlreadySignUp.Result.Success -> {
                    _actionFlow.emit(Action.AlreadySignUp(result.message))
                }

                is CheckAlreadySignUp.Result.Fail -> {
                    _actionFlow.emit(Action.NotSignUp(result.errorMessage))
                }
            }
        }
    }


    sealed interface Action {
        data class SaveSuccess(val kakaoId: String) : Action
        data class AlreadySignUp(val message: String) : Action
        data class NotSignUp(val message: String) : Action
        data class Error(val errorMessage: String) : Action
    }
}