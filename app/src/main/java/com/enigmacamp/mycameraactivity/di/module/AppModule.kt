package com.enigmacamp.mycameraactivity.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun provideBaseUrl() = "http://10.10.14.89:3005/camera/"

    @Singleton
    @Provides
    fun provideMoshiConverter(): MoshiConverterFactory {
        return MoshiConverterFactory.create()
    }

    @Singleton
    @Provides
    fun provideRetrofitInstance(
        baseUrl: String,
        moshi: MoshiConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(moshi)
            .build()
    }
}