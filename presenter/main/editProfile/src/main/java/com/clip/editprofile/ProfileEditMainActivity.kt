package com.clip.editprofile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.clip.domain.model.Router
import com.clip.editprofile.nav.ProfileEditNav
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileEditMainActivity : ComponentActivity() {
    private val viewModel: ProfileEditViewModel by viewModels()

    @Inject
    lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProfileEditNav(
                viewModel,
                goToLoginPage = { router.navigation(NAV_LOGOUT) },
                goToMainPage = { finish() }
            )
        }
    }
}