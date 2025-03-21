package com.sungil.device

interface Device {
    suspend fun checkPermission()
    suspend fun getPhoneNumber() : String
}