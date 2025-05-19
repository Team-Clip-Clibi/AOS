package com.sungil.onething.di

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.signup.SignUpActivity
import com.sungil.alarm.AlarmMainActivity
import com.sungil.billing.BillingActivity
import com.sungil.domain.model.Router
import com.sungil.editprofile.ProfileEditMainActivity
import com.sungil.kakao.com.kakao.sdk.auth.AuthCodeHandlerActivity
import com.sungil.login.LoginActivity
import com.sungil.low.ui.low.LowActivity
import com.sungil.main.MainActivity
import com.sungil.onethingmatch.OneThinMatchActivity
import com.sungil.pay_finish.PayFinishActivity
import com.sungil.report.ReportMainActivity

class Router(private val context: Context) : Router {
    override fun navigation(target: String, args: Bundle) {
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

            "Login" -> {
                val intent = Intent(context, LoginActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                context.startActivity(intent)
            }

            "Main" -> {
                val intent = Intent(context, MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                context.startActivity(intent)
            }

            "MainEditProfile" -> {
                val intent = Intent(context, ProfileEditMainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                context.startActivity(intent)
            }

            "report" -> {
                val intent = Intent(context, ReportMainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                context.startActivity(intent)
            }

            "low" -> {
                val intent = Intent(context, LowActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                context.startActivity(intent)
            }

            "alarm" -> {
                val intent = Intent(context, AlarmMainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                context.startActivity(intent)
            }

            "oneThing" -> {
                val intent = Intent(context, OneThinMatchActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                context.startActivity(intent)
            }

            "pay" -> {
                val intent = Intent(context, BillingActivity::class.java).apply {
                    putExtras(args)
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                context.startActivity(intent)
            }

            "pay_finish" ->{
                val intent = Intent(context , PayFinishActivity::class.java).apply {
                    putExtras(args)
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