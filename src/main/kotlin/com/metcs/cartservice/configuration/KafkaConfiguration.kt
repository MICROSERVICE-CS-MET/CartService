package com.metcs.cartservice.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.support.converter.StringJsonMessageConverter

@Configuration
class KafkaConfiguration {
    @Bean
    fun jsonConverter(): StringJsonMessageConverter {
        return StringJsonMessageConverter()
    }
}
