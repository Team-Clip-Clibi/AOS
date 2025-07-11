package com.example.data.mapper

import com.sungil.domain.CATEGORY
import com.sungil.domain.model.MatchData
import com.sungil.domain.model.MatchInfo
import com.sungil.network.model.MatchingDto
import com.sungil.network.model.MatchingResponse

fun MatchingResponse.toMatchData(): MatchData {
    return MatchData(
        oneThingMatch = oneThingMatchings.map {
            it.toMatchInfo(CATEGORY.CONTENT_ONE_THING)
        },
        randomMatch = randomMatchings.map {
            it.toMatchInfo(CATEGORY.CONTENT_RANDOM)
        }
    )
}

fun MatchingDto.toMatchInfo(category: CATEGORY): MatchInfo {
    return MatchInfo(
        category = category,
        matchingId = matchingId,
        daysUntilMeeting = daysUntilMeeting,
        meetingPlace = meetingPlace,
        matchTime = meetingTime
    )
}
