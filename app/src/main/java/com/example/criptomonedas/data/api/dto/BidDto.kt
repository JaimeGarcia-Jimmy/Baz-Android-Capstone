package com.example.criptomonedas.data.api.dto

import com.example.criptomonedas.data.database.entities.BidDbEntity

data class BidDto(
    val book: String,
    val price: Double,
    val amount: Double
) {

    fun toDbEntity(): BidDbEntity =
        BidDbEntity(
            book = book,
            price = price,
            amount = amount
        )
}
