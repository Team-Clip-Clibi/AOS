package com.sungil.editprofile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.sungil.editprofile.nav.ProfileEditNav
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileEditMainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProfileEditNav()
        }
    }
}