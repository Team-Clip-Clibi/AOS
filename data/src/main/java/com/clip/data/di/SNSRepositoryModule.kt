package com.clip.data.di

import auth.SNSLogin
import com.clip.data.repositoryImpl.SNSLoginRepositoryImpl
import com.clip.domain.repository.SNSLoginRepository
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