package com.example.criptomonedas.data.datasources

import com.example.criptomonedas.data.api.dto.AvailableBooksResponseDto
import com.example.criptomonedas.data.api.services.BooksService
import javax.inject.Inject

class BooksRemoteDataSourceImpl @Inject constructor(
    private val booksService: BooksService
): BooksRemoteDataSource {

    override suspend fun getAvailableBooks(): AvailableBooksResponseDto {
        return booksService.doGetAvailableBooksRequest()
    }
}