package com.sungil.network

import android.app.Activity
import android.content.Context
import com.google.firebase.Firebase
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.auth
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class FirebaseRepoImpl @Inject constructor(@ApplicationContext private val applicationContext: Context) :
    FirebaseRepo {
    private val auth = Firebase.auth

    override suspend fun requestSMS(phoneNumber: String, activity: Activity): Boolean {
        val callback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                TODO("Not yet implemented")
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                TODO("Not yet implemented")
            }

        }
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber("+82${phoneNumber}")

    }
}