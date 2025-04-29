package com.sungil.alarm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sungil.domain.model.Notification
import com.sungil.domain.useCase.GetNotice
import com.sungil.domain.useCase.GetReadNotice
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class AlarmViewModel @Inject constructor(
    private val getNotification: GetNotice,
    private val getReadNotice: GetReadNotice,
) : ViewModel() {
    private val _unReadNotificationPagingFlow =
        MutableStateFlow<PagingData<Notification>>(PagingData.empty())
    val unReadNotify: StateFlow<PagingData<Notification>> =
        _unReadNotificationPagingFlow.asStateFlow()

    private val _readNotification = MutableStateFlow<PagingData<Notification>>(PagingData.empty())
    val readNotification: StateFlow<PagingData<Notification>> = _readNotification.asStateFlow()

    init {
        loadNotification()
        loadReadNotice()
    }

    private fun loadNotification() {
        viewModelScope.launch {
            getNotification.invoke()
                .cachedIn(viewModelScope)
                .collectLatest { pagingData ->
                    _unReadNotificationPagingFlow.value = pagingData
                }
        }
    }

    private fun loadReadNotice() {
        viewModelScope.launch {
            getReadNotice.invoke()
                .cachedIn(viewModelScope)
                .collectLatest { pagingData ->
                    _readNotification.value = pagingData
                }
        }
    }

}