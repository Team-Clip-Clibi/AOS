package com.sungil.low.ui.low

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.sungil.domain.model.Router
import com.sungil.low.LowView
import com.sungil.low.NAV_MAIN
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LowActivity : ComponentActivity() {

    @Inject
    lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LowView(onBackClick = { router.navigation(NAV_MAIN) })
        }
    }
}
