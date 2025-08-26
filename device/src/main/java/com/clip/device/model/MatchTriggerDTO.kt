package com.clip.device.model

data class MatchTriggerDTO(
    val matchId: Int,
    val matchTime: String,
    val meetingType: String,
    val triggerType: TriggerType,
)
enum class TriggerType {
    OVERDUE,
    TIME_UP
}