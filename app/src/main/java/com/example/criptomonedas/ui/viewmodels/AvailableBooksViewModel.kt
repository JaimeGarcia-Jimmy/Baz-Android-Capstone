package com.example.criptomonedas.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.criptomonedas.data.Resource
import com.example.criptomonedas.data.database.CryptoDatabase
import com.example.criptomonedas.data.entities.Book
import com.example.criptomonedas.data.repositories.BooksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AvailableBooksViewModel @Inject constructor(
    private val booksRepository: BooksRepository
): ViewModel() {

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

    /*class PokedexViewModelFactory(
        private val repository: PokedexRepository,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(PokedexRepository::class.java).newInstance(repository)
        }
    }*/
}