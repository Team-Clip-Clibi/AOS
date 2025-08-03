package com.sungil.onething.di

import auth.SNSLogin
import auth.SNSLoginImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class SnsModule {
    @Provides
    fun provideSnsLogin(): SNSLogin {
        return SNSLoginImpl()
    }
}