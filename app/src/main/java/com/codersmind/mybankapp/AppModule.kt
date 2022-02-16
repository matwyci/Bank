package com.codersmind.mybankapp

import com.codersmind.mybankapp.infrastructure.AppState
import com.codersmind.mybankapp.infrastructure.FakeDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)

class AppModule {

    private val appState = AppState()


    private val fakeDatabase = FakeDatabase

    @Provides
    fun provideAppState(): AppState = appState

    @Provides
    fun provideDatabase(): FakeDatabase = fakeDatabase
}