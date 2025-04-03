package com.sungil.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sungil.domain.model.UserInfoUseCase
import com.sungil.domain.useCase.GetUserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val userInfo: GetUserInfo) : ViewModel() {
    private val _mainViewState = MutableSharedFlow<MainState>()
    val mainViewState: SharedFlow<MainState> = _mainViewState.asSharedFlow()


    init {
        requestUserInfo()
    }

    private fun requestUserInfo() {
        viewModelScope.launch {
            when (val result = userInfo.invoke()) {
                is GetUserInfo.Result.Success -> {
                    _mainViewState.emit(MainState.SuccessUserInfo(result.data))
                }

                is GetUserInfo.Result.Fail -> {
                    _mainViewState.emit(MainState.Error(result.errorMessage))
                }
            }
        }
    }

    sealed interface MainState {
        data class Loading(val message: String) : MainState
        data class SuccessUserInfo(val data: UserInfoUseCase) : MainState
        data class Error(val errorMessage: String) : MainState
    }
}
