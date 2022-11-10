package com.example.criptomonedas.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.criptomonedas.data.entities.Book

@Entity(tableName = "books")
data class BookDbEntity(
    @PrimaryKey
    val book: String,
    val high: Double?,
    val last: Double?,
    val low: Double?
) {
    fun toEntity(): Book =
        Book(
            bookId = book,
            high = high,
            last = last,
            low = low
        )
}
