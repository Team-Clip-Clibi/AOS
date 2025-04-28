package com.sungil.alarm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sungil.domain.model.Notification
import com.sungil.domain.useCase.GetNotice
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class AlarmViewModel @Inject constructor(private val getNotification: GetNotice) : ViewModel() {
    private val _notificationPagingFlow =
        MutableStateFlow<PagingData<Notification>>(PagingData.empty())
    val notificationPagingFlow: StateFlow<PagingData<Notification>> =
        _notificationPagingFlow.asStateFlow()

    init {
        loadNotification()
    }

    private fun loadNotification() {
        viewModelScope.launch {
            getNotification.invoke()
                .cachedIn(viewModelScope)
                .collectLatest { pagingData ->
                    _notificationPagingFlow.value = pagingData
                }
        }
    }
}