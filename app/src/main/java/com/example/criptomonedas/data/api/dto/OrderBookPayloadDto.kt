package com.example.criptomonedas.data.api.dto

data class OrderBookPayloadDto(
    val asks: List<AskDto>,
    val bids: List<BidDto>
)
