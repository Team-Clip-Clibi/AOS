package com.sungil.device

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.telephony.TelephonyManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DeviceImpl @Inject constructor(@ApplicationContext private val application: Context) :
    Device {
    private val REQUEST_CODE = 1001

    override suspend fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                application,
                Manifest.permission.READ_PHONE_STATE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        ActivityCompat.requestPermissions(
            application as Activity,
            arrayOf(Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_PHONE_NUMBERS),
            REQUEST_CODE
        )
    }

    override suspend fun getPhoneNumber(): String {
        val phoneManger =
            application.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        if (ContextCompat.checkSelfPermission(
                application,
                Manifest.permission.READ_PHONE_STATE
            ) == PackageManager.PERMISSION_DENIED || ContextCompat.checkSelfPermission(
                application,
                Manifest.permission.READ_PHONE_NUMBERS
            ) == PackageManager.PERMISSION_DENIED
        ) {
            return "Permission Denied"
        }
        return phoneManger.line1Number
    }
}