package com.oneThing.domain.useCase

import com.oneThing.domain.repository.DeviceRepository
import javax.inject.Inject

class ActivateHaptic @Inject constructor(private val device: DeviceRepository) {
    suspend fun invoke() {
        device.requestVibrate()
    }
}