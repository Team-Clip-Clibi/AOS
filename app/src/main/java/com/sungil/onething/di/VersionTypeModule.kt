package com.sungil.onething.di

import com.sungil.domain.model.AppVersionProvider
import com.sungil.onething.provider.AppVersion
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object VersionTypeModule {
    @Provides
    @Singleton
    fun provideAppVersion () : AppVersionProvider = AppVersion()
}