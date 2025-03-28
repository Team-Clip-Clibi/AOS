package com.example.data.repositoryImpl

import com.sungil.device.Device
import com.sungil.domain.repository.DeviceRepository
import javax.inject.Inject

class DeviceRepositoryImpl @Inject constructor(private val device: Device) : DeviceRepository {

    override suspend fun requestVibrate() {
        device.requestVibrate()
    }

    override suspend fun getAndroidOsVersion(): Int {
        return device.getOsVersion()
    }

}