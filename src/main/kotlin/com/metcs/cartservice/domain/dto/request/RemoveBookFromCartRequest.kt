package com.metcs.cartservice.domain.dto.request

import java.util.UUID

data class RemoveBookFromCartRequest(
    var productId: UUID,
    var userId: UUID
)
