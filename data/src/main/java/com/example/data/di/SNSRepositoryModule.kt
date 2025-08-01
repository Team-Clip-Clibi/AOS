package com.example.data.di

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
    fun provideSNSRepository(): SNSLoginRepository {
        return SNSLoginRepositoryImpl()
    }
}