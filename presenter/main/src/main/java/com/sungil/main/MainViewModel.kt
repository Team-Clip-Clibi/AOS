package com.sungil.main


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sungil.domain.model.UserInfo
import com.sungil.domain.useCase.GetUserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val userInfo: GetUserInfo) : ViewModel() {

    private val _userState = MutableStateFlow<UserUiState>(UserUiState.Loading)
    val userState: StateFlow<UserUiState> = _userState.asStateFlow()

    init {
        requestUserInfo()
    }

    private fun requestUserInfo() {
        viewModelScope.launch {
            when (val result = userInfo.invoke()) {
                is GetUserInfo.Result.Success -> {
                    _userState.value = UserUiState.Success(result.data)
                }

                is GetUserInfo.Result.Fail -> {
                    _userState.value = UserUiState.Error(result.errorMessage)
                }
            }
        }
    }

    sealed interface UserUiState {
        object Loading : UserUiState
        data class Success(val userData: UserInfo) : UserUiState
        data class Error(val message: String) : UserUiState
    }
}
