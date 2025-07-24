package com.oneThing.data.mapper

import com.oneThing.domain.CATEGORY
import com.oneThing.domain.model.MatchData
import com.oneThing.domain.model.MatchInfo
import com.oneThing.domain.model.MatchProgress
import com.oneThing.domain.model.OneThineNotify
import com.oneThing.domain.model.UserData
import com.oneThing.domain.model.UserInfo
import com.oneThing.network.model.MatchProgressDTO
import com.oneThing.network.model.MatchingDto
import com.oneThing.network.model.MatchingResponse
import com.oneThing.network.model.OneThinNotify
import com.oneThing.network.model.RequestUserInfo

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

fun MatchProgressDTO.toMatchProgress(): MatchProgress {
    return MatchProgress(
        nicknameList = this.nicknameList,
        nicknameOnethingMap = this.nicknameOnethingMap,
        tmiList = this.tmiList
    )
}

fun RequestUserInfo.toDomain(responseCode: Int): UserInfo {
    return UserInfo(
        responseCode = responseCode,
        data = UserData(
            userName = username ?: "",
            nickName = nickname,
            phoneNumber = phoneNumber ?: "",
            job = "NONE",
            loveState = Pair("NONE", false),
            diet = "NONE",
            language = "KOREAN"
        )
    )
}

fun OneThinNotify.toDomain(): OneThineNotify {
    return OneThineNotify(
        id = id,
        notificationType = notificationType,
        content = content,
        createdAt = createdAt
    )
}
