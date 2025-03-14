package com.sungil.onething.di

import android.content.Context
import com.sungil.database.SharedPreference
import com.sungil.database.SharedPreferenceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    fun provideDatabase(@ApplicationContext context : Context) : SharedPreference{
        return SharedPreferenceImpl(context)
    }
}