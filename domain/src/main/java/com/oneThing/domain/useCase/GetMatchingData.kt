package com.oneThing.domain.useCase

import androidx.paging.PagingData
import com.oneThing.domain.model.MatchingData
import com.oneThing.domain.repository.NetworkRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetMatchingData @Inject constructor(private val network: NetworkRepository) {

    fun invoke(
        matchingStatus: String,
        lastMeetingTime: String,
    ): Flow<PagingData<MatchingData>> {
        return network.requestMatchingData(matchStatus = matchingStatus, lastTime = lastMeetingTime)
    }
}