package com.example.criptomonedas.data.repositories

import com.example.criptomonedas.data.Resource
import com.example.criptomonedas.data.api.dto.TickerResponseDto
import com.example.criptomonedas.data.entities.Ask
import com.example.criptomonedas.data.entities.Bid
import com.example.criptomonedas.data.entities.Book
import kotlinx.coroutines.flow.Flow

interface BookOrdersRepository {

    fun getBookById(bookId: String): Flow<Resource<Book>>

    suspend fun updateBook(bookId: String): Boolean

    fun handleGetBookByTickerSuccess(response: TickerResponseDto): Unit

    fun handleGetBookByTicketError(error: Throwable): Unit

    fun getAsksByBook(bookId: String): Flow<Resource<List<Ask>>>

    suspend fun updateAsks(bookId: String): Unit

    fun getBidsByBook(bookId: String): Flow<Resource<List<Bid>>>

    suspend fun updateBids(bookId: String): Unit
}