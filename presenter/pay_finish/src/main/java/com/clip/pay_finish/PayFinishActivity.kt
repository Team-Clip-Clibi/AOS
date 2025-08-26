package com.clip.pay_finish

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import com.clip.domain.model.Router
import com.clip.pay_finish.ui.PayFinishView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PayFinishActivity : ComponentActivity() {

    @Inject
    lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val orderType: String = intent?.getStringExtra(BuildConfig.KEY_MATCH) ?: ""
        if (orderType.isEmpty()) {
            finish()
        }
        setContent {
            PayFinishView(
                orderType = orderType,
                onBackClick = {
                    router.navigation(NAV_HONE)
                }
            )
        }
        onBackPressedDispatcher.addCallback(this) {
            router.navigation(NAV_HONE)
        }
    }
}