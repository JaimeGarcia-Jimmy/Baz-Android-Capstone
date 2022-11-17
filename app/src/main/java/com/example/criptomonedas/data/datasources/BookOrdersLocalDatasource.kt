package com.example.criptomonedas.data.datasources

import com.example.criptomonedas.data.database.entities.AskDbEntity
import com.example.criptomonedas.data.database.entities.BidDbEntity
import com.example.criptomonedas.data.database.entities.BookDbEntity
import kotlinx.coroutines.flow.Flow

interface BookOrdersLocalDatasource {

    fun getBookByIdFlow(bookId: String): Flow<BookDbEntity>

    fun updateBook(book: BookDbEntity): Unit

    fun getAsksByBookFlow(bookId: String): Flow<List<AskDbEntity>>

    fun deleteAndInsertAsksByBook(bookId: String, newAsks: List<AskDbEntity>): Unit

    fun getBidsByBookFlow(bookId: String): Flow<List<BidDbEntity>>

    fun deleteAndInsertBidsByBook(bookId: String, newBids: List<BidDbEntity>): Unit
}