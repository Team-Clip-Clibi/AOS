package com.sungil.onething.provider

import com.sungil.domain.model.AppVersionProvider
import com.sungil.onething.BuildConfig
import javax.inject.Inject

class AppVersion @Inject constructor() : AppVersionProvider {
    override fun provideAppVersion(): String = BuildConfig.VERSION_NAME
}