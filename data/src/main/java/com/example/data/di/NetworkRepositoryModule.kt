package com.example.data.di

import com.example.data.repositoryImpl.NetworkRepositoryImpl
import com.sungil.domain.repository.NetworkRepository
import com.sungil.network.FirebaseRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent ::class)
class NetworkRepositoryModule {
    @Provides
    fun provideNetWorkRepo(firebaseRepo: FirebaseRepo) : NetworkRepository{
        return NetworkRepositoryImpl(firebaseRepo)
    }
}