package com.example.data.di

import com.example.data.tokenMangerImpl.TokenMangerControllerImpl
import com.sungil.domain.repository.DatabaseRepository
import com.sungil.domain.repository.NetworkRepository
import com.sungil.domain.tokenManger.TokenMangerController
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