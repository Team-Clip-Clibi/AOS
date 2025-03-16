package com.sungil.onething.di

import android.content.Context
import android.content.Intent
import com.example.signup.SignUpActivity
import com.sungil.domain.model.Router
import com.sungil.kakao.com.kakao.sdk.auth.AuthCodeHandlerActivity

class Router(private val context: Context) : Router {
    override fun navigationToSMS(target: String) {
        when (target) {
            "KAKAO" -> {
                val intent = Intent(context, AuthCodeHandlerActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                context.startActivity(intent)
            }

            "SignUp" -> {
                val intent = Intent(context, SignUpActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                context.startActivity(intent)
            }

            else -> {
                throw IllegalArgumentException("Unsupported target: $target")
            }
        }
    }
}