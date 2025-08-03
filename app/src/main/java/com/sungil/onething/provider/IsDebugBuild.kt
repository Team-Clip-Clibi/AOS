package com.sungil.onething.provider

import com.sungil.domain.model.DebugProvider
import com.sungil.onething.BuildConfig
import javax.inject.Inject

class IsDebugBuild @Inject constructor() : DebugProvider {
    override fun provide(): Boolean = BuildConfig.DEBUG
}