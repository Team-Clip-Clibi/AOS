package com.sungil.kakao.com.kakao.sdk.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sungil.domain.useCase.CheckAlreadySignUp
import com.sungil.domain.useCase.SaveKaKaoId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SMSViewModel @Inject constructor(
    private val saveToken: SaveKaKaoId,
    private val signUp: CheckAlreadySignUp,
) : ViewModel() {

    private val _uiState = MutableLiveData<Action>()
    val uiState: LiveData<Action> = _uiState

    fun saveKaKaoId(kakaoId: String) {
        viewModelScope.launch {
            when (val result = saveToken.invoke(SaveKaKaoId.Param(token = kakaoId))) {
                is SaveKaKaoId.Result.Fail -> {
                    _uiState.postValue(Action.Error(result.errorMessage))
                }

                is SaveKaKaoId.Result.Success -> {
                    _uiState.postValue(Action.SaveSuccess(result.token))
                }
            }
        }
    }

    fun checkAlreadySignUp(socialId: String) {
        viewModelScope.launch {
            when (val result = signUp.invoke(CheckAlreadySignUp.Param(socialId))) {
                is CheckAlreadySignUp.Result.Success -> {
                    _uiState.postValue(Action.AlreadySignUp(result.message))
                }

                is CheckAlreadySignUp.Result.Fail -> {
                    _uiState.postValue(Action.NotSignUp(result.errorMessage))
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