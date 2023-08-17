package com.metcs.cartservice.client.service

import com.metcs.cartservice.client.dto.response.BookResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import java.util.UUID

@Component
@FeignClient(value = "book-service", url = "http://localhost:3030/books")
interface BookServiceClient {

    @GetMapping("/{id}")
    fun findById(@PathVariable("id") id: UUID): BookResponse?
}
