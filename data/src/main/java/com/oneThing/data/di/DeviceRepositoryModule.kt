package com.oneThing.data.di

import com.oneThing.data.repositoryImpl.DeviceRepositoryImpl
import com.oneThing.device.Device
import com.oneThing.domain.repository.DeviceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent :: class)
class DeviceRepositoryModule {
    @Provides
    @Singleton
    fun provideDeviceRepository(device : Device) : DeviceRepository{
        return DeviceRepositoryImpl(device)
    }
}