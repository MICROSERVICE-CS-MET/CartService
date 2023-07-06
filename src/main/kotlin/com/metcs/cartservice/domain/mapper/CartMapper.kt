package com.metcs.cartservice.domain.mapper

import com.metcs.cartservice.domain.dto.response.CartItemResponse
import com.metcs.cartservice.domain.dto.response.CartResponse
import com.metcs.cartservice.domain.model.Cart
import com.metcs.cartservice.domain.model.CartItem
import org.mapstruct.Mapper

@Mapper
interface CartMapper {
    fun cartToCartResponse(cart:Cart):CartResponse
    fun cartItemToCartItemResponse(cartItem: CartItem):CartItemResponse
    fun cartItemsToCartItemsListResponse(cartItems: List<CartItem>):List<CartItemResponse>
}