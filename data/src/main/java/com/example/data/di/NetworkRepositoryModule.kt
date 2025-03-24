package com.example.data.di

import com.example.data.repositoryImpl.NetworkRepositoryImpl
import com.sungil.domain.repository.NetworkRepository
import com.sungil.network.FirebaseRepo
import com.sungil.network.http.HttpApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent ::class)
class NetworkRepositoryModule {

    @Provides
    @Singleton
    fun provideNetWorkRepo(firebaseRepo: FirebaseRepo , networkApi : HttpApi) : NetworkRepository{
        return NetworkRepositoryImpl(firebaseRepo , networkApi)
    }

}