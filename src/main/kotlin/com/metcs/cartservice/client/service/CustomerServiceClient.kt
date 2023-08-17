package com.metcs.cartservice.client.service

import com.metcs.cartservice.client.dto.response.CustomerResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import java.util.UUID

@Component
@FeignClient(value = "customer-service", url = "http://localhost:6060/customers")
interface CustomerServiceClient {

    @GetMapping("/{id}")
    fun findById(@PathVariable("id") id: UUID): CustomerResponse?
}
