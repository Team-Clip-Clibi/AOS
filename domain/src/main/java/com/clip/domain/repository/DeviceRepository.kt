package com.clip.domain.repository

import com.clip.domain.model.MatchTrigger
import kotlinx.coroutines.flow.Flow

interface DeviceRepository {
    suspend fun requestVibrate()
    suspend fun getAndroidOsVersion(): Int
    suspend fun checkTossInstall(): Boolean
    fun startCheckMeetingTime(
        meetTime: String,
        matchId: Int,
        matchType: String,
    ): Flow<MatchTrigger>
}