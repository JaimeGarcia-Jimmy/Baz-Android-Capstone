package com.example.criptomonedas.data.datasources

import com.example.criptomonedas.data.database.dao.BooksDao
import com.example.criptomonedas.data.database.entities.BookDbEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BooksLocalDataSourceImpl @Inject constructor(
    private val booksDao: BooksDao
): BooksLocalDataSource {
    override suspend fun getAllBooksFlow(): Flow<List<BookDbEntity>> {
        return booksDao.getAllBooksFlow()
    }

    override suspend fun insertAllBooks(books: List<BookDbEntity>) {
        booksDao.insertAllBooks(books)
    }
}