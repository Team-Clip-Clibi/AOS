package com.clip.alarm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.clip.domain.useCase.GetNotice
import com.clip.domain.useCase.GetReadNotice
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(
    private val getNotification: GetNotice,
    private val getReadNotice: GetReadNotice,
) : ViewModel() {

    val unReadNotificationPagingFlow = getNotification.invoke()
        .cachedIn(viewModelScope)

    val readNotificationPagingFlow = getReadNotice.invoke()
        .cachedIn(viewModelScope)

    private val _isAlarmEmpty = MutableStateFlow(false)
    val isAlarmEmpty: StateFlow<Boolean> = _isAlarmEmpty.asStateFlow()

    fun setAlarmIsEmpty(result: Boolean) {
        _isAlarmEmpty.value = result
    }
}