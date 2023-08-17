package com.metcs.cartservice.domain.dto.request

import java.util.UUID

data class AddBookToCartRequest(
    var productId: UUID,
    var userId: UUID,
    var productCount: Int
)
