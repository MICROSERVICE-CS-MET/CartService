package com.metcs.cartservice.domain.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Cart(
    @Id val id: String? = null,
    var userId: String,
    var cartItems: List<CartItem>? = null,
    var totalPrice: Double? = 0.0,

)
