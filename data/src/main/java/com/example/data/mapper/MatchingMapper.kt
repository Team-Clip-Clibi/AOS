package com.example.data.mapper

import androidx.paging.PagingSource
import com.sungil.domain.model.MatchingData
import com.sungil.network.model.Matching
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