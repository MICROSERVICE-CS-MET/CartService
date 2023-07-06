package com.metcs.cartservice.service

import com.metcs.cartservice.domain.dto.request.AddBookToCartRequest
import com.metcs.cartservice.domain.dto.request.RemoveBookFromCartRequest
import com.metcs.cartservice.domain.model.Cart
import com.metcs.cartservice.domain.model.CartItem
import com.metcs.cartservice.exception.CartHasNoDataException
import com.metcs.cartservice.repository.CartRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class CartService(
    private val cartRepository: CartRepository
) {
    suspend fun findByUserId(userId: String):Cart{
        return cartRepository.findByUserId(userId)?:cartRepository.save(Cart(userId=userId,cartItems=
        ArrayList<CartItem>()))
    }
    suspend fun addBookToCart(addToCartDto:AddBookToCartRequest){
        val cart= addToCartDto.userId?.let { cartRepository.findByUserId(it) } ?:cartRepository.save(Cart(
            userId=addToCartDto.userId,cartItems=
        ArrayList<CartItem>()))
        cart.cartItems= addToCartDto.bookId?.let { addToCartList(cart.cartItems!!.toMutableList(), it) }
        cartRepository.save(cart)
    }

    suspend fun removeBookFromCart(removeBookFromCartRequest: RemoveBookFromCartRequest){
        val cart= removeBookFromCartRequest.userId?.let { cartRepository.findByUserId(it) } ?: throw CartHasNoDataException("Cart Already Has No" +
                " Data")
        cart.cartItems= removeBookFromCartRequest.bookId?.let {
            removeFromCartList(cart.cartItems!!.toMutableList(),
                it
            )
        }
        cartRepository.save(cart)
    }

    suspend fun getCartItemsByUserId(userId:String):List<CartItem>{
        var cart = cartRepository.findByUserId(userId)?:cartRepository.save(Cart(userId=userId,cartItems=
        ArrayList<CartItem>()))
        return cart.cartItems!!
    }

    suspend fun cleanToCart(userId: String){
        var cart = cartRepository.findByUserId(userId)?:cartRepository.save(Cart(userId=userId,cartItems=
        ArrayList<CartItem>()))

        cart.cartItems= ArrayList<CartItem>()
        cartRepository.save(cart)
    }














    private fun removeFromCartList(cartItems: MutableList<CartItem>, bookId: String):MutableList<CartItem> {
        val iterator = cartItems.iterator()
        while (iterator.hasNext()) {
            val item = iterator.next()
            if (item.bookId == bookId) {
                if (item.count!! > 1) {
                    item.count = item.count!! - 1
                } else {
                    iterator.remove()
                }
                break
            }
        }
        return cartItems
    }




    private fun addToCartList(cartItems: MutableList<CartItem>, bookId: String) : MutableList<CartItem>{
        var foundItem: CartItem? = null
        for (item in cartItems) {
            if (item.bookId == bookId) {
                foundItem = item
                break
            }
        }
        if (foundItem != null) {
            foundItem.count = foundItem.count?.plus(1)
        } else {
            val newItem = CartItem(bookId, 1, 0.0, 0.0)
            cartItems.add(newItem)
        }
        return cartItems
    }
}