package com.example.criptomonedas.data.api.dto

import com.example.criptomonedas.data.database.entities.BookDbEntity

data class BookDto(
    val book: String,
    val high: Double?,
    val last: Double?,
    val low: Double?
) {

    fun toDbEntity(): BookDbEntity =
        BookDbEntity(
            book = book,
            high = high,
            last = last,
            low = low
        )
}
