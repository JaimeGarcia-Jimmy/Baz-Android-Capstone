package com.example.criptomonedas.data.api.dto

import com.example.criptomonedas.data.database.entities.AskDbEntity

data class AskDto(
    val book: String,
    val price: Double,
    val amount: Double
) {

    fun toDbEntity(): AskDbEntity =
        AskDbEntity(
            book = book,
            price = price,
            amount = amount
        )
}
