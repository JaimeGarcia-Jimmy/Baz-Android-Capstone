package com.example.criptomonedas.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.criptomonedas.data.Resource
import com.example.criptomonedas.data.database.CryptoDatabase
import com.example.criptomonedas.data.entities.Book
import com.example.criptomonedas.data.repositories.BooksRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AvailableBooksViewModel(application: Application): AndroidViewModel(application) {

    private val cryptoDatabase = CryptoDatabase.getInstance(application.applicationContext)
    private val booksRepository = BooksRepository(cryptoDatabase.booksDao())

    private val _booksList = MutableStateFlow<Resource<List<Book>>>( Resource.loading() )
    val booksList: StateFlow<Resource<List<Book>>> = _booksList

    fun updateBooks() {
        viewModelScope.launch {
            _booksList.value = Resource.loading()

            try {
                booksRepository.updateBooks()
            } catch (e: Exception) {
                _booksList.value = Resource.error("Error al actualizar", e)
            }
        }
    }

    init {
        viewModelScope.launch {
            booksRepository.getBooks().collect() {
                _booksList.value = it
            }
        }
    }
}