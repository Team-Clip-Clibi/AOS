package com.sungil.device

interface Device {
    suspend fun requestVibrate()
    suspend fun getOsVersion() : Int
    suspend fun checkTossInstall() : Boolean
}