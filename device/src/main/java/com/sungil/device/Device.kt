package com.sungil.device

import com.sungil.device.model.MatchTriggerDTO
import kotlinx.coroutines.flow.Flow

interface Device {
    suspend fun requestVibrate()
    suspend fun getOsVersion(): Int
    suspend fun checkTossInstall(): Boolean
    fun monitorMeetingTime(
        meetTime: String,
        meetId: Int,
        meetType: String,
    ): Flow<MatchTriggerDTO>
}