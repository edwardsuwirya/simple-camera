package com.enigmacamp.mycameraactivity.di.module

import com.enigmacamp.mycameraactivity.data.api.CameraApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {
    @Singleton
    @Provides
    fun provideCalculatorApi(retrofit: Retrofit): CameraApi {
        return retrofit.create(CameraApi::class.java)
    }
}