package com.oneThing.fcm

interface FirebaseToken {
    suspend fun getFirebaseToken() : String
}