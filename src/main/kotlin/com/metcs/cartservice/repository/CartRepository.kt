package com.metcs.cartservice.repository

import com.metcs.cartservice.domain.model.Cart
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface CartRepository : MongoRepository<Cart, String> {
    fun findByUserId(id: UUID): Cart?
}
