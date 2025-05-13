package com.sungil.onethingmatch

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import kotlin.Error

@HiltViewModel
class OneThingViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(OneThingData())
    val uiState: StateFlow<OneThingData> = _uiState.asStateFlow()

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
}

data class OneThingData(
    val subject: String = "",
    val selectedCategories: Set<CATEGORY> = emptySet(),
    val location : Set<Location> = emptySet(),
    val error: UiError = UiError.None
)

sealed class UiError{
    data object MaxLocationSelected : UiError()
    data object MaxCategorySelected : UiError()
    data object None : UiError()
}