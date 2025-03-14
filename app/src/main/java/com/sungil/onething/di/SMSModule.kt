package com.sungil.onething.di

import android.content.Context
import com.sungil.domain.model.Router
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@InstallIn(ActivityComponent::class)
@Module
abstract class SMSModule {
    companion object {
        @Provides
        fun provideSMSRouter(@ApplicationContext context: Context): Router {
            return SMSRouter(context)
        }
    }
}