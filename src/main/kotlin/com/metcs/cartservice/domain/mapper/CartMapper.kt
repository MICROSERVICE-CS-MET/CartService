package com.metcs.cartservice.domain.mapper

import com.metcs.cartservice.domain.dto.response.CartItemResponse
import com.metcs.cartservice.domain.dto.response.CartResponse
import com.metcs.cartservice.domain.events.CompleteOrderEvent
import com.metcs.cartservice.domain.model.Cart
import com.metcs.cartservice.domain.model.CartItem
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper
interface CartMapper {
    fun cartToCartResponse(cart: Cart): CartResponse
    fun cartItemToCartItemResponse(cartItem: CartItem): CartItemResponse
    fun cartItemsToCartItemsListResponse(cartItems: List<CartItem>): List<CartItemResponse>

    @Mapping(source = "cartItems", target = "orderItems")
    fun cartToCompleteOrderEvent(cart: Cart): CompleteOrderEvent
}
