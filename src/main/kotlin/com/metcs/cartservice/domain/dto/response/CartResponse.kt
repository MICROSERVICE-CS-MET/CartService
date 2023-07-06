package com.metcs.cartservice.domain.dto.response

import com.metcs.cartservice.domain.model.CartItem
import org.bson.types.ObjectId

class CartResponse (
    val id: ObjectId?=null,
    var userId: String,
    var cartItems:List<CartItem>?=null
)