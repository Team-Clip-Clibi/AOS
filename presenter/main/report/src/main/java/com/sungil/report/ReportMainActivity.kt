package com.sungil.report

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.sungil.domain.model.Router
import com.sungil.report.nav.ReportNav
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ReportMainActivity : ComponentActivity() {
    private val viewModel: ReportViewModel by viewModels()

    @Inject
    lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReportNav(
                viewModel = viewModel,
                onProfilePage = {
                    router.navigation(NAV_MAIN)
                }
            )
        }
    }

}