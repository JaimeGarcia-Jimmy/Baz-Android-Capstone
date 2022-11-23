package com.example.criptomonedas.ui.viewmodels

import com.example.criptomonedas.MainCoroutineRule
import com.example.criptomonedas.data.Resource
import com.example.criptomonedas.data.entities.Book
import com.example.criptomonedas.data.repositories.BooksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.doSuspendableAnswer
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.lang.Exception

class AvailableBooksViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val booksRepository = mock<BooksRepository>()

    private lateinit var viewModel: AvailableBooksViewModel

    @Before
    fun setUp() {
        viewModel = AvailableBooksViewModel(booksRepository)
    }

    @Test
    fun `Loading state after executing updateBooks works`() = runBlocking {
        whenever(booksRepository.updateBooks()).doSuspendableAnswer {
            withContext(Dispatchers.IO) { delay(5000) }
        }
        viewModel = AvailableBooksViewModel(booksRepository)
        viewModel.updateBooks()
        Assert.assertEquals(Resource.loading<Resource<List<Book>>>(), viewModel.booksList.value)
    }

    @Test
    fun `Verify repository_updateBooks is called inside updateBooks`() = runBlocking {
        whenever(booksRepository.updateBooks()).thenReturn(null)
        viewModel = AvailableBooksViewModel(booksRepository)
        viewModel.updateBooks()
        verify(booksRepository).updateBooks()
    }

    @Test
    fun `Failure state after executing updateBooks works`() = runBlocking {
        whenever(booksRepository.updateBooks()).thenThrow(RuntimeException("Error"))
        viewModel = AvailableBooksViewModel(booksRepository)
        viewModel.updateBooks()
        Assert.assertEquals(Resource.error<Resource<List<Book>>>("Error al actualizar", RuntimeException("Error")).javaClass, viewModel.booksList.value.javaClass)
    }

    @After
    fun tearDown() {
    }
}