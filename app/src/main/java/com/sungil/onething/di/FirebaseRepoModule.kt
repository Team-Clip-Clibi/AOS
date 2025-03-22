package com.sungil.onething.di

import android.content.Context
import com.sungil.network.FirebaseRepo
import com.sungil.network.FirebaseRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class FirebaseRepoModule {
    @Provides
    fun provideFirebaseRepo(@ApplicationContext context : Context) : FirebaseRepo{
        return FirebaseRepoImpl(context)
    }
}