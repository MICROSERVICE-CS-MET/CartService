package com.metcs.cartservice.service

import com.metcs.cartservice.domain.dto.request.AddBookToCartRequest
import com.metcs.cartservice.domain.dto.request.RemoveBookFromCartRequest
import com.metcs.cartservice.domain.mapper.CartMapper
import com.metcs.cartservice.domain.model.Cart
import com.metcs.cartservice.domain.model.CartItem
import com.metcs.cartservice.exception.CartHasNoDataException
import com.metcs.cartservice.producer.CartProducer
import com.metcs.cartservice.repository.CartRepository
import org.mapstruct.factory.Mappers
import org.springframework.stereotype.Service

@Service
class CartService(
    private val cartRepository: CartRepository,
    private val cartProducer: CartProducer
) {
    suspend fun findByUserId(userId: String): Cart {
        return cartRepository.findByUserId(userId) ?: cartRepository.save(
            Cart(
                userId = userId,
                cartItems = ArrayList<CartItem>()
            )
        )
    }
    suspend fun addBookToCart(addToCartDto: AddBookToCartRequest) {
        val cart = cartRepository.findByUserId(addToCartDto.userId) ?: cartRepository.save(
            Cart(
                userId = addToCartDto.userId,
                cartItems =
                ArrayList<CartItem>()
            )
        )
        cart.cartItems = addToCartList(cart.cartItems!!.toMutableList(), addToCartDto)
        cartRepository.save(cart)
    }

    suspend fun removeBookFromCart(removeBookFromCartRequest: RemoveBookFromCartRequest) {
        val cart = removeBookFromCartRequest.userId?.let { cartRepository.findByUserId(it) } ?: throw CartHasNoDataException(
            "Cart Already Has No" +
                " Data"
        )
        cart.cartItems = removeBookFromCartRequest.productId?.let {
            removeFromCartList(
                cart.cartItems!!.toMutableList(),
                it
            )
        }
        cartRepository.save(cart)
    }

    suspend fun getCartItemsByUserId(userId: String): List<CartItem> {
        var cart = cartRepository.findByUserId(userId) ?: cartRepository.save(
            Cart(
                userId = userId,
                cartItems =
                ArrayList<CartItem>()
            )
        )
        return cart.cartItems!!
    }

    suspend fun cleanToCart(userId: String) {
        var cart = cartRepository.findByUserId(userId) ?: cartRepository.save(
            Cart(
                userId = userId,
                cartItems =
                ArrayList<CartItem>()
            )
        )

        cart.cartItems = ArrayList<CartItem>()
        cartRepository.save(cart)
    }
    suspend fun completeOrder(cartId: String) {
        val cart = cartRepository.findById(cartId).orElseThrow { RuntimeException("Cart Not Found") }
        val converter = Mappers.getMapper(CartMapper::class.java)
        val completeOrderEvent = converter.cartToCompleteOrderEvent(cart)
        cartProducer.sendCompleteOrderEvent(completeOrderEvent)
    }

    private fun removeFromCartList(cartItems: MutableList<CartItem>, bookId: String): MutableList<CartItem> {
        val iterator = cartItems.iterator()
        while (iterator.hasNext()) {
            val item = iterator.next()
            if (item.productId == bookId) {
                if (item.productCount!! > 1) {
                    item.productCount = item.productCount!! - 1
                } else {
                    iterator.remove()
                }
                break
            }
        }
        return cartItems
    }

    private fun addToCartList(cartItems: MutableList<CartItem>, product: AddBookToCartRequest): MutableList<CartItem> {
        var foundItem: CartItem? = null
        for (item in cartItems) {
            if (item.productId == product.productId) {
                foundItem = item
                break
            }
        }
        if (foundItem != null) {
            foundItem.productCount = foundItem.productCount?.plus(product.productCount)
            foundItem.totalPrice = product.totalPrice?.plus((product.unitPrice?.times(product.productCount)!!))
        } else {
            val newItem = CartItem(product.productId, product.productCount, product.unitPrice, product.totalPrice)
            cartItems.add(newItem)
        }
        return cartItems
    }
}
