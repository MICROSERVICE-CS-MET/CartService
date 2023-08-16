package com.metcs.cartservice.configuration.client.dto.response

import java.util.UUID

class CustomerResponse(
    val id: UUID?,
    var fullName: String,
    var password: String,
    var email: String
)
