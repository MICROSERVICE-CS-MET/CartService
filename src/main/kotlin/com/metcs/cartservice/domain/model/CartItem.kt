package com.metcs.cartservice.domain.model

import java.util.UUID

data class CartItem(
    var bookId:String?=null,
    var count:Int?=null,
    var price: Double?=null,
    var totalPrice: Double?=null

)
