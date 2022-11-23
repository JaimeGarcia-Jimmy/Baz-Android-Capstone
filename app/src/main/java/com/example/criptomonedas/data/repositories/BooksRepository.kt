package com.example.criptomonedas.data.repositories

import com.example.criptomonedas.data.Resource
import com.example.criptomonedas.data.entities.Book
import kotlinx.coroutines.flow.Flow

interface BooksRepository {

    fun getBooks(): Flow<Resource<List<Book>>>

    suspend fun updateBooks(): Unit
}