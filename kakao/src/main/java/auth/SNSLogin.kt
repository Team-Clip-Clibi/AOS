package auth

import android.app.Activity

interface SNSLogin {
    suspend fun checkKAKAOLogin(activity : Activity) : Boolean
    suspend fun kakaoWebLogin(activity : Activity): Result<String>
    suspend fun kakaoSdkLogin(activity: Activity): Result<String>
}