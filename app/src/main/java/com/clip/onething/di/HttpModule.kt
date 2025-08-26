package com.clip.onething.di

import com.clip.network.http.HttpApi
import com.clip.network.http.RetrofitFactory
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