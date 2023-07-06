package com.metcs.cartservice.domain.dto.request


data class RemoveBookFromCartRequest (
    var bookId: String,
    var userId: String
)