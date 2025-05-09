package com.metcs.cartservice.controller

import com.metcs.cartservice.domain.dto.request.AddBookToCartRequest
import com.metcs.cartservice.domain.dto.request.RemoveBookFromCartRequest
import com.metcs.cartservice.domain.dto.response.CartItemResponse
import com.metcs.cartservice.domain.dto.response.CartResponse
import com.metcs.cartservice.domain.mapper.CartMapper
import com.metcs.cartservice.service.CartService
import org.mapstruct.factory.Mappers
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/carts")
class CartController(
    private val cartService: CartService
) {

    @GetMapping("/find-by-user-id/{userid}")
    suspend fun getByUserId(@PathVariable("userid")userId: UUID): CartResponse {
        val converter = Mappers.getMapper(CartMapper::class.java)
        return converter.cartToCartResponse(cartService.findByUserId(userId))
    }

    @GetMapping("/car-items/{userid}")
    suspend fun getCartItemsByUserId(@PathVariable("userid")userId: UUID): List<CartItemResponse> {
        val converter = Mappers.getMapper(CartMapper::class.java)
        return converter.cartItemsToCartItemsListResponse(cartService.getCartItemsByUserId(userId))
    }

    @PostMapping("/cart-items/add-book")
    suspend fun addBookToCart(@RequestBody addBookToCartRequest: AddBookToCartRequest) {
        cartService.addBookToCart(addBookToCartRequest)
    }

    @DeleteMapping("/cart-items/remove-book")
    suspend fun removeBookToCart(@RequestBody removeBookFromCartRequest: RemoveBookFromCartRequest) {
        cartService.removeBookFromCart(removeBookFromCartRequest)
    }

    @PostMapping("/complete-order/{cartId}")
    suspend fun completeOrder(@PathVariable("cartId")cartId: String): ResponseEntity<String> {
        cartService.completeOrder(cartId)
        return ResponseEntity.ok("Order Completed")
    }

    @DeleteMapping("/cart-items/clean-cart/{userid}")
    suspend fun cleanToCart(@PathVariable("userid")userId: UUID) {
        cartService.cleanToCart(userId)
    }
}
