package com.oneThing.random

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oneThing.random.component.Location
import com.oneThing.random.component.RandomMatch
import com.sungil.domain.useCase.CheckRandomMatchDuplicate
import com.sungil.domain.useCase.GetRandomMatch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RandomMatchViewModel @Inject constructor(
    private val checkDuplicate: CheckRandomMatchDuplicate,
    private val random: GetRandomMatch,
) :
    ViewModel() {
    private val _uiState = MutableStateFlow(RandomMatchData())
    val uiState: StateFlow<RandomMatchData> = _uiState.asStateFlow()

    fun duplicateCheck() {
        viewModelScope.launch {
            when (val result = checkDuplicate.invoke()) {
                is CheckRandomMatchDuplicate.Result.Fail -> {
                    _uiState.update { current ->
                        current.copy(error = UiError.Error(result.errorMessage))
                    }
                }

                is CheckRandomMatchDuplicate.Result.Success -> {
                    _uiState.update { current ->
                        current.copy(
                            success = UiSuccess.DuplicateSuccess(
                                result.meetTime,
                                result.nextMeetTime
                            )
                        )
                    }
                }
            }
        }
    }

    fun randomMatch() {
        viewModelScope.launch {
            val current = _uiState.value
            when (val result = random.invoke(
                GetRandomMatch.Params(
                    tmi = current.tmi,
                    topic = current.topic,
                    district = current.location.name
                )
            )) {
                is GetRandomMatch.Result.Error -> {
                    _uiState.update { current ->
                        current.copy(error = UiError.Error(result.error))
                    }
                }

                is GetRandomMatch.Result.Success -> {
                    _uiState.update { current ->
                        current.copy(
                            success = UiSuccess.RandomMatchSuccess(
                                RandomMatch(
                                    orderId = result.orderId,
                                    amount = result.amount,
                                    meetingTime = result.meetingTime,
                                    meetingPlace = result.meetingPlace,
                                    meetingLocation = result.meetingLocation
                                )
                            )
                        )
                    }
                }
            }
        }
    }

    fun location(location: Location) {
        _uiState.update { current ->
            current.copy(location = location)
        }
    }

    fun tmi(tmi: String) {
        _uiState.update { current ->
            current.copy(tmi = tmi)
        }
    }

    fun topic(topic: String) {
        _uiState.update { current ->
            current.copy(topic = topic)
        }
    }

    data class RandomMatchData(
        val success: UiSuccess = UiSuccess.None,
        val error: UiError = UiError.None,
        val location: Location = Location.NONE,
        val tmi: String = "",
        val topic: String = "",
    )
}

sealed interface UiSuccess {
    data object None : UiSuccess
    data class DuplicateSuccess(val date: String, val nextDate: String) : UiSuccess
    data class RandomMatchSuccess(val data: RandomMatch) : UiSuccess
}

sealed class UiError {
    data object None : UiError()
    data class Error(val message: String) : UiError()
}