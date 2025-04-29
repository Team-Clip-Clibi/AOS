package com.sungil.alarm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.sungil.alarm.component.NAV_MAIN
import com.sungil.alarm.ui.AlarmView
import com.sungil.domain.model.Router
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AlarmMainActivity : ComponentActivity() {
    private val viewModel: AlarmViewModel by viewModels()

    @Inject
    lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AlarmView(
                viewModel = viewModel,
                backClick = {
                    router.navigation(NAV_MAIN)
                },
                reLogin = {

                }
            )
        }
    }

}