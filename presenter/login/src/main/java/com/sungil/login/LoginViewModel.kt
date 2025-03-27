package com.sungil.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sungil.domain.useCase.CheckAlreadySignUp
import com.sungil.domain.useCase.GetFcmToken
import com.sungil.domain.useCase.GetKakaoId
import com.sungil.domain.useCase.UpdateAndSaveToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getKakaoId: GetKakaoId,
    private val signUp: CheckAlreadySignUp,
    private val fcm: GetFcmToken,
    private val updateAndSaveToken: UpdateAndSaveToken,
) : ViewModel() {
    private val _actionFlow = MutableSharedFlow<Action>()
    val actionFlow: SharedFlow<Action> = _actionFlow.asSharedFlow()

    fun getKAKAOId() {
        viewModelScope.launch {
            when (val result = getKakaoId.invoke()) {
                is GetKakaoId.Result.Success -> {
                    _actionFlow.emit(Action.GetSuccess(result.kakaoId))
                }

                is GetKakaoId.Result.Fail -> {
                    _actionFlow.emit(Action.Error("The kakao id is null"))
                }
            }
        }
    }

    fun checkSignUp() {
        viewModelScope.launch {
            when (val result = signUp.invoke()) {
                is CheckAlreadySignUp.Result.Success -> {
                    _actionFlow.emit(Action.SignUp(result.message))
                }

                is CheckAlreadySignUp.Result.Fail -> {
                    _actionFlow.emit(Action.NotSignUp(result.errorMessage))
                }
            }
        }
    }

    fun setNotification(data: Boolean) {
        viewModelScope.launch {
            if (!data) {
                _actionFlow.emit(Action.Error("error permission"))
                return@launch
            }
            getToken()
        }
    }

    private fun getToken() {
        viewModelScope.launch {
            val result = fcm.invoke()
            if (result == ERROR_FCM_TOKEN) {
                _actionFlow.emit(Action.Error(ERROR_FCM_TOKEN))
                return@launch
            }
            when (val saveOrUpdateToken =
                updateAndSaveToken.invoke(UpdateAndSaveToken.Param(result))) {
                is UpdateAndSaveToken.Result.Success -> {
                    _actionFlow.emit(Action.FCMToken(saveOrUpdateToken.message))
                }

                is UpdateAndSaveToken.Result.Fail -> {
                    _actionFlow.emit(Action.Error(ERROR_FCM_TOKEN))
                }
            }
        }
    }

    sealed interface Action {
        data class GetSuccess(val message: String) : Action
        data class SignUp(val message: String) : Action
        data class NotSignUp(val message: String) : Action
        data class FCMToken(val message: String) : Action
        data class Error(val errorMessage: String) : Action
    }
}