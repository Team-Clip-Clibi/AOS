package com.sungil.onethingmatch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sungil.domain.model.WeekData
import com.sungil.domain.useCase.GetOneThingDay
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OneThingViewModel @Inject constructor(
    private val oneThingDate: GetOneThingDay,
) : ViewModel() {
    private val _uiState = MutableStateFlow(OneThingData())
    val uiState: StateFlow<OneThingData> = _uiState.asStateFlow()

    init {
        date()
    }

    fun onSubjectChanged(newValue: String) {
        _uiState.update { it.copy(subject = newValue) }
    }

    fun toggleCategory(category: CATEGORY) {
        _uiState.update { current ->
            val currentSet = current.selectedCategories
            when {
                category in currentSet -> current.copy(
                    selectedCategories = currentSet - category,
                    error = UiError.None
                )

                currentSet.size < 2 -> current.copy(
                    selectedCategories = currentSet + category,
                    error = UiError.None
                )

                else -> current.copy(
                    error = UiError.MaxCategorySelected
                )
            }
        }
    }

    fun location(location: Location) {
        _uiState.update { current ->
            val currentSet = current.location
            when {
                location in currentSet -> current.copy(
                    location = currentSet - location,
                    error = UiError.None
                )

                currentSet.size < 2 -> current.copy(
                    location = currentSet + location,
                    error = UiError.None
                )

                else -> current.copy(
                    error = UiError.MaxLocationSelected
                )
            }
        }
    }

    fun selectDate(date: WeekData) {
        _uiState.update { current ->
            val currentSet = current.selectDate
            when {
                date in currentSet -> current.copy(
                    selectDate = currentSet - date,
                    error = UiError.None
                )

                currentSet.size < 3 -> current.copy(
                    selectDate = currentSet + date,
                    error = UiError.None
                )

                else -> current.copy(
                    error = UiError.MaxDateSelected
                )
            }
        }
    }

    fun removeDate(date: WeekData) {
        _uiState.update { current ->
            val currentSet = current.selectDate
            when {
                date in currentSet -> current.copy(
                    selectDate = currentSet - date,
                    error = UiError.None
                )

                else -> current.copy(
                    error = UiError.NullDataSelect
                )
            }
        }
    }

    fun date() {
        viewModelScope.launch {
            when (val result = oneThingDate.invoke()) {
                is GetOneThingDay.Result.Success -> {
                    _uiState.update { data ->
                        data.copy(
                            dateData = result.data,
                            error = UiError.None
                        )
                    }
                }

                is GetOneThingDay.Result.Fail -> {
                    _uiState.update { errorDate ->
                        errorDate.copy(error = UiError.FailToGetDate)
                    }
                }
            }
        }
    }

    fun onBudgetChanged(data: Budget) {
        _uiState.update { current ->
            current.copy(budget = data, error = UiError.None)
        }
    }

}

data class OneThingData(
    val subject: String = "",
    val selectedCategories: Set<CATEGORY> = emptySet(),
    val location: Set<Location> = emptySet(),
    val dateData: List<WeekData> = emptyList(),
    val selectDate: Set<WeekData> = emptySet(),
    val budget: Budget = Budget.RANGE_NONE,
    val error: UiError = UiError.None,
)

sealed class UiError {
    data object MaxLocationSelected : UiError()
    data object MaxCategorySelected : UiError()
    data object MaxDateSelected : UiError()
    data object NullDataSelect : UiError()
    data object FailToGetDate : UiError()
    data object None : UiError()
}