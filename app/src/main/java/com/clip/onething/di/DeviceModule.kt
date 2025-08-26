package com.clip.onething.di

import android.content.Context
import com.clip.device.Device
import com.clip.device.DeviceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DeviceModule {
    @Provides
    fun provideDeviceModule(@ApplicationContext context: Context) : Device {
        return DeviceImpl(context)
    }
}