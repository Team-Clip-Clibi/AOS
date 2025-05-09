package com.sungil.onethingmatch

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OneThingViewModel @Inject constructor() : ViewModel() {
    var subjectText by mutableStateOf("")

    fun onSubjectChanged(newValue: String) {
        subjectText = newValue
    }
}