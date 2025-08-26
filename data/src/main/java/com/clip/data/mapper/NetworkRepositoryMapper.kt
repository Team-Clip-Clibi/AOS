package com.clip.data.mapper

import com.clip.domain.CATEGORY
import com.clip.domain.model.MatchData
import com.clip.domain.model.MatchInfo
import com.clip.domain.model.MatchProgress
import com.clip.domain.model.OneThineNotify
import com.clip.domain.model.UserData
import com.clip.domain.model.UserInfo
import com.clip.network.model.MatchProgressDTO
import com.clip.network.model.MatchingDto
import com.clip.network.model.MatchingResponse
import com.clip.network.model.OneThinNotify
import com.clip.network.model.RequestUserInfo

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
