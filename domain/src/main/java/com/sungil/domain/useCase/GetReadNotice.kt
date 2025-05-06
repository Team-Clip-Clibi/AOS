package com.sungil.domain.useCase

import androidx.paging.PagingData
import com.sungil.domain.model.Notification
import com.sungil.domain.repository.NetworkRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetReadNotice @Inject constructor(private val network: NetworkRepository) {
    fun invoke(): Flow<PagingData<Notification>> {
        return network.requestReadNotificationPaging()
    }
}