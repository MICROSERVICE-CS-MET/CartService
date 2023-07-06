package com.metcs.cartservice.controller

import com.metcs.cartservice.domain.dto.request.AddBookToCartRequest
import com.metcs.cartservice.domain.dto.request.RemoveBookFromCartRequest
import com.metcs.cartservice.domain.dto.response.CartItemResponse
import com.metcs.cartservice.domain.dto.response.CartResponse
import com.metcs.cartservice.domain.mapper.CartMapper
import com.metcs.cartservice.domain.model.Cart
import com.metcs.cartservice.domain.model.CartItem
import com.metcs.cartservice.service.CartService
import org.mapstruct.factory.Mappers
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/carts")
class CartController(
    private val cartService: CartService
) {
    @GetMapping("/{userid}")
    suspend fun findCartByUserId(@PathVariable("userid")userId:String):CartResponse{
        val converter = Mappers.getMapper(CartMapper::class.java)
        return converter.cartToCartResponse(cartService.findByUserId(userId))
    }
    @GetMapping("/cartItems/{userid}")
    suspend fun findCartItemsByUserId(@PathVariable("userid")userId:String):List<CartItemResponse>{
        val converter = Mappers.getMapper(CartMapper::class.java)
        return converter.cartItemsToCartItemsListResponse(cartService.getCartItemsByUserId(userId))
    }
    @PostMapping("/cartItems/addBook")
    suspend fun addBookToCart(@RequestBody addBookToCartRequest: AddBookToCartRequest){
        cartService.addBookToCart(addBookToCartRequest)
    }
    @DeleteMapping("/cartItems/removeBook")
    suspend fun addBookToCart(@RequestBody removeBookFromCartRequest: RemoveBookFromCartRequest){
        cartService.removeBookFromCart(removeBookFromCartRequest)
    }
    @DeleteMapping("/cartItems/cleanCart/{userid}")
    suspend fun cleanToCart(@PathVariable("userid")userId: String){
        cartService.cleanToCart(userId)
    }
}