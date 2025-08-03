package auth

import android.app.Activity
import com.kakao.sdk.auth.model.Prompt
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class SNSLoginImpl @Inject constructor() : SNSLogin {

    override suspend fun checkKAKAOLogin(activity: Activity): Boolean {
        return UserApiClient.instance.isKakaoTalkLoginAvailable(activity)
    }

    override suspend fun kakaoWebLogin(activity: Activity): Result<String> {
        return suspendCancellableCoroutine { cont ->
            UserApiClient.instance.loginWithKakaoAccount(
                context = activity,
                prompts = listOf(Prompt.LOGIN)
            ) { token, error ->
                when {
                    error != null -> cont.resume(Result.failure(error))
                    token != null -> {
                        UserApiClient.instance.me { user, throwable ->
                            when {
                                user == null -> cont.resume(
                                    Result.failure(
                                        throwable ?: Exception("User data is null")
                                    )
                                )

                                throwable != null -> cont.resume(Result.failure(throwable))
                                else -> cont.resume(Result.success(user.id.toString()))
                            }
                        }
                    }

                    else -> cont.resume(Result.failure(Exception("Unknown error occurred")))
                }
            }
        }
    }

    override suspend fun kakaoSdkLogin(activity: Activity): Result<String> {
        return suspendCancellableCoroutine { cont ->
            UserApiClient.instance.loginWithKakaoTalk(context = activity) { token, error ->
                when {
                    error != null -> cont.resume(Result.failure(error))
                    token != null -> {
                        UserApiClient.instance.me { user, throwable ->
                            when {
                                user == null -> cont.resume(
                                    Result.failure(
                                        throwable ?: Exception("User data is null")
                                    )
                                )

                                throwable != null -> cont.resume(Result.failure(throwable))
                                else -> cont.resume(Result.success(user.id.toString()))
                            }
                        }
                    }

                    else -> cont.resume(Result.failure(Exception("Unknown error occurred")))
                }
            }
        }
    }
}

