package com.sungil.main


import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sungil.domain.model.Match
import com.sungil.domain.model.Notification
import com.sungil.domain.model.OneThineNotify
import com.sungil.domain.model.UserInfo
import com.sungil.domain.useCase.GetMatch
import com.sungil.domain.useCase.GetNewNotification
import com.sungil.domain.useCase.GetNotification
import com.sungil.domain.useCase.GetUserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userInfo: GetUserInfo,
    private val notify: GetNotification,
    private val oneThingNotify: GetNewNotification,
    private val match: GetMatch,
) : ViewModel() {

    private val _userState = MutableStateFlow(MainViewState())
    val userState: StateFlow<MainViewState> = _userState.asStateFlow()

    private val _notifyShow = MutableStateFlow(true)
    val notifyShow: StateFlow<Boolean> = _notifyShow

    init {
        requestUserInfo()
    }

    private fun requestUserInfo() {
        viewModelScope.launch {
            when (val result = userInfo.invoke()) {
                is GetUserInfo.Result.Success -> {
                    _userState.update {
                        it.copy(userDataState = UiState.Success(result.data))
                    }
                    oneThingNotify()
                }

                is GetUserInfo.Result.Fail -> {
                    _userState.update {
                        it.copy(userDataState = UiState.Error(result.errorMessage))
                    }
                }
            }
        }
    }

    private fun oneThingNotify() {
        viewModelScope.launch {
            when (val result = oneThingNotify.invoke()) {
                is GetNewNotification.Result.Fail -> {
                    _userState.update {
                        it.copy(oneThingState = UiState.Error(result.errorMessage))
                    }
                    serviceNotify()
                }

                is GetNewNotification.Result.Success -> {
                    _userState.update {
                        it.copy(oneThingState = UiState.Success(result.data))
                    }
                }
            }
        }
    }

    private fun serviceNotify() {
        viewModelScope.launch {
            when (val result = notify.invoke()) {
                is GetNotification.Result.Fail -> {
                    _userState.update {
                        it.copy(notificationState = UiState.Error(result.errorMessage))
                    }
                }

                is GetNotification.Result.Success -> {
                    _userState.update {
                        it.copy(notificationState = UiState.Success(result.data))
                    }
                    requestMatch()
                }
            }
        }
    }

    private fun requestMatch() {
        viewModelScope.launch {
            when (val result = match.invoke()) {
                is GetMatch.Result.Fail -> {
                    _userState.update {
                        it.copy(matchState = UiState.Error(result.errorMessage))
                    }
                }

                is GetMatch.Result.Success -> {
                    _userState.update {
                        it.copy(matchState = UiState.Success(result.data))
                    }
                }
            }
        }
    }

    fun setNotifyShow(data: Boolean) {
        _notifyShow.value = data
    }

    sealed interface UiState<out T> {
        object Loading : UiState<Nothing>
        data class Success<T>(val data: T) : UiState<T>
        data class Error(val message: String) : UiState<Nothing>
    }

    data class MainViewState(
        val userDataState: UiState<UserInfo> = UiState.Loading,
        val notificationState: UiState<Notification> = UiState.Loading,
        val oneThingState: UiState<List<OneThineNotify>> = UiState.Loading,
        val matchState: UiState<Match> = UiState.Loading,
    )
}

