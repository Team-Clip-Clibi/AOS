package com.sungil.device

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.VibrationEffect
import android.os.Vibrator
import android.telephony.TelephonyManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DeviceImpl @Inject constructor(@ApplicationContext private val application: Context) :
    Device {

    override suspend fun requestVibrate() {
        val vibrator = application.getSystemService(Vibrator::class.java)
        val vibrationEffect = VibrationEffect.createPredefined(VibrationEffect.EFFECT_HEAVY_CLICK)
        vibrator.vibrate(vibrationEffect)
    }

    override suspend fun getOsVersion(): Int {
        return when(android.os.Build.VERSION.SDK_INT){
            29 -> 10
            30 -> 11
            31, 32 -> 12
            33 -> 13
            34 -> 14
            35 -> 15
            else -> 0
        }
    }

}