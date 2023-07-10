package com.metcs.cartservice.domain.dto.response

data class CartItemResponse(
    var bookId: String? = null,
    var count: Int? = null,
    var price: Double? = null,
    var totalPrice: Double? = null,
)
