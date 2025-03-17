package com.sungil.login

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.sungil.domain.model.Router
import com.sungil.login.ui.LoginScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    private val viewModel: LoginViewModel by viewModels()

    @Inject
    lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginScreen(
                kakaoLogin = {
                    router.navigationToSMS("KAKAO")
                }
            )
        }

        addListener()
    }

    private fun addListener(){
        CoroutineScope(Dispatchers.Main).launch{
            viewModel.actionFlow.collect{
                when(it){
                    is LoginViewModel.Action.GetSuccess ->{
                        router.navigationToSMS("SignUp")
                    }
                    is LoginViewModel.Action.Error ->{
                        router.navigationToSMS("SignUp")
                    }
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.getToken()
        Log.d(javaClass.name.toString() , "The view is Pause")
    }

    override fun onResume() {
        super.onResume()
        Log.d(javaClass.name.toString() , "The view is resume")
    }

}