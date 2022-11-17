package com.example.criptomonedas.data.datasources

import com.example.criptomonedas.data.api.dto.OrderBookResponseDto
import com.example.criptomonedas.data.api.dto.TickerResponseDto
import io.reactivex.rxjava3.core.Observable

interface BookOrdersRemoteDataSource {

    suspend fun getBookByTickerObservable(bookId: String): Observable<TickerResponseDto>

    suspend fun getOrdersByBook(bookId: String): OrderBookResponseDto
}