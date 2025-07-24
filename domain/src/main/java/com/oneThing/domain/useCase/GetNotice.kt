package com.oneThing.domain.useCase

import androidx.paging.PagingData
import com.oneThing.domain.model.Notification
import com.oneThing.domain.repository.NetworkRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNotice @Inject constructor(private val network: NetworkRepository) {

    fun invoke(): Flow<PagingData<Notification>> {
        return network.requestNotificationPaging()
    }
}