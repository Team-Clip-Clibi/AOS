package com.sungil.domain.repository

interface DeviceRepository {
    suspend fun requestVibrate()
    suspend fun getAndroidOsVersion() : Int
}