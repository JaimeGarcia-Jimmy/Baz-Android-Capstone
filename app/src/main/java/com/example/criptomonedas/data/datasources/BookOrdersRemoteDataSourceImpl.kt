package com.example.criptomonedas.data.datasources

import com.example.criptomonedas.data.api.dto.OrderBookResponseDto
import com.example.criptomonedas.data.api.dto.TickerResponseDto
import com.example.criptomonedas.data.api.services.BooksService
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class BookOrdersRemoteDataSourceImpl @Inject constructor(
    private val booksService: BooksService
): BookOrdersRemoteDataSource {
    override suspend fun getBookByTickerObservable(bookId: String): Observable<TickerResponseDto> {
        return booksService.doGetBookByTickerRequestObservable(bookId)
    }

    override suspend fun getOrdersByBook(bookId: String): OrderBookResponseDto {
        return booksService.doGetOrdersByBookRequest(bookId)
    }
}