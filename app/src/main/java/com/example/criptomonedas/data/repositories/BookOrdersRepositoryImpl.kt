package com.example.criptomonedas.data.repositories

import com.example.criptomonedas.data.Resource
import com.example.criptomonedas.data.api.dto.TickerResponseDto
import com.example.criptomonedas.data.datasources.BookOrdersLocalDatasource
import com.example.criptomonedas.data.datasources.BookOrdersRemoteDataSource
import com.example.criptomonedas.data.entities.Ask
import com.example.criptomonedas.data.entities.Bid
import com.example.criptomonedas.data.entities.Book
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BookOrdersRepositoryImpl @Inject constructor(
    private val bookOrdersLocalDatasource: BookOrdersLocalDatasource,
    private val bookOrdersRemoteDataSource: BookOrdersRemoteDataSource,
    private val compositeDisposable: CompositeDisposable
): BookOrdersRepository {

    override fun getBookById(bookId: String): Flow<Resource<Book>> = flow {
        emit(Resource.loading())

        val dbFlow: Flow<Resource<Book>> = bookOrdersLocalDatasource.getBookByIdFlow(bookId).map {
            Resource.success(it.toEntity())
        }

        try {
            updateBook(bookId)
        } catch (e: Exception) {
            emit(Resource.error("La peticion fallo", e))
            e.printStackTrace()
        }

        emitAll(dbFlow)
    }.flowOn(Dispatchers.IO)

    override suspend fun updateBook(bookId: String) =
        withContext(Dispatchers.IO) {
            val disposable = bookOrdersRemoteDataSource.getBookByTickerObservable(bookId)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(::handleGetBookByTickerSuccess, ::handleGetBookByTicketError)
            compositeDisposable.add(disposable)
        }

    override fun handleGetBookByTickerSuccess(response: TickerResponseDto) {
        val remoteSourceBook = response.payload
        bookOrdersLocalDatasource.updateBook(
            remoteSourceBook.toDbEntity()
        )
        compositeDisposable.clear()
    }

    override fun handleGetBookByTicketError(error: Throwable) {
        error.printStackTrace()
        compositeDisposable.clear()
    }

    override fun getAsksByBook(bookId: String): Flow<Resource<List<Ask>>> = flow {
        emit(Resource.loading())

        val dbFlow: Flow<Resource<List<Ask>>> = bookOrdersLocalDatasource.getAsksByBookFlow(bookId).map { asksDbList ->
            Resource.success(asksDbList.map { it.toEntity() })
        }

        try {
            updateAsks(bookId)
        } catch (e: Exception) {
            emit(Resource.error("La peticion fallo", e))
        }

        emitAll(dbFlow)
    }.flowOn(Dispatchers.IO)

    override suspend fun updateAsks(bookId: String) =
        withContext(Dispatchers.IO) {
            val remoteSourceAsksList = bookOrdersRemoteDataSource.getOrdersByBook(bookId).payload.asks

            bookOrdersLocalDatasource.deleteAndInsertAsksByBook(
                bookId,
                remoteSourceAsksList.map { it.toDbEntity() }
            )
        }

    override fun getBidsByBook(bookId: String): Flow<Resource<List<Bid>>> = flow {
        emit(Resource.loading())

        val dbFlow: Flow<Resource<List<Bid>>> = bookOrdersLocalDatasource.getBidsByBookFlow(bookId).map { bidsDbList ->
            Resource.success(bidsDbList.map { it.toEntity() })
        }

        try {
            updateBids(bookId)
        } catch (e: Exception) {
            emit(Resource.error("La peticion fallo", e))
        }

        emitAll(dbFlow)
    }.flowOn(Dispatchers.IO)

    override suspend fun updateBids(bookId: String) =
        withContext(Dispatchers.IO) {
            val remoteSourceBidsList = bookOrdersRemoteDataSource.getOrdersByBook(bookId).payload.bids

            bookOrdersLocalDatasource.deleteAndInsertBidsByBook(
                bookId,
                remoteSourceBidsList.map { it.toDbEntity() }
            )
        }
}
