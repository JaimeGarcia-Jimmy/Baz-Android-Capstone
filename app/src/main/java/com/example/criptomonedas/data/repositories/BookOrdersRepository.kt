package com.example.criptomonedas.data.repositories

import com.example.criptomonedas.data.Resource
import com.example.criptomonedas.data.api.ApiClient
import com.example.criptomonedas.data.database.dao.BooksDao
import com.example.criptomonedas.data.entities.Ask
import com.example.criptomonedas.data.entities.Bid
import com.example.criptomonedas.data.entities.Book
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext

class BookOrdersRepository(
    private val booksDao: BooksDao
) {

    fun getBookById(bookId: String): Flow<Resource<Book>> = flow {

        emit(Resource.loading())

        val dbFlow: Flow<Resource<Book>> = booksDao.getBookByIdFlow(bookId).map {
            Resource.success(it.toEntity())
        }

        try {
            updateBook(bookId)
        } catch (e: Exception) {
            emit(Resource.error("La peticion fallo", e))
        }

        emitAll(dbFlow)
    }.flowOn(Dispatchers.IO)

    suspend fun updateBook(bookId: String) =
        withContext(Dispatchers.IO) {

            val remoteSourceBook = ApiClient.booksService().doGetBookByTickerRequest(bookId).payload

            booksDao.updateBook(
                remoteSourceBook.toDbEntity()
            )
        }

    fun getAsksByBook(bookId: String): Flow<Resource<List<Ask>>> = flow {

        emit(Resource.loading())

        val dbFlow: Flow<Resource<List<Ask>>> = booksDao.getAsksByBookFlow(bookId).map {
            Resource.success(it.map { it.toEntity() })
        }

        try {
            updateAsks(bookId)
        } catch (e: Exception) {
            emit(Resource.error("La peticion fallo", e))
        }

        emitAll(dbFlow)
    }.flowOn(Dispatchers.IO)

    suspend fun updateAsks(bookId: String) =
        withContext(Dispatchers.IO) {

            val remoteSourceAsksList = ApiClient.booksService().doGetOrdersByBookRequest(bookId).payload.asks

            booksDao.deleteAndInsertAsksByBook(
                bookId,
                remoteSourceAsksList.map { it.toDbEntity() }
            )
        }

    fun getBidsByBook(bookId: String): Flow<Resource<List<Bid>>> = flow {

        emit(Resource.loading())

        val dbFlow: Flow<Resource<List<Bid>>> = booksDao.getBidsByBookFlow(bookId).map {
            Resource.success(it.map { it.toEntity() })
        }

        try {
            updateBids(bookId)
        } catch (e: Exception) {
            emit(Resource.error("La peticion fallo", e))
        }

        emitAll(dbFlow)
    }.flowOn(Dispatchers.IO)

    suspend fun updateBids(bookId: String) =
        withContext(Dispatchers.IO) {

            val remoteSourceBidsList = ApiClient.booksService().doGetOrdersByBookRequest(bookId).payload.bids

            booksDao.deleteAndInsertBidsByBook(
                bookId,
                remoteSourceBidsList.map { it.toDbEntity() }
            )
        }
}