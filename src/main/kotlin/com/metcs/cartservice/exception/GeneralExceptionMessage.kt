package com.metcs.cartservice.exception

data class GeneralExceptionMessage(
    var message: String? = null,
    var path: String? = null,
    var status: Int? = null,
)
