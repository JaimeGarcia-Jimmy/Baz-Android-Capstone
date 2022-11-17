package com.example.criptomonedas.di

import com.example.criptomonedas.data.datasources.BooksLocalDataSource
import com.example.criptomonedas.data.datasources.BooksLocalDataSourceImpl
import com.example.criptomonedas.data.datasources.BooksRemoteDataSource
import com.example.criptomonedas.data.datasources.BooksRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BooksModule {

    @Binds
    abstract fun providesBooksRemoteDataSource(impl: BooksRemoteDataSourceImpl): BooksRemoteDataSource

    @Binds
    abstract fun providesBooksLocalDataSource(impl: BooksLocalDataSourceImpl): BooksLocalDataSource
}