package com.sungil.onethingmatch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sungil.domain.model.WeekData
import com.sungil.domain.useCase.CheckTossInstall
import com.sungil.domain.useCase.GetOneThingDay
import com.sungil.domain.useCase.OneThingMatchOrder
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
    private val checkInstall: CheckTossInstall,
    private val order: OneThingMatchOrder,
) : ViewModel() {
    private val _uiState = MutableStateFlow(OneThingData())
    val uiState: StateFlow<OneThingData> = _uiState.asStateFlow()

    init {
        date()
    }

    fun onSubjectChanged(newValue: String) {
        _uiState.update { it.copy(topic = newValue) }
    }

    fun onTmiChanged(newValue: String) {
        _uiState.update { it.copy(tmi = newValue) }
    }

    fun toggleCategory(category: CATEGORY) {
        _uiState.update { current ->
            current.copy(selectedCategories = category)
        }
    }

    fun location(location: Location) {
        _uiState.update { current ->
            current.copy(location = location)
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

    private fun date() {
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

    fun checkInstall() {
        viewModelScope.launch {
            when (val result = checkInstall.invoke()) {
                is CheckTossInstall.Result.Success -> {
                    _uiState.update { current ->
                        current.copy(tosInstall = result.message)
                    }
                }

                is CheckTossInstall.Result.Fail -> {
                    _uiState.update { errorData ->
                        errorData.copy(error = UiError.TossNotInstall)
                    }
                }
            }
        }
    }

    fun order() {
        viewModelScope.launch {
            when (val result = order.invoke(
                OneThingMatchOrder.Param(
                    topic = _uiState.value.topic,
                    districts = _uiState.value.location.name,
                    date = _uiState.value.selectDate.toList(),
                    tmiContent = _uiState.value.tmi,
                    oneThingBudgetRange = _uiState.value.budget.name,
                    oneThingCategory = _uiState.value.selectedCategories.name,
                )
            )) {
                is OneThingMatchOrder.Result.Fail -> {
                    _uiState.update { error ->
                        error.copy(error = UiError.FailOrder(result.errorMessage))
                    }
                }

                is OneThingMatchOrder.Result.Success -> {
                    _uiState.update { order ->
                        order.copy(
                            orderNumber = result.orderId,
                            userId = result.userId,
                            amount = result.amount
                        )
                    }
                }
            }
        }
    }

    fun initError() {
        _uiState.update { errorData ->
            errorData.copy(error = UiError.None)
        }
    }

    fun initInstallResult() {
        _uiState.update { install ->
            install.copy(tosInstall = "")
        }
    }

    fun initOrderNumber() {
        _uiState.update { order ->
            order.copy(orderNumber = "", userId = "", amount = -1)
        }
    }
}

data class OneThingData(
    val topic: String = "",
    val tmi: String = "",
    val selectedCategories: CATEGORY = CATEGORY.NONE,
    val location: Location = Location.NONE,
    val dateData: List<WeekData> = emptyList(),
    val selectDate: Set<WeekData> = emptySet(),
    val budget: Budget = Budget.RANGE_NONE,
    val tosInstall: String = "",
    val orderNumber: String = "",
    val userId: String = "",
    val amount: Int = -1,
    val error: UiError = UiError.None,
)

sealed class UiError {
    data object MaxLocationSelected : UiError()
    data object MaxCategorySelected : UiError()
    data object MaxDateSelected : UiError()
    data object NullDataSelect : UiError()
    data object FailToGetDate : UiError()
    data object TossNotInstall : UiError()
    data class FailOrder(val message: String) : UiError()
    data object None : UiError()
}