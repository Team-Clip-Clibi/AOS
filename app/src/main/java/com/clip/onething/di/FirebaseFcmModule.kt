package com.clip.onething.di

import com.clip.fcm.FirebaseToken
import com.clip.fcm.FirebaseTokenImpl
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