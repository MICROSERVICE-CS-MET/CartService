package com.metcs.cartservice.domain.model

import java.util.UUID

data class CartItem(
    var productId: UUID?,
    var productName: String?,
    var productCount: Int?,
    var unitPrice: Double?,
    var totalPrice: Double?
)
