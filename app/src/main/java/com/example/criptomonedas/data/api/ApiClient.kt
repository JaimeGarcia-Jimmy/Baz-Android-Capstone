package com.example.criptomonedas.data.api

import com.example.criptomonedas.data.api.services.BooksService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private val retrofitClient: OkHttpClient = OkHttpClient()

    private val retrofitInstance = Retrofit.Builder()
        .client(retrofitClient)
        .baseUrl("https://api.bitso.com/v3/")
        .addConverterFactory( GsonConverterFactory.create() )
        .build()

    fun booksService() = retrofitInstance.create( BooksService::class.java )
}