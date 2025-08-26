package com.oneThing.random

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.oneThing.random.component.NAV_HOME
import com.oneThing.random.component.NAV_LOGIN
import com.oneThing.random.component.NAV_PAY
import com.oneThing.random.nav.RandomMatchNav
import com.clip.domain.model.Router
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RandomMatchActivity : ComponentActivity() {
    private val viewModel: RandomMatchViewModel by viewModels()

    @Inject
    lateinit var router: Router
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RandomMatchNav(
                onBack = {
                    finish()
                },
                loginPage = {
                    router.navigation(NAV_LOGIN)
                },
                viewModel = viewModel,
                goMeeting = {
                    /**
                     * TODO 기능 미구현
                     */
                },
                pay = { orderId, userId, amount, matchInfo ->
                    val bundle = Bundle().apply {
                        putString(BuildConfig.KEY_USER, userId)
                        putString(BuildConfig.KEY_ORDER, orderId)
                        putInt(BuildConfig.KEY_AMOUNT, amount)
                        putString(BuildConfig.KEY_MATCH, matchInfo)
                    }
                    router.navigation(NAV_PAY, bundle)
                }
            )
        }
        onBackPressedDispatcher.addCallback(this){
            router.navigation(NAV_HOME)
        }
    }
}