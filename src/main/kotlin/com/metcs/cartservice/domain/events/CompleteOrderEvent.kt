package com.metcs.cartservice.domain.events

data class CompleteOrderEvent(
    var userId: String,
    var orderItems: List<OrderItem>,
    var totalPrice: Double,
)
