package com.clip.onething.di

import android.content.Context
import com.clip.domain.model.Router
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RouterModule {
    companion object {
        @Provides
        fun provideSMSRouter(@ApplicationContext context: Context): Router {
            return Router(context)
        }
    }
}