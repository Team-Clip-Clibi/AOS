package com.sungil.onethingmatch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.sungil.domain.model.Router
import com.sungil.onethingmatch.nav.OneThingNav
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OneThinMatchActivity : ComponentActivity() {
    private val viewModel: OneThingViewModel by viewModels()

    @Inject
    lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OneThingNav(
                viewModel = viewModel,
                home = { router.navigation(NAV_HOME) },
                pay = { router.navigation(NAV_PAY) }
            )
        }
    }

}