package com.sungil.main


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sungil.domain.model.BannerData
import com.sungil.domain.model.MatchData
import com.sungil.domain.model.NotificationData
import com.sungil.domain.model.NotificationResponse
import com.sungil.domain.model.OneThineNotify
import com.sungil.domain.model.UserData
import com.sungil.domain.useCase.GetBanner
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
    private val banner: GetBanner,
) : ViewModel() {

    private val _userState = MutableStateFlow(MainViewState())
    val userState: StateFlow<MainViewState> = _userState.asStateFlow()

    init {
        requestUserInfo()
        oneThingNotify()
        serviceNotify()
        requestMatch()
        getBanner()
    }

    private fun requestUserInfo() {
        viewModelScope.launch {
            when (val result = userInfo.invoke()) {
                is GetUserInfo.Result.Success -> {
                    _userState.update {
                        it.copy(userDataState = UiState.Success(result.data))
                    }
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
                        it.copy(notificationResponseState = UiState.Error(result.errorMessage))
                    }
                }

                is GetNotification.Result.Success -> {
                    _userState.update {
                        it.copy(notificationResponseState = UiState.Success(result.data))
                    }
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

    private fun getBanner() {
        viewModelScope.launch {
            when (val result = banner.invoke(GetBanner.Param("HOME"))) {
                is GetBanner.Result.Fail -> {
                    _userState.update {
                        it.copy(banner = UiState.Error(result.message))
                    }
                }

                is GetBanner.Result.Success -> {
                    val test = result.data
                    _userState.update {
                        it.copy(banner = UiState.Success(result.data))
                    }
                }
            }
        }
    }

    sealed interface UiState<out T> {
        data object Loading : UiState<Nothing>
        data class Success<T>(val data: T) : UiState<T>
        data class Error(val message: String) : UiState<Nothing>
    }

    data class MainViewState(
        val userDataState: UiState<UserData> = UiState.Loading,
        val notificationResponseState: UiState<List<NotificationData>> = UiState.Loading,
        val oneThingState: UiState<List<OneThineNotify>> = UiState.Loading,
        val banner: UiState<List<BannerData>> = UiState.Loading,
        val matchState: UiState<MatchData> = UiState.Loading,
    )
}

