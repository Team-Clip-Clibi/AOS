package com.sungil.onething.di

import com.sungil.network.http.HttpApi
import com.sungil.network.http.RetrofitFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class HttpModule {
    companion object {
        @Singleton
        @Provides
        fun provideRetrofit(): Retrofit = RetrofitFactory.create()

        @Singleton
        @Provides
        fun provideHttpApi(retrofit: Retrofit) = retrofit.create(HttpApi::class.java)
    }
}