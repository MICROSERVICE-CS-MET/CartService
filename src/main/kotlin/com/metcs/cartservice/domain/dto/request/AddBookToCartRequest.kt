package com.metcs.cartservice.domain.dto.request

data class AddBookToCartRequest(
    var bookId: String,
    var userId: String,
)
