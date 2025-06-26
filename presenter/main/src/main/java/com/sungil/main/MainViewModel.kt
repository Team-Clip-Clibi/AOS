package com.sungil.main


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.sungil.domain.model.BannerData
import com.sungil.domain.model.MatchData
import com.sungil.domain.model.MatchDetail
import com.sungil.domain.model.NotificationData
import com.sungil.domain.model.OneThineNotify
import com.sungil.domain.model.UserData
import com.sungil.domain.useCase.GetBanner
import com.sungil.domain.useCase.GetFirstMatchInput
import com.sungil.domain.useCase.GetLatestMatch
import com.sungil.domain.useCase.GetMatch
import com.sungil.domain.useCase.GetMatchDetail
import com.sungil.domain.useCase.GetMatchingData
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
    private val firstMatch: GetFirstMatchInput,
    private val latestDay: GetLatestMatch,
    private val matching: GetMatchingData,
    private val matchDetail: GetMatchDetail,
) : ViewModel() {

    private val _userState = MutableStateFlow(MainViewState())
    val userState: StateFlow<MainViewState> = _userState.asStateFlow()

    val matchAllData = matching.invoke(matchingStatus = MATCH_KEY_ALL, lastMeetingTime = "")
        .cachedIn(viewModelScope)
    val matchApplied = matching.invoke(matchingStatus = MATCH_KEY_APPLIED, lastMeetingTime = "")
        .cachedIn(viewModelScope)
    val matchConfirmed = matching.invoke(matchingStatus = MATCH_KEY_CONFIRMED, lastMeetingTime = "")
        .cachedIn(viewModelScope)
    val matchComplete = matching.invoke(matchingStatus = MATCH_KEY_COMPLETED, lastMeetingTime = "")
        .cachedIn(viewModelScope)
    val matchCancelled = matching.invoke(matchingStatus = MATCH_KEY_CANCELLED, lastMeetingTime = "")
        .cachedIn(viewModelScope)

    private var _matchButton = MutableStateFlow(0)
    val matchButton: StateFlow<Int> = _matchButton.asStateFlow()

    init {
        requestUserInfo()
        oneThingNotify()
        serviceNotify()
        requestMatch()
        getBanner()
        getLatestMatch()
    }

    private fun requestUserInfo() {
        viewModelScope.launch {
            when (val result = userInfo.invoke()) {
                is GetUserInfo.Result.Success -> {
                    _userState.update { state ->
                        state.copy(userDataState = UiState.Success(result.data))
                    }
                }

                is GetUserInfo.Result.Fail -> {
                    _userState.update { state ->
                        state.copy(userDataState = UiState.Error(result.errorMessage))
                    }
                }
            }
        }
    }

    private fun oneThingNotify() {
        viewModelScope.launch {
            when (val result = oneThingNotify.invoke()) {
                is GetNewNotification.Result.Fail -> {
                    _userState.update { state ->
                        state.copy(oneThingState = UiState.Error(result.errorMessage))
                    }

                }

                is GetNewNotification.Result.Success -> {
                    _userState.update { state ->
                        state.copy(oneThingState = UiState.Success(result.data))
                    }
                }
            }
        }
    }

    private fun serviceNotify() {
        viewModelScope.launch {
            when (val result = notify.invoke()) {
                is GetNotification.Result.Fail -> {
                    _userState.update { state ->
                        state.copy(notificationResponseState = UiState.Error(result.errorMessage))
                    }
                }

                is GetNotification.Result.Success -> {
                    _userState.update { state ->
                        state.copy(notificationResponseState = UiState.Success(result.data))
                    }
                }
            }
        }
    }

    private fun requestMatch() {
        viewModelScope.launch {
            when (val result = match.invoke()) {
                is GetMatch.Result.Fail -> {
                    _userState.update { state ->
                        state.copy(matchState = UiState.Error(result.errorMessage))
                    }
                }

                is GetMatch.Result.Success -> {
                    _userState.update { state ->
                        state.copy(matchState = UiState.Success(result.data))
                    }
                }
            }
        }
    }

    private fun getLatestMatch() {
        viewModelScope.launch {
            when (val result = latestDay.invoke()) {
                is GetLatestMatch.Result.Fail -> {
                    _userState.update { state ->
                        state.copy(latestDay = UiState.Error(result.errorMessage))
                    }
                }

                is GetLatestMatch.Result.Success -> {
                    _userState.update { state ->
                        state.copy(
                            latestDay = UiState.Success(
                                LatestDayInfo(
                                    time = result.time,
                                    applyTime = result.applyTime,
                                    confirmTime = result.confirmTime
                                )
                            )
                        )
                    }
                }
            }
        }
    }

    private fun getBanner() {
        viewModelScope.launch {
            when (val result = banner.invoke(GetBanner.Param("HOME"))) {
                is GetBanner.Result.Fail -> {
                    _userState.update { state ->
                        state.copy(banner = UiState.Error(result.message))
                    }
                }

                is GetBanner.Result.Success -> {
                    _userState.update { state ->
                        state.copy(banner = UiState.Success(result.data))
                    }
                }
            }
        }
    }

    fun checkFirstMatch(orderType: String) {
        viewModelScope.launch {
            when (firstMatch.invoke()) {
                is GetFirstMatchInput.Result.Success -> {
                    _userState.update { state ->
                        state.copy(
                            firstMatch = UiState.Success(orderType)
                        )
                    }
                }

                is GetFirstMatchInput.Result.Fail -> {
                    _userState.update { state ->
                        state.copy(
                            firstMatch = UiState.Error(orderType)
                        )
                    }
                }
            }
        }
    }

    fun matchDetail(matchId: Int, matchType: String) {
        viewModelScope.launch {
            when (val result = matchDetail.invoke(
                GetMatchDetail.Param(
                    matchId = matchId,
                    matchType = matchType
                )
            )) {
                is GetMatchDetail.Result.Success -> {
                    _userState.update { state ->
                        state.copy(matchDetail = UiState.Success(result.matchDetail))
                    }
                }

                is GetMatchDetail.Result.Fail -> {
                    _userState.update { state ->
                        state.copy(matchDetail = UiState.Error(result.errorMessage))
                    }
                }
            }
        }
    }

    fun setMatchButton(index: Int) {
        _matchButton.value = index
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
        val firstMatch: UiState<String> = UiState.Loading,
        val latestDay: UiState<LatestDayInfo> = UiState.Loading,
        val matchDetail: UiState<MatchDetail> = UiState.Loading,
    )

}

data class LatestDayInfo(
    val time: String, val applyTime: Int, val confirmTime: Int,
)

