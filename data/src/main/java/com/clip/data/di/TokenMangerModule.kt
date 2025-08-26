package com.clip.data.di

import com.clip.data.tokenMangerImpl.TokenMangerControllerImpl
import com.clip.domain.repository.DatabaseRepository
import com.clip.domain.repository.NetworkRepository
import com.clip.domain.tokenManger.TokenMangerController
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TokenMangerModule {
    @Provides
    @Singleton
    fun provideTokenManger(
        network: NetworkRepository,
        database: DatabaseRepository,
    ): TokenMangerController {
        return TokenMangerControllerImpl(
            network, database
        )
    }
}