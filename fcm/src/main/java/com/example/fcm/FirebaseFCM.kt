package com.example.fcm

interface FirebaseFCM {
    suspend fun getFirebaseToken() : String
}