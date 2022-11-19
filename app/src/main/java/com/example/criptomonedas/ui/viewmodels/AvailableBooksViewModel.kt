package com.example.criptomonedas.ui.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.criptomonedas.R
import com.example.criptomonedas.data.Resource
import com.example.criptomonedas.data.entities.Book
import com.example.criptomonedas.data.repositories.BooksRepository
import com.example.criptomonedas.data.repositories.BooksRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AvailableBooksViewModel @Inject constructor(
    private val booksRepository: BooksRepository,
    private val ioDispatcher: CoroutineDispatcher,
    @ApplicationContext private val appContext: Context
) : ViewModel() {

    private val _booksList = MutableStateFlow<Resource<List<Book>>>(Resource.loading())
    val booksList: StateFlow<Resource<List<Book>>> = _booksList

    fun updateBooks() {
        viewModelScope.launch(ioDispatcher) {
            _booksList.value = Resource.loading()

            try {
                booksRepository.updateBooks()
            } catch (e: Exception) {
                _booksList.value = Resource.error(appContext.getString(R.string.view_update_error_message), e)
            }
        }
    }

    init {
        viewModelScope.launch(ioDispatcher) {
            booksRepository.getBooks().collect() {
                _booksList.value = it
            }
        }
    }


}
