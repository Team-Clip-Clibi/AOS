package com.clip.fcm

interface FirebaseToken {
    suspend fun getFirebaseToken() : String
}