package com.sungil.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sungil.domain.useCase.CheckAlreadySignUp
import com.sungil.domain.useCase.GetFcmToken
import com.sungil.domain.useCase.GetKakaoId
import com.sungil.domain.useCase.RequestLogin
import com.sungil.domain.useCase.SetNotifyState
import com.sungil.domain.useCase.UpdateAndSaveToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
    private val notifyState: SetNotifyState,
    private val login: RequestLogin,
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
            when (val result = notifyState.invoke(SetNotifyState.Param(data))) {
                is SetNotifyState.Result.Success -> {
                    Log.d(javaClass.name.toString(), "Success ${result.message}")
                }

                is SetNotifyState.Result.Fail -> {
                    _actionFlow.emit(Action.Error(result.errorMessage))
                }
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

    fun requestLogin() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = login.invoke()) {
                is RequestLogin.Result.Success -> {
                    _actionFlow.emit(Action.Login(result.message))
                }

                is RequestLogin.Result.Fail -> {
                    _actionFlow.emit(Action.Error(result.errorMessage))
                }
            }
        }
    }

    sealed interface Action {
        data class GetSuccess(val message: String) : Action
        data class SignUp(val message: String) : Action
        data class NotSignUp(val message: String) : Action
        data class FCMToken(val message: String) : Action
        data class Login(val message: String) : Action
        data class Error(val errorMessage: String) : Action
    }
}