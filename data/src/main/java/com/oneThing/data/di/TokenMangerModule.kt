package com.oneThing.data.di

import com.oneThing.data.tokenMangerImpl.TokenMangerControllerImpl
import com.oneThing.domain.repository.DatabaseRepository
import com.oneThing.domain.repository.NetworkRepository
import com.oneThing.domain.tokenManger.TokenMangerController
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