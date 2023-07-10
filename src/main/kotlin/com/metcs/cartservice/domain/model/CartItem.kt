package com.metcs.cartservice.domain.model

data class CartItem(
    var bookId: String? = null,
    var count: Int? = null,
    var price: Double? = null,
    var totalPrice: Double? = null,

)
