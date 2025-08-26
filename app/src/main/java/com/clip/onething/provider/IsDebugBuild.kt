package com.clip.onething.provider

import com.clip.domain.model.DebugProvider
import com.clip.onething.BuildConfig
import javax.inject.Inject

class IsDebugBuild @Inject constructor() : DebugProvider {
    override fun provide(): Boolean = BuildConfig.DEBUG
}