package com.clip.domain.model

interface AppVersionProvider {
    fun provideAppVersion(): String
}