package com.enigmacamp.mycameraactivity.di.module

import com.enigmacamp.mycameraactivity.data.repository.CameraRepository
import com.enigmacamp.mycameraactivity.data.repository.CameraRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepoModule {
    @Binds
    abstract fun bindsCameraRepo(cameraRepositoryImpl: CameraRepositoryImpl): CameraRepository
}