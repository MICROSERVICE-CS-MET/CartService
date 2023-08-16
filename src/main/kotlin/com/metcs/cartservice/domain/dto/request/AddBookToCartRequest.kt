package com.metcs.cartservice.domain.dto.request

import java.util.UUID

data class AddBookToCartRequest(
    var productId: UUID,
    var userId: UUID,
    var name: String,
    var description: String,
    var category: String,
    var pageCount: Int,
    var unitPrice: Double?,
    var totalPrice: Double?,
    var productCount: Int
)
