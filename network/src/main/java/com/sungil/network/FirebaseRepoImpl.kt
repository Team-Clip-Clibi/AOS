package com.sungil.network

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.auth
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class FirebaseRepoImpl @Inject constructor(@ApplicationContext private val applicationContext: Context) :
    FirebaseRepo {
    private val auth = Firebase.auth
    private var currentToken: PhoneAuthProvider.ForceResendingToken? = null
    private var currentVerificationId: String? = null

    private val _smsGranted = MutableStateFlow("")
    val smsGranted: StateFlow<String> = _smsGranted.asStateFlow()

    private val callback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(p0: PhoneAuthCredential) {
            Log.e(javaClass.name.toString(), "success to verification")
            _smsGranted.value = "Success"
        }

        override fun onVerificationFailed(p0: FirebaseException) {
            Log.e(javaClass.name.toString(), "Fail to verification")
            _smsGranted.value = "Error"
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken,
        ) {
            super.onCodeSent(verificationId, token)
            currentToken = token
            currentVerificationId = verificationId
            Log.d(
                javaClass.name.toString(),
                "Success to set id & token $currentVerificationId & $currentToken"
            )
        }
    }

    override suspend fun requestSMS(phoneNumber: String, activity: Activity): Boolean {
        return try {
            val options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber("+82${phoneNumber}")
                .setTimeout(120L , TimeUnit.SECONDS)
                .setActivity(activity)
                .setCallbacks(callback)
                .build()

            PhoneAuthProvider.verifyPhoneNumber(options)
            auth.setLanguageCode("kr")
            true
        } catch (e: Exception) {
            Log.e(javaClass.name.toString() , "error : ${e.message}")
            false
        }
    }

    override suspend fun verifyCode(code: String) {
        if (currentVerificationId == null) {
            Log.e(javaClass.name.toString(), "Error the verification id is null")
            return
        }
        val credential = PhoneAuthProvider.getCredential(currentVerificationId!!, code)
        auth.signInWithCredential(credential).await()
    }

    override suspend fun smsFlow(): Flow<String> = smsGranted
}