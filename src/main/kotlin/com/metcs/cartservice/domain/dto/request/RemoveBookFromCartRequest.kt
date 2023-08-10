package com.metcs.cartservice.domain.dto.request

data class RemoveBookFromCartRequest(
    var productId: String,
    var userId: String
)
