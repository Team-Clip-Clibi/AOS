package com.example.data.di

import com.example.data.repositoryImpl.NetworkRepositoryImpl
import com.example.fcm.FirebaseToken
import com.sungil.database.token.TokenManager
import com.sungil.domain.repository.NetworkRepository
import com.sungil.network.FirebaseSMSRepo
import com.sungil.network.http.HttpApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkRepositoryModule {

    @Provides
    @Singleton
    fun provideNetWorkRepo(
        firebaseSMSRepo: FirebaseSMSRepo,
        networkApi: HttpApi,
        fcm: FirebaseToken,
        tokenManager: TokenManager,
    ): NetworkRepository {
        return NetworkRepositoryImpl(firebaseSMSRepo, networkApi, fcm, tokenManager)
    }

}