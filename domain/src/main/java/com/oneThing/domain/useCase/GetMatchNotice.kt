package com.oneThing.domain.useCase

import androidx.paging.PagingData
import com.oneThing.domain.model.MatchNotice
import com.oneThing.domain.repository.NetworkRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMatchNotice @Inject constructor(private val network: NetworkRepository) {
    fun invoke(lastTime: String): Flow<PagingData<MatchNotice>> {
        return network.requestMatchNotice(lastTime)
    }
}