package com.oneThing.onething.di

import com.oneThing.fcm.FirebaseToken
import com.oneThing.fcm.FirebaseTokenImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class FirebaseFcmModule {

    @Provides
    fun provideFcmModule(): FirebaseToken {
        return FirebaseTokenImpl()
    }

}