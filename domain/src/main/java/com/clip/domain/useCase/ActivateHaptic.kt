package com.clip.domain.useCase

import com.clip.domain.repository.DeviceRepository
import javax.inject.Inject

class ActivateHaptic @Inject constructor(private val device: DeviceRepository) {
    suspend fun invoke() {
        device.requestVibrate()
    }
}