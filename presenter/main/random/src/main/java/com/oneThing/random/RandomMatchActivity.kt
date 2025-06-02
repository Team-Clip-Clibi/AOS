package com.oneThing.random

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.sungil.domain.model.Router
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RandomMatchActivity : ComponentActivity() {
    @Inject
    lateinit var router: Router
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}