package com.oneThing.onething.di

import com.oneThing.database.room.dao.TokenDao
import com.oneThing.database.token.TokenManager
import com.oneThing.database.token.TokenManagerImpl
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