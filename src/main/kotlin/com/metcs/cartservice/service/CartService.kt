package com.metcs.cartservice.service

import com.metcs.cartservice.client.dto.response.BookResponse
import com.metcs.cartservice.client.service.BookServiceClient
import com.metcs.cartservice.client.service.CustomerServiceClient
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
import java.util.UUID

@Service
class CartService(
    private val cartRepository: CartRepository,
    private val cartProducer: CartProducer,
    private val bookServiceClient: BookServiceClient,
    private val customerServiceClient: CustomerServiceClient
) {
    suspend fun findByUserId(userId: UUID): Cart {
        return cartRepository.findByUserId(userId) ?: cartRepository.save(
            Cart(
                userId = userId,
                cartItems = ArrayList<CartItem>()
            )
        )
    }
    suspend fun addBookToCart(addToCartDto: AddBookToCartRequest) {
        val book = bookServiceClient.findById(addToCartDto.productId)
        customerServiceClient.findById(addToCartDto.userId)

        val cart = cartRepository.findByUserId(addToCartDto.userId) ?: cartRepository.save(
            Cart(
                userId = addToCartDto.userId,
                cartItems =
                ArrayList<CartItem>()
            )
        )
        cart.cartItems = addToCartList(cart.cartItems!!.toMutableList(), addToCartDto, book!!)
        cartRepository.save(cart)
    }

    suspend fun removeBookFromCart(removeBookFromCartRequest: RemoveBookFromCartRequest) {
        val cart = removeBookFromCartRequest.userId?.let { cartRepository.findByUserId(it) } ?: throw CartHasNoDataException(
            "Cart Already Has No" +
                " Data"
        )
        val book = bookServiceClient.findById(removeBookFromCartRequest.productId)
        cart.cartItems = removeFromCartList(cart.cartItems!!.toMutableList(), removeBookFromCartRequest.productId, book!!)
        cartRepository.save(cart)
    }

    suspend fun getCartItemsByUserId(userId: UUID): List<CartItem> {
        var cart = cartRepository.findByUserId(userId) ?: cartRepository.save(
            Cart(
                userId = userId,
                cartItems =
                ArrayList<CartItem>()
            )
        )
        return cart.cartItems!!
    }

    suspend fun cleanToCart(userId: UUID) {
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

    private fun removeFromCartList(cartItems: MutableList<CartItem>, bookId: UUID, book: BookResponse): MutableList<CartItem> {
        val iterator = cartItems.iterator()
        while (iterator.hasNext()) {
            val item = iterator.next()
            if (item.productId == bookId) {
                if (item.productCount!! > 1) {
                    item.productCount = item.productCount!! - 1
                    item.totalPrice = item.productCount!! * book.price
                    item.unitPrice = book.price
                } else {
                    iterator.remove()
                }
                break
            }
        }
        return cartItems
    }

    private fun addToCartList(cartItems: MutableList<CartItem>, product: AddBookToCartRequest, book: BookResponse): MutableList<CartItem> {
        var foundItem: CartItem? = null
        for (item in cartItems) {
            if (item.productId == product.productId) {
                foundItem = item
                foundItem.productCount = product.productCount
                foundItem.productId = book.id
                foundItem.unitPrice = book.price
                foundItem.totalPrice = book.price * product.productCount
                foundItem.productName = book.name
                break
            }
        }
        if (foundItem != null) {
            foundItem.productCount = foundItem.productCount?.plus(product.productCount)
            foundItem.totalPrice = foundItem.totalPrice!!.plus(product.productCount * book.price)
        } else {
            val newItem = CartItem(productId = product.productId, productName = book.name, productCount = product.productCount, unitPrice = book.price, totalPrice = book.price * product.productCount)
            cartItems.add(newItem)
        }
        return cartItems
    }
}
