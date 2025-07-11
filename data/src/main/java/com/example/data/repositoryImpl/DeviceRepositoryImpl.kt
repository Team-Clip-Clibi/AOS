package com.example.data.repositoryImpl

import com.example.data.mapper.toDomain
import com.sungil.device.Device
import com.sungil.domain.model.MatchTrigger
import com.sungil.domain.repository.DeviceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DeviceRepositoryImpl @Inject constructor(private val device: Device) : DeviceRepository {

    override suspend fun requestVibrate() {
        device.requestVibrate()
    }

    override suspend fun getAndroidOsVersion(): Int {
        return device.getOsVersion()
    }

    override suspend fun checkTossInstall(): Boolean {
        return device.checkTossInstall()
    }

    override fun startCheckMeetingTime(
        meetTime: String,
        matchId: Int,
        matchType: String,
    ): Flow<MatchTrigger> {
        return device.monitorMeetingTime(
            meetTime = meetTime,
            meetId = matchId,
            meetType = matchType
        ).map { dto -> dto.toDomain() }
    }

}