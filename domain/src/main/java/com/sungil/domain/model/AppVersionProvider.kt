package com.sungil.domain.model

interface AppVersionProvider {
    fun provideAppVersion(): String
}