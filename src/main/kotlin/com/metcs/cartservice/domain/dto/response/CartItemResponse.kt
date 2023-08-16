package com.metcs.cartservice.domain.dto.response

import java.util.UUID

data class CartItemResponse(
    var productId: UUID?,
    var productCount: Int?,
    var unitPrice: Double?,
    var totalPrice: Double?
)
