package com.metcs.cartservice.repository

import com.metcs.cartservice.domain.model.Cart
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface CartRepository : MongoRepository<Cart, ObjectId> {
    fun findByUserId(id: String): Cart?
}
