package com.example.criptomonedas.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.criptomonedas.data.entities.Ask

@Entity(tableName = "asks")
data class AskDbEntity(
    val book: String,
    val price: Double,
    val amount: Double
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    fun toEntity(): Ask =
        Ask(
            bookId = book,
            price = price,
            amount = amount
        )
}
