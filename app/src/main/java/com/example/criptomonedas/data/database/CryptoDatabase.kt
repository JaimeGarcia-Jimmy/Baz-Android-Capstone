package com.example.criptomonedas.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.criptomonedas.data.database.dao.BooksDao
import com.example.criptomonedas.data.database.entities.AskDbEntity
import com.example.criptomonedas.data.database.entities.BidDbEntity
import com.example.criptomonedas.data.database.entities.BookDbEntity

@Database(
    entities = [
        BookDbEntity::class,
        AskDbEntity::class,
        BidDbEntity::class
    ],
    version = 1
)
abstract class CryptoDatabase : RoomDatabase() {

    abstract fun booksDao(): BooksDao
}
