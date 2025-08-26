package com.clip.data.di

import com.clip.data.repositoryImpl.DeviceRepositoryImpl
import com.clip.device.Device
import com.clip.domain.repository.DeviceRepository
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