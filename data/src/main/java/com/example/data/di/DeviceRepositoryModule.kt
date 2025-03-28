package com.example.data.di

import com.example.data.repositoryImpl.DeviceRepositoryImpl
import com.sungil.device.Device
import com.sungil.domain.repository.DeviceRepository
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