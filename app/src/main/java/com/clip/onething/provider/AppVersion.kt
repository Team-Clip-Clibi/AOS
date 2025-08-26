package com.clip.onething.provider

import com.clip.domain.model.AppVersionProvider
import com.clip.onething.BuildConfig
import javax.inject.Inject

class AppVersion @Inject constructor() : AppVersionProvider {
    override fun provideAppVersion(): String = BuildConfig.VERSION_NAME
}