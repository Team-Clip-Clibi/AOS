package com.sungil.domain.repository

import android.app.Activity

interface SNSLoginRepository {
    suspend fun loginKAKOWeb(activity: Activity): Result<String>
    suspend fun loginKAKAOSdk(activity: Activity): Result<String>
}