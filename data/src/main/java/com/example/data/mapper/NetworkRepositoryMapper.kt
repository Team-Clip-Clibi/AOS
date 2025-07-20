package com.example.data.mapper

import com.sungil.domain.CATEGORY
import com.sungil.domain.model.MatchData
import com.sungil.domain.model.MatchInfo
import com.sungil.domain.model.MatchProgress
import com.sungil.domain.model.OneThineNotify
import com.sungil.domain.model.UserData
import com.sungil.domain.model.UserInfo
import com.sungil.network.model.MatchProgressDTO
import com.sungil.network.model.MatchingDto
import com.sungil.network.model.MatchingResponse
import com.sungil.network.model.OneThinNotify
import com.sungil.network.model.RequestUserInfo

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
