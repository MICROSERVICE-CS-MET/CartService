package com.metcs.cartservice.domain.model

data class CartItem(
    var productId: String?,
    var productCount: Int?,
    var unitPrice: Double?,
    var totalPrice: Double?
)
