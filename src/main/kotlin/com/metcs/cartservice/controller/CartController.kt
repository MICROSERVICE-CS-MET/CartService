package com.metcs.cartservice.controller

import com.metcs.cartservice.domain.dto.request.AddBookToCartRequest
import com.metcs.cartservice.domain.dto.request.RemoveBookFromCartRequest
import com.metcs.cartservice.domain.model.Cart
import com.metcs.cartservice.domain.model.CartItem
import com.metcs.cartservice.service.CartService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/carts")
class CartController(
    private val cartService: CartService
) {
    @GetMapping("/{userid}")
    suspend fun findCartByUserId(@PathVariable("userid")userId:String):Cart{
        return cartService.findByUserId(userId)
    }
    @GetMapping("/cartItems/{userid}")
    suspend fun findCartItemsByUserId(@PathVariable("userid")userId:String):List<CartItem>{
        return cartService.getCartItemsByUserId(userId)
    }
    @PostMapping("/cartItems/addBook")
    suspend fun addBookToCart(@RequestBody addBookToCartRequest: AddBookToCartRequest){
        cartService.addBookToCart(addBookToCartRequest)
    }
    @DeleteMapping("/cartItems/removeBook")
    suspend fun addBookToCart(@RequestBody removeBookFromCartRequest: RemoveBookFromCartRequest){
        cartService.removeBookFromCart(removeBookFromCartRequest)
    }
}