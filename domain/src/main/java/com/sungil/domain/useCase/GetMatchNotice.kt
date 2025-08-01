package com.sungil.domain.useCase

import androidx.paging.PagingData
import com.sungil.domain.model.MatchNotice
import com.sungil.domain.repository.NetworkRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMatchNotice @Inject constructor(private val network: NetworkRepository) {
    fun invoke(lastTime: String): Flow<PagingData<MatchNotice>> {
        return network.requestMatchNotice(lastTime)
    }
}