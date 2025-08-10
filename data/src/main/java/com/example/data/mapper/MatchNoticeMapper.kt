package com.example.data.mapper

import androidx.paging.PagingSource
import com.sungil.domain.model.Job
import com.sungil.domain.model.MatchNotice
import com.sungil.network.model.MatchNoticeDto
import retrofit2.Response

fun Response<List<MatchNoticeDto>>.toLoadResult(currentKey: String): PagingSource.LoadResult<String, MatchNotice> {
    val data = body()?.map { it.toMatchNotice() } ?: emptyList()
    val lastKey = data.lastOrNull()?.matchTime?.trim()
    val nextKey = if (lastKey.isNullOrEmpty() || lastKey == currentKey) null else lastKey

    return PagingSource.LoadResult.Page(
        data = data,
        prevKey = null,
        nextKey = nextKey
    )
}


private fun MatchNoticeDto.toMatchNotice(): MatchNotice {
    return MatchNotice(
        matchId = this.matchingId,
        matchTime = this.meetingTime,
        matchStatus = this.matchingStatus[0],
        matchType = this.matchingType,
        restaurantName = this.restaurantName,
        restaurantAddress = this.location,
        menuCategory = this.menuCategory,
        jonInfos = this.jobInfos.map {
            Job(
                jobName = it.jobName,
                count = it.count
            )
        },
        diet = this.dietaryList,
        category = this.myOneThingContent
    )
}