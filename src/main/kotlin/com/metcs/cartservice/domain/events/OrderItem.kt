package com.metcs.cartservice.domain.events

data class OrderItem(
    var productId: String,
    var productName: String,
    var productCount: Int,
    var unitPrice: Double,
    var totalPrice: Int
)
