package com.example.criptomonedas.di

import com.example.criptomonedas.data.datasources.BookOrdersLocalDataSourceImpl
import com.example.criptomonedas.data.datasources.BookOrdersLocalDatasource
import com.example.criptomonedas.data.datasources.BookOrdersRemoteDataSource
import com.example.criptomonedas.data.datasources.BookOrdersRemoteDataSourceImpl
import com.example.criptomonedas.data.datasources.BooksLocalDataSource
import com.example.criptomonedas.data.datasources.BooksLocalDataSourceImpl
import com.example.criptomonedas.data.datasources.BooksRemoteDataSource
import com.example.criptomonedas.data.datasources.BooksRemoteDataSourceImpl
import com.example.criptomonedas.data.repositories.BookOrdersRepository
import com.example.criptomonedas.data.repositories.BookOrdersRepositoryImpl
import com.example.criptomonedas.data.repositories.BooksRepository
import com.example.criptomonedas.data.repositories.BooksRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BooksModule {

    @Binds
    abstract fun providesBooksRepository(impl: BooksRepositoryImpl): BooksRepository

    @Binds
    abstract fun providesBookOrdersRepository(impl: BookOrdersRepositoryImpl): BookOrdersRepository

    @Binds
    abstract fun providesBooksRemoteDataSource(impl: BooksRemoteDataSourceImpl): BooksRemoteDataSource

    @Binds
    abstract fun providesBooksLocalDataSource(impl: BooksLocalDataSourceImpl): BooksLocalDataSource

    @Binds
    abstract fun providesBookOrdersRemoteDataSource(impl: BookOrdersRemoteDataSourceImpl): BookOrdersRemoteDataSource

    @Binds
    abstract fun providesBookOrdersLocalDataSource(impl: BookOrdersLocalDataSourceImpl): BookOrdersLocalDatasource
}