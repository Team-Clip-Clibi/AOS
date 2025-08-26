package com.clip.onething.di

import com.clip.domain.model.DebugProvider
import com.clip.onething.provider.IsDebugBuild
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