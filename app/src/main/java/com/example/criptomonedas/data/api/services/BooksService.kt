package com.example.criptomonedas.data.api.services

import com.example.criptomonedas.data.api.dto.AvailableBooksResponseDto
import com.example.criptomonedas.data.api.dto.OrderBookResponseDto
import com.example.criptomonedas.data.api.dto.TickerResponseDto
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface BooksService {

    @GET("available_books")
    suspend fun doGetAvailableBooksRequest(): AvailableBooksResponseDto

    @GET("ticker")
    suspend fun doGetBookByTickerRequest(@Query("book") book: String): TickerResponseDto

    @GET("order_book")
    suspend fun doGetOrdersByBookRequest(@Query("book") book: String): OrderBookResponseDto

    @GET("available_books")
    fun doGetAvailableBooksRequestObservable(): Observable<AvailableBooksResponseDto>
}
