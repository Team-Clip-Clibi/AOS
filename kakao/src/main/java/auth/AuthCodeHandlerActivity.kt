package auth

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kakao.sdk.user.UserApiClient
import com.sungil.domain.model.Router
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import androidx.activity.viewModels
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.auth.model.Prompt
import com.sungil.domain.model.DebugProvider

@AndroidEntryPoint
class AuthCodeHandlerActivity : AppCompatActivity() {
    private val viewModel: SMSViewModel by viewModels()

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var debugProvider: DebugProvider

    val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e(javaClass.name.toString(), "카카오계정으로 로그인 실패", error)
        } else if (token != null) {
            Log.i(javaClass.name.toString(), "카카오계정으로 로그인 성공 ${token.accessToken}")
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val loginAction = if (debugProvider.provide()) ::startKAKAOWebLogin else ::startKakaoLogin
        loginAction()
        addListener()
    }


    private fun startKAKAOWebLogin() {
        UserApiClient.instance.loginWithKakaoAccount(this@AuthCodeHandlerActivity, prompts = listOf(Prompt.LOGIN) , callback = callback)
    }

    private fun startKakaoLogin() {
        UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
            if (error != null || token == null) {
                Log.e(javaClass.name.toString(), "error $error")
                viewModel.saveKaKaoId("3975324589")
                return@loginWithKakaoTalk
            }
            Log.i(javaClass.name.toString(), "로그인 성공 ${token.accessToken}")
            UserApiClient.instance.me { user, errorMessage ->
                if (user == null) {
                    Log.e(javaClass.name.toString(), "the user data is null $errorMessage")
                    return@me
                }
                Log.d(javaClass.name.toString(), "userId : ${user.id}")
                viewModel.saveKaKaoId(user.id.toString())
            }
        }
    }

    private fun addListener() {
        viewModel.uiState.observe(this) { result ->
            when (result) {
                is SMSViewModel.Action.AlreadySignUp -> {
                    router.navigation(NAV_MAIN)
                }

                is SMSViewModel.Action.SaveSuccess -> {
                    viewModel.checkAlreadySignUp(result.kakaoId)
                }

                is SMSViewModel.Action.NotSignUp -> {
                    router.navigation(NAV_SIGN_UP)
                }

                is SMSViewModel.Action.Error -> {
                    Toast.makeText(
                        this@AuthCodeHandlerActivity,
                        result.errorMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}