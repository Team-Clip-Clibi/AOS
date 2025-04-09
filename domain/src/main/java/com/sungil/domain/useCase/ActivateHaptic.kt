package com.sungil.domain.useCase

import com.sungil.domain.repository.DeviceRepository
import javax.inject.Inject

class ActivateHaptic @Inject constructor(private val device: DeviceRepository) {
    suspend fun invoke() {
        device.requestVibrate()
    }
}