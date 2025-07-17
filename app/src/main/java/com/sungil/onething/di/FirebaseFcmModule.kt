package com.sungil.onething.di

import com.example.fcm.FirebaseToken
import com.example.fcm.FirebaseTokenImpl
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