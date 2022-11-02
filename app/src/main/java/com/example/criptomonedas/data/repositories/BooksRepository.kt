package com.example.criptomonedas.data.repositories

import com.example.criptomonedas.data.Resource
import com.example.criptomonedas.data.api.ApiClient
import com.example.criptomonedas.data.database.dao.BooksDao
import com.example.criptomonedas.data.entities.Book
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext

class BooksRepository(
    private val booksDao: BooksDao
) {

    fun getBooks(): Flow<Resource<List<Book>>> =
        flow {

            //Al iniciar la vista se emite un recuros de tipo cargando
            emit(Resource.loading())

            //Se inicia el flujo de la base de datos
            val dbFlow: Flow<Resource<List<Book>>> = booksDao.getAllBooksFlow().map {
                Resource.success(it.map { it.toEntity() })
            }

            try {
                updateBooks()
            } catch (e: Exception) {
                emit(Resource.error("La peticion fallo", e))
            }

            emitAll(dbFlow)
        }.flowOn(Dispatchers.IO)

    suspend fun updateBooks() =
        withContext(Dispatchers.IO) {
            //Peticion al api remoto
            val remoteSourceBooks = ApiClient.booksService().doGetAvailableBooksRequest().payload
            //Actualizar la base de datos local con informacion remota
            booksDao.insertAllBooks(
                remoteSourceBooks.map {
                    it.toDbEntity()
                }
            )
        }
}