package com.metcs.cartservice.domain.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID

@Document
data class Cart(
    @Id val id: String? = null,
    var userId: UUID,
    var cartItems: List<CartItem>? = null,
    var totalPrice: Double? = 0.0

)
