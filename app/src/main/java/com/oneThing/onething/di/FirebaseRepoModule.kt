package com.oneThing.onething.di

import android.content.Context
import com.oneThing.network.FirebaseSMSRepo
import com.oneThing.network.FirebaseSMSRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FirebaseRepoModule {

    @Provides
    @Singleton
    fun provideFirebaseRepo(@ApplicationContext context : Context) : FirebaseSMSRepo{
        return FirebaseSMSRepoImpl(context)
    }
}