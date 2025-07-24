package com.oneThing.data.mapper

import com.oneThing.device.model.MatchTriggerDTO
import com.oneThing.domain.model.MatchTrigger

fun MatchTriggerDTO.toDomain(): MatchTrigger = MatchTrigger(
    id = matchId,
    time = matchTime,
    type = meetingType,
    trigger = triggerType.name
)
