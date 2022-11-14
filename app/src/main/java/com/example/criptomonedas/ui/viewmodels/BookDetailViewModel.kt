package com.example.criptomonedas.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.criptomonedas.data.Resource
import com.example.criptomonedas.data.database.CryptoDatabase
import com.example.criptomonedas.data.entities.Ask
import com.example.criptomonedas.data.entities.Bid
import com.example.criptomonedas.data.entities.Book
import com.example.criptomonedas.data.repositories.BookOrdersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    private val bookOrdersRepository: BookOrdersRepository
): ViewModel() {

    private var _bookId: String = ""

    private val _book = MutableStateFlow<Resource<Book>>( Resource.loading() )
    val book: StateFlow<Resource<Book>> = _book

    private val _asks = MutableStateFlow<Resource<List<Ask>>>( Resource.loading() )
    val asks: StateFlow<Resource<List<Ask>>> = _asks

    private val _bids = MutableStateFlow<Resource<List<Bid>>>( Resource.loading() )
    val bids: StateFlow<Resource<List<Bid>>> = _bids

    fun getBookById(bookId: String) {

        _bookId = bookId

        viewModelScope.launch {
            bookOrdersRepository.getBookById(bookId).collect() {
                _book.value = it
            }
        }

        viewModelScope.launch {
            bookOrdersRepository.getAsksByBook(bookId).collect() {
                _asks.value = it
            }
        }

        viewModelScope.launch {
            bookOrdersRepository.getBidsByBook(bookId).collect() {
                _bids.value = it
            }
        }
    }

    fun updateBook() {
        viewModelScope.launch {
            _asks.value = Resource.loading()

            try {
                bookOrdersRepository.updateBook(_bookId)
                bookOrdersRepository.updateAsks(_bookId)
                bookOrdersRepository.updateBids(_bookId)
            } catch (e: Exception) {
                _asks.value = Resource.error("Error al actualizar", e)
            }
        }
    }
}