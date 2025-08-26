package com.clip.data.di

import com.clip.data.repositoryImpl.NetworkRepositoryImpl
import com.clip.fcm.FirebaseToken
import com.clip.database.token.TokenManager
import com.clip.domain.repository.NetworkRepository
import com.clip.network.FirebaseSMSRepo
import com.clip.network.http.HttpApi
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