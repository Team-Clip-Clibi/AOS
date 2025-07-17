package com.example.data.mapper

import com.sungil.device.model.MatchTriggerDTO
import com.sungil.domain.model.MatchTrigger

fun MatchTriggerDTO.toDomain(): MatchTrigger = MatchTrigger(
    id = matchId,
    time = matchTime,
    type = meetingType,
    trigger = triggerType.name
)
