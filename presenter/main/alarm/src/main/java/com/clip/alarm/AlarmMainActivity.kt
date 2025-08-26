package com.clip.alarm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.clip.alarm.component.NAV_LOGIN
import com.clip.alarm.ui.AlarmView
import com.clip.domain.model.Router
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
                    finish()
                },
                reLogin = {
                    router.navigation(NAV_LOGIN)
                }
            )
        }
    }

}