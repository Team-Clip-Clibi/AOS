package com.example.fcm

interface FirebaseToken {
    suspend fun getFirebaseToken() : String
}