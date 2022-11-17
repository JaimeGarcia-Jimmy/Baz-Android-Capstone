package com.example.criptomonedas.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.criptomonedas.data.database.entities.AskDbEntity
import com.example.criptomonedas.data.database.entities.BidDbEntity
import com.example.criptomonedas.data.database.entities.BookDbEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BooksDao {

    @Query("SELECT * FROM books")
    fun getAllBooksFlow(): Flow<List<BookDbEntity>>

    @Query("SELECT * FROM books")
    fun getAllBooks(): List<BookDbEntity>

    @Query("SELECT * FROM books WHERE books.book = :book_id")
    fun getBookByIdFlow(book_id: String): Flow<BookDbEntity>

    @Query("SELECT * FROM books WHERE books.book = :book_id")
    fun getBookById(book_id: String): BookDbEntity

    @Delete
    fun deleteBook(book: BookDbEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllBooks(books: List<BookDbEntity>)

    @Update
    fun updateBook(book: BookDbEntity)

    @Query("SELECT * FROM asks WHERE asks.book = :book_id")
    fun getAsksByBookFlow(book_id: String): Flow<List<AskDbEntity>>

    @Query("SELECT * FROM asks WHERE asks.book = :book_id")
    fun getAsksByBook(book_id: String): List<AskDbEntity>

    @Query("SELECT * FROM bids WHERE bids.book = :book_id")
    fun getBidsByBookFlow(book_id: String): Flow<List<BidDbEntity>>

    @Query("SELECT * FROM bids WHERE bids.book = :book_id")
    fun getBidsByBook(book_id: String): List<BidDbEntity>

    @Delete
    fun deleteAllAsks(asks: List<AskDbEntity>)

    @Delete
    fun deleteAllBids(bids: List<BidDbEntity>)

    @Insert
    fun insertAllAsks(asks: List<AskDbEntity>)

    @Insert
    fun insertAllBids(bids: List<BidDbEntity>)

    @Query(
        "SELECT * FROM books " +
            "JOIN asks ON books.book = asks.book " +
            "WHERE books.book = :book_id"
    )
    fun loadBookAndAsks(book_id: String): Map<BookDbEntity, List<AskDbEntity>>

    @Query(
        "SELECT * FROM books " +
            "JOIN bids ON books.book = bids.book " +
            "WHERE books.book = :book_id"
    )
    fun loadBookAndBids(book_id: String): Map<BookDbEntity, List<BidDbEntity>>

    @Transaction
    fun deleteAndInsertAsksByBook(bookId: String, newAsks: List<AskDbEntity>) {
        val oldAsks = getAsksByBook(bookId)
        deleteAllAsks(oldAsks)
        insertAllAsks(newAsks)
    }

    @Transaction
    fun deleteAndInsertBidsByBook(bookId: String, newBids: List<BidDbEntity>) {
        val oldBids = getBidsByBook(bookId)
        deleteAllBids(oldBids)
        insertAllBids(newBids)
    }
}
