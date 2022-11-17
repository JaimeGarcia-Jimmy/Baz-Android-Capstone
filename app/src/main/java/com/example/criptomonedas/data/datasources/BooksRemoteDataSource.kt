package com.example.criptomonedas.data.datasources

import com.example.criptomonedas.data.api.dto.AvailableBooksResponseDto

interface BooksRemoteDataSource {

    suspend fun getAvailableBooks(): AvailableBooksResponseDto
}