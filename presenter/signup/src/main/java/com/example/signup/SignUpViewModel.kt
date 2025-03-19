package com.example.signup

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.signup.model.TermItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor() : ViewModel() {
    private val _termItem = MutableStateFlow(
        listOf(
            TermItem("allChecked", true, isAllCheck = true),
            TermItem("serviceChecked", true),
            TermItem("collectPerson", true),
            TermItem("marketing", false)
        )
    )
    val termItem: StateFlow<List<TermItem>> = _termItem.asStateFlow()

    fun changeTermItem(termItem: TermItem) {
        val updateList = _termItem.value.map {
            if (it.termName == termItem.termName) {
                termItem
            } else {
                it
            }
        }
        _termItem.value = updateList
    }
}