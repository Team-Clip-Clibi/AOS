package com.clip.data.mapper

import com.clip.device.model.MatchTriggerDTO
import com.clip.domain.model.MatchTrigger

fun MatchTriggerDTO.toDomain(): MatchTrigger = MatchTrigger(
    id = matchId,
    time = matchTime,
    type = meetingType,
    trigger = triggerType.name
)
