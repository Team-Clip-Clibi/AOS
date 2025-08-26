package com.oneThing.first_matrch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.oneThing.first_matrch.nav.FirstMatchNav
import com.clip.domain.model.Router
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FirstMatchActivity : ComponentActivity() {
    private val viewModel: FirstMatchViewModel by viewModels()

    @Inject
    lateinit var router: Router
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val destination = intent?.getStringExtra(BuildConfig.KEY_MATCH)
            ?: throw IllegalArgumentException("destination data is null")

        viewModel.destination(destination)
        setContent {
            FirstMatchNav(
                viewModel = viewModel,
                goMatchPage = {
                    when (viewModel.getDestination()) {
                        BuildConfig.ONE_THING -> {
                            router.navigation(NAV_ONE_THING)
                        }

                        BuildConfig.RANDOM -> {
                            router.navigation(NAV_RANDOM)
                        }
                    }
                },
                home = {
                    finish()
                }
            )
        }
    }
}