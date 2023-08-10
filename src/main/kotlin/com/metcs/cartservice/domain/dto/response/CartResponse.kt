package com.metcs.cartservice.domain.dto.response

import com.metcs.cartservice.domain.model.CartItem

class CartResponse(
    val id: String? = null,
    var userId: String,
    var cartItems: List<CartItem>? = null
)
