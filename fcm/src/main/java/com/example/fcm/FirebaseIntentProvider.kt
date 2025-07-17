package com.example.fcm

import android.content.Context
import android.content.Intent

interface FirebaseIntentProvider {
    fun getFCMIntent(context: Context): Intent
}