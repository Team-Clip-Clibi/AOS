package com.sungil.onething.di

import com.sungil.domain.model.DebugProvider
import com.sungil.onething.provider.IsDebugBuild
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BuildTypeModule {

    @Provides
    @Singleton
    fun provideIsDebugBuild() : DebugProvider = IsDebugBuild()
}