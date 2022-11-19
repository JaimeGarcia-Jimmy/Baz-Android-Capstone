package com.example.criptomonedas.data.repositories

import com.example.criptomonedas.data.Resource
import com.example.criptomonedas.data.datasources.BooksLocalDataSource
import com.example.criptomonedas.data.datasources.BooksRemoteDataSource
import com.example.criptomonedas.data.entities.Book
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BooksRepositoryImpl @Inject constructor(
    private val booksLocalDataSource: BooksLocalDataSource,
    private val booksRemoteDataSource: BooksRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher
): BooksRepository {

    override fun getBooks(): Flow<Resource<List<Book>>> =
        flow {
            // Al iniciar la vista se emite un recuros de tipo cargando
            emit(Resource.loading())

            // Se inicia el flujo de la base de datos
            val dbFlow: Flow<Resource<List<Book>>> = booksLocalDataSource.getAllBooksFlow().map { booksDbList ->
                Resource.success(booksDbList.map { it.toEntity() })
            }

            try {
                updateBooks()
            } catch (e: Exception) {
                emit(Resource.error("La peticion fallo", e))
            }

            emitAll(dbFlow)
        }.flowOn(ioDispatcher)

    override suspend fun updateBooks() =
        withContext(ioDispatcher) {
            // Peticion al api remoto
            val remoteSourceBooks = booksRemoteDataSource.getAvailableBooks().payload
            // Actualizar la base de datos local con informacion remota
            booksLocalDataSource.insertAllBooks(
                remoteSourceBooks.map {
                    it.toDbEntity()
                }
            )
        }
}
