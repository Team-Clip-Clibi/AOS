package com.clip.domain.useCase

import com.clip.domain.model.MatchData
import com.clip.domain.model.MatchTrigger
import com.clip.domain.repository.DeviceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MonitoryMeetingTime @Inject constructor(
    private val device: DeviceRepository,
) {
    data class Param(val data: MatchData)

    fun invoke(param: Param): Flow<MatchTrigger> = channelFlow {
        val matchInfos = param.data.oneThingMatch + param.data.randomMatch
        val todayMatches = matchInfos.filter { it.daysUntilMeeting == 0 }

        todayMatches.forEach { info ->
            launch {
                device.startCheckMeetingTime(
                    meetTime = info.matchTime,
                    matchId = info.matchingId,
                    matchType = info.category.name
                ).collect {
                    send(it)
                }
            }
        }
    }
}
