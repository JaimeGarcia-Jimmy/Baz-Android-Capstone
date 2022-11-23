package com.example.criptomonedas.di

import android.content.Context
import androidx.room.Room
import com.example.criptomonedas.data.database.CryptoDatabase
import com.example.criptomonedas.data.database.dao.BooksDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideCryptoDatabase(@ApplicationContext context: Context): CryptoDatabase {
        return Room.databaseBuilder(
            context,
            CryptoDatabase::class.java,
            "cripto_db"
        )
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    fun providesBooksDao(criptoDatabase: CryptoDatabase): BooksDao {
        return criptoDatabase.booksDao()
    }
}
