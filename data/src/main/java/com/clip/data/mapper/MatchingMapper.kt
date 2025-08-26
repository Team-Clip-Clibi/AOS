package com.clip.data.mapper

import androidx.paging.PagingSource
import com.clip.domain.model.MatchingData
import com.clip.network.model.Matching
import retrofit2.Response

fun Response<List<Matching>>.toLoadResult(): PagingSource.LoadResult<String, MatchingData> {
    val data = body()?.map { it.toMatchData() } ?: emptyList()
    val nextKey = data.lastOrNull()?.meetTime
    return PagingSource.LoadResult.Page(
        data = data,
        prevKey = null,
        nextKey = nextKey
    )
}

fun Matching.toMatchData(): MatchingData {
    return MatchingData(
        id = this.id,
        meetTime = this.meetingTime,
        matchingStatusName = this.matchingStatus.matchingStatusName,
        matchingType = this.matchingType,
        myOneThingContent = this.myOneThingContent,
        isReviewWritten = this.isReviewWritten
    )
}