package com.clip.onething.di

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.clip.signup.SignUpActivity
import com.clip.billing.BillingActivity
import com.clip.domain.model.Router
import com.clip.login.LoginActivity
import com.clip.main.MainActivity
import com.clip.onething.NAV_LOGIN
import com.clip.onething.NAV_MAIN
import com.clip.onething.NAV_PAY
import com.clip.onething.NAV_PAY_FINISH

import com.clip.onething.NAV_SIGN_UP
import com.clip.pay_finish.PayFinishActivity

/**
 * TODO ENUM 값으로 변경해라
 */
class Router(private val context: Context) : Router {
    override fun navigation(target: String, args: Bundle) {
        when (target) {
            NAV_SIGN_UP -> {
                val intent = Intent(context, SignUpActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                context.startActivity(intent)
            }

            NAV_LOGIN -> {
                val intent = Intent(context, LoginActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                context.startActivity(intent)
            }

            NAV_MAIN -> {
                val intent = Intent(context, MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                context.startActivity(intent)
            }

            NAV_PAY -> {
                val intent = Intent(context, BillingActivity::class.java).apply {
                    putExtras(args)
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                context.startActivity(intent)
                if (context is Activity) {
                    context.finish()
                }
            }

            NAV_PAY_FINISH -> {
                val intent = Intent(context, PayFinishActivity::class.java).apply {
                    putExtras(args)
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                context.startActivity(intent)
                if (context is Activity) {
                    context.finish()
                }
            }

            else -> {
                throw IllegalArgumentException("Unsupported target: $target")
            }
        }
    }
}