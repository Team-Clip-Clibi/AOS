package com.clip.onething.di

import com.clip.database.room.dao.TokenDao
import com.clip.database.token.TokenManager
import com.clip.database.token.TokenManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TokenModule {

    @Provides
    @Singleton
    fun provideTokenManager(tokenDao: TokenDao): TokenManager {
        return TokenManagerImpl(tokenDao)
    }
}