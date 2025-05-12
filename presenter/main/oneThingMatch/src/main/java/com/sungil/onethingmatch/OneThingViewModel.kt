package com.sungil.onethingmatch

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

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
            val newSet = when {
                category in currentSet -> currentSet - category
                currentSet.size < 2 -> currentSet + category
                else -> currentSet
            }
            current.copy(selectedCategories = newSet)
        }
    }

}

data class OneThingData(
    val subject: String = "",
    val selectedCategories: Set<CATEGORY> = emptySet(),
)