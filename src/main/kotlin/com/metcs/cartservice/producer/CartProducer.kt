package com.metcs.cartservice.producer

import com.metcs.cartservice.configuration.COMPLETE_ORDER_TOPIC
import com.metcs.cartservice.domain.events.CompleteOrderEvent
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class CartProducer(
    private val kafkaTemplate: KafkaTemplate<String, CompleteOrderEvent>
) {
    fun sendCompleteOrderEvent(completeOrderEvent: CompleteOrderEvent) {
        println(completeOrderEvent.toString())
        kafkaTemplate.send(COMPLETE_ORDER_TOPIC, completeOrderEvent)
    }
}