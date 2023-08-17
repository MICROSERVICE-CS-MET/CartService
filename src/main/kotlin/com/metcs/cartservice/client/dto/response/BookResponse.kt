package com.metcs.cartservice.client.dto.response

import java.util.UUID

data class BookResponse(
    val id: UUID,
    var name: String,
    var description: String,
    var category: String,
    var pageCount: Int,
    var stock: Int,
    var price: Double
)
