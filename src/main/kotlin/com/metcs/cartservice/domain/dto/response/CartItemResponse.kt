package com.metcs.cartservice.domain.dto.response

data class CartItemResponse(
    var productId: String?,
    var productCount: Int?,
    var unitPrice: Double?,
    var totalPrice: Double?
)
