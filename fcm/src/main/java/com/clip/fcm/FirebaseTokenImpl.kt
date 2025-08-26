package com.clip.fcm

import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseTokenImpl @Inject constructor() : FirebaseToken {

    override suspend fun getFirebaseToken(): String {
        return try {
            FirebaseMessaging.getInstance().token.await()
        } catch (e: Exception) {
            throw RuntimeException("Error to get Firebase FCM Token : ${e.message}")
        }
    }

}