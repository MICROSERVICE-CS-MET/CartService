package com.metcs.cartservice.domain.dto.request

data class AddBookToCartRequest(
    var productId: String,
    var userId: String,
    var productCount: Int,
    var unitPrice: Double?,
    var totalPrice: Double?
)
