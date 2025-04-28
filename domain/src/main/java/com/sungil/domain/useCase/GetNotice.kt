package com.sungil.domain.useCase

import androidx.paging.PagingData
import com.sungil.domain.UseCase
import com.sungil.domain.model.Notification
import com.sungil.domain.repository.NetworkRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNotice @Inject constructor(private val network: NetworkRepository) {

    suspend fun invoke(): Flow<PagingData<Notification>> {
        return network.requestNotificationPaging()
    }
}