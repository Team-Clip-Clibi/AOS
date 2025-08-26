package com.clip.domain.useCase

import androidx.paging.PagingData
import com.clip.domain.model.MatchNotice
import com.clip.domain.repository.NetworkRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMatchNotice @Inject constructor(private val network: NetworkRepository) {
    fun invoke(lastTime: String): Flow<PagingData<MatchNotice>> {
        return network.requestMatchNotice(lastTime)
    }
}