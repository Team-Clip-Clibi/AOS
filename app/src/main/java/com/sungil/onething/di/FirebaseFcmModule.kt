package com.sungil.onething.di

import com.example.fcm.FirebaseFCM
import com.example.fcm.FirebaseFCMImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FirebaseFcmModule {

    @Provides
    fun provideFcmModule(): FirebaseFCM {
        return FirebaseFCMImpl()
    }

}