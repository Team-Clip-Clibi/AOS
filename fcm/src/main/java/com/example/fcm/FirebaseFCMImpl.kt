package com.example.fcm

import com.google.firebase.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseFCMImpl @Inject constructor() : FirebaseFCM {

    override suspend fun getFirebaseToken(): String {
        return try {
            FirebaseMessaging.getInstance().token.await()
        } catch (e: Exception) {
            "Error"
        }
    }

}