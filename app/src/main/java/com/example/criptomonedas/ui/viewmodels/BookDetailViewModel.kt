package com.example.criptomonedas.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.criptomonedas.data.Resource
import com.example.criptomonedas.data.entities.Ask
import com.example.criptomonedas.data.entities.Bid
import com.example.criptomonedas.data.entities.Book
import com.example.criptomonedas.data.repositories.BookOrdersRepository
import com.example.criptomonedas.data.repositories.BookOrdersRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    private val bookOrdersRepository: BookOrdersRepository,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private var _bookId: String = ""

    private val _book = MutableStateFlow<Resource<Book>>(Resource.loading())
    val book: StateFlow<Resource<Book>> = _book

    private val _asks = MutableStateFlow<Resource<List<Ask>>>(Resource.loading())
    val asks: StateFlow<Resource<List<Ask>>> = _asks

    private val _bids = MutableStateFlow<Resource<List<Bid>>>(Resource.loading())
    val bids: StateFlow<Resource<List<Bid>>> = _bids

    fun getBookById(bookId: String) {
        _bookId = bookId

        viewModelScope.launch(ioDispatcher) {
            bookOrdersRepository.getBookById(bookId).collect() {
                _book.value = it
            }
        }

        viewModelScope.launch(ioDispatcher) {
            bookOrdersRepository.getAsksByBook(bookId).collect() {
                _asks.value = it
            }
        }

        viewModelScope.launch(ioDispatcher) {
            bookOrdersRepository.getBidsByBook(bookId).collect() {
                _bids.value = it
            }
        }
    }

    fun updateBook() {
        viewModelScope.launch(ioDispatcher) {
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

    override fun onCleared() {
        super.onCleared()
    }
}
