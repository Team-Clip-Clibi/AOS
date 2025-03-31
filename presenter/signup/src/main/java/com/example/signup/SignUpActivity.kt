package com.example.signup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.signup.nav.SignUpNavigation
import com.sungil.domain.model.Router
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignUpActivity : ComponentActivity() {
    private val viewModel: SignUpViewModel by viewModels()
    @Inject
    lateinit var router: Router
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SignUpNavigation(
                viewModel = viewModel,
                clear = {
                    router.navigation(NAV_LOGIN)
                },
                activity = this
            )
        }
    }

}