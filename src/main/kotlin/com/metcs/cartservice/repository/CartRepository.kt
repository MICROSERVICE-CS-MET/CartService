package com.metcs.cartservice.repository

import com.metcs.cartservice.domain.model.Cart
import com.metcs.cartservice.domain.model.CartItem
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface CartRepository: MongoRepository<Cart,ObjectId> {
    fun findByUserId(id:String):Cart?
    fun findCartItemsByUserId(id:String):List<CartItem>
}