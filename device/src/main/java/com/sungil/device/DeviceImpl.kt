package com.sungil.device

import android.content.Context
import android.content.pm.PackageManager
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import com.sungil.device.model.MatchTriggerDTO
import com.sungil.device.model.TriggerType
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.Duration
import java.time.ZonedDateTime
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

    override suspend fun checkTossInstall(): Boolean {
        return try{
            application.packageManager.getPackageInfo("viva.republica.toss", 0)
            true
        }catch (e : PackageManager.NameNotFoundException){
            Log.d(javaClass.name.toString() , "error message : ${e.printStackTrace()}")
            false
        }
    }

    override fun monitorMeetingTime(
        meetTime: String,
        meetId: Int,
        meetType: String,
    ): Flow<MatchTriggerDTO> = flow {
        val targetTime = ZonedDateTime.parse(meetTime)
        var timeUpEmitted = false

        while (true) {
            val now = ZonedDateTime.now()
            val secondsDiff = Duration.between(targetTime, now).seconds
            Log.d(
                javaClass.name,
                "Meeting ID: $meetId 지난 시간: $secondsDiff 초"
            )
            if (!timeUpEmitted && secondsDiff >= 0) {
                requestVibrate()
                emit(
                    MatchTriggerDTO(
                        matchId = meetId,
                        matchTime = meetTime,
                        meetingType = meetType,
                        triggerType = TriggerType.TIME_UP
                    )
                )
                timeUpEmitted = true
            }
            if (timeUpEmitted && secondsDiff >= 120) {
                emit(
                    MatchTriggerDTO(
                        matchId = meetId,
                        matchTime = meetTime,
                        meetingType = meetType,
                        triggerType = TriggerType.OVERDUE
                    )
                )
                break
            }
            delay(1000L)
        }
    }


}