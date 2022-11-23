package com.example.criptomonedas.data.repositories

import android.content.Context
import com.example.criptomonedas.R
import com.example.criptomonedas.data.Resource
import com.example.criptomonedas.data.api.dto.TickerResponseDto
import com.example.criptomonedas.data.datasources.BookOrdersLocalDatasource
import com.example.criptomonedas.data.datasources.BookOrdersRemoteDataSource
import com.example.criptomonedas.data.entities.Ask
import com.example.criptomonedas.data.entities.Bid
import com.example.criptomonedas.data.entities.Book
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineDispatcher
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
    private val compositeDisposable: CompositeDisposable,
    private val ioDispatcher: CoroutineDispatcher,
    private val subscribeIoScheduler: Scheduler,
    private val observeIoScheduler: Scheduler,
    @ApplicationContext private val appContext: Context
): BookOrdersRepository {

    override fun getBookById(bookId: String): Flow<Resource<Book>> = flow {
        emit(Resource.loading())

        val dbFlow: Flow<Resource<Book>> = bookOrdersLocalDatasource.getBookByIdFlow(bookId).map {
            Resource.success(it.toEntity())
        }

        try {
            updateBook(bookId)
        } catch (e: Exception) {
            emit(Resource.error(appContext.getString(R.string.http_request_error_message), e))
            e.printStackTrace()
        }

        emitAll(dbFlow)
    }.flowOn(ioDispatcher)

    override fun updateBook(bookId: String) {
        val disposable = bookOrdersRemoteDataSource.getBookByTickerObservable(bookId)
            .subscribeOn(subscribeIoScheduler)
            .observeOn(observeIoScheduler)
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
            emit(Resource.error(appContext.getString(R.string.http_request_error_message), e))
        }

        emitAll(dbFlow)
    }.flowOn(ioDispatcher)

    override suspend fun updateAsks(bookId: String) =
        withContext(ioDispatcher) {
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
            emit(Resource.error(appContext.getString(R.string.http_request_error_message), e))
        }

        emitAll(dbFlow)
    }.flowOn(ioDispatcher)

    override suspend fun updateBids(bookId: String) =
        withContext(ioDispatcher) {
            val remoteSourceBidsList = bookOrdersRemoteDataSource.getOrdersByBook(bookId).payload.bids

            bookOrdersLocalDatasource.deleteAndInsertBidsByBook(
                bookId,
                remoteSourceBidsList.map { it.toDbEntity() }
            )
        }
}
