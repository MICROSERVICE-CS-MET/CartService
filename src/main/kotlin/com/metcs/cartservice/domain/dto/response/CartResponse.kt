package com.metcs.cartservice.domain.dto.response

import com.metcs.cartservice.domain.model.CartItem
import java.util.UUID

class CartResponse(
    val id: String? = null,
    var userId: UUID,
    var cartItems: List<CartItem>? = null
)
