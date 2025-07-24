package com.oneThing.data.di

import com.oneThing.data.repositoryImpl.NetworkRepositoryImpl
import com.oneThing.fcm.FirebaseToken
import com.oneThing.database.token.TokenManager
import com.oneThing.domain.repository.NetworkRepository
import com.oneThing.network.FirebaseSMSRepo
import com.oneThing.network.http.HttpApi
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