package com.example.criptomonedas.data.datasources

import com.example.criptomonedas.data.database.dao.BooksDao
import com.example.criptomonedas.data.database.entities.AskDbEntity
import com.example.criptomonedas.data.database.entities.BidDbEntity
import com.example.criptomonedas.data.database.entities.BookDbEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BookOrdersLocalDataSourceImpl @Inject constructor(
    private val booksDao: BooksDao
): BookOrdersLocalDatasource {
    override fun getBookByIdFlow(bookId: String): Flow<BookDbEntity> {
        return booksDao.getBookByIdFlow(bookId)
    }

    override fun updateBook(book: BookDbEntity) {
        booksDao.updateBook(book)
    }

    override fun getAsksByBookFlow(bookId: String): Flow<List<AskDbEntity>> {
        return booksDao.getAsksByBookFlow(bookId)
    }

    override fun deleteAndInsertAsksByBook(bookId: String, newAsks: List<AskDbEntity>) {
        booksDao.deleteAndInsertAsksByBook(bookId, newAsks)
    }

    override fun getBidsByBookFlow(bookId: String): Flow<List<BidDbEntity>> {
        return booksDao.getBidsByBookFlow(bookId)
    }

    override fun deleteAndInsertBidsByBook(bookId: String, newBids: List<BidDbEntity>) {
        booksDao.deleteAndInsertBidsByBook(bookId, newBids)
    }
}