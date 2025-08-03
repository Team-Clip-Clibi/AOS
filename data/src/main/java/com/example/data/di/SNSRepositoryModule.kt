package com.example.data.di

import auth.SNSLogin
import com.example.data.repositoryImpl.SNSLoginRepositoryImpl
import com.sungil.domain.repository.SNSLoginRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SNSRepositoryModule {
    @Provides
    @Singleton
    fun provideSNSRepository(snsLogin: SNSLogin): SNSLoginRepository {
        return SNSLoginRepositoryImpl(snsLogin)
    }
}