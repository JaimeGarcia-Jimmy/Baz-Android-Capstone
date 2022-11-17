package com.example.criptomonedas.data.datasources

import com.example.criptomonedas.data.database.entities.BookDbEntity
import kotlinx.coroutines.flow.Flow

interface BooksLocalDataSource {

    suspend fun getAllBooksFlow(): Flow<List<BookDbEntity>>

    suspend fun insertAllBooks(books: List<BookDbEntity>): Unit
}